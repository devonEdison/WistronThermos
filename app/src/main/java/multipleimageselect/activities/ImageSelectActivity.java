package multipleimageselect.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wits.wistronthermos.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import multipleimageselect.adapters.CustomImageSelectAdapter;
import multipleimageselect.helpers.Constants;
import multipleimageselect.models.Image;

/**
 * Created by Darshan on 4/18/2015.
 * revised by Devon on 9/25/2015.
 */
public class ImageSelectActivity extends AppCompatActivity {
    private static final String TAG = "ImageSelectActivity";
    private ProgressBar progressBar;
    private RelativeLayout login_progress;
    private GridView gridView;
    private CustomImageSelectAdapter customImageSelectAdapter;

    private ArrayList<Image> images;
    private String album;

    private int countSelected;

    private ContentObserver contentObserver;
    private static Handler handler;

    private Thread thread;

    private final String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
    private TextView textView_choose;
    private TextView toolbar_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        textView_choose = (TextView) findViewById(R.id.textView_choose);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(R.string.image_view);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.app_name);
            toolbar.setNavigationIcon(R.mipmap.btn_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            toolbar_next = (TextView) toolbar.findViewById(R.id.toolbar_next);
            toolbar_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countSelected > Constants.limit) {
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.limit_exceeded), Constants.limit), Toast.LENGTH_LONG).show();
                    } else if (countSelected < Constants.limit) {
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.limit_exceeded), Constants.limit), Toast.LENGTH_LONG).show();
                    } else {
                        sendIntent();
                    }
                }
            });
        }
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        album = intent.getStringExtra(Constants.INTENT_EXTRA_ALBUM);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_image_select);
        login_progress = (RelativeLayout) findViewById(R.id.login_progress);
        gridView = (GridView) findViewById(R.id.grid_view_image_select);
        gridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.FETCH_STARTED: {
                        progressBar.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.INVISIBLE);
                        break;
                    }

                    case Constants.FETCH_COMPLETED: {
                        /*If adapter is null, this implies that the loaded images will be shown
                        			for the first time, hence send FETCH_COMPLETED message.
                        			However, if adapter has been initialised, this thread was run either
                        			due to the activity being restarted or content being changed.*/
                        if (customImageSelectAdapter == null) {
                            customImageSelectAdapter = new CustomImageSelectAdapter(getApplicationContext(), images);
                            gridView.setAdapter(customImageSelectAdapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            gridView.setVisibility(View.VISIBLE);
                            orientationBasedUI(getResources().getConfiguration().orientation);

                        } else {
                            customImageSelectAdapter.notifyDataSetChanged();
                            /* Some selected images may have been deleted
                        				     hence update action mode title */
                            countSelected = msg.arg1;
                            textView_choose.setText(String.format(getString(R.string.already_selected), countSelected, countSelected));
                        }

                        break;
                    }

                    default: {
                        super.handleMessage(msg);
                    }
                }
            }
        };
        contentObserver = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                startImageLoading();
            }
        };
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, contentObserver);

        startImageLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();

        abortImageLoading();

        getContentResolver().unregisterContentObserver(contentObserver);
        contentObserver = null;

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (mHandlerMenu != null) {
            mHandlerMenu.removeCallbacksAndMessages(null);
            mHandlerMenu = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationBasedUI(newConfig.orientation);
    }

    private void orientationBasedUI(int orientation) {
        final WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int size = orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 3 : metrics.widthPixels / 5;
        customImageSelectAdapter.setLayoutParams(size);
        gridView.setNumColumns(orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5);
    }

    private AbsListView.OnItemClickListener onItemClickListener = new AbsListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if ((countSelected < 1) || (countSelected == 1 && images.get(position).isSelected)) {
                toggleSelection(position);
                textView_choose.setText(String.format(getString(R.string.already_selected), countSelected, countSelected));
            }
        }
    };
    Handler mHandlerMenu;

    private void toggleSelection(int position) {
        images.get(position).isSelected = !images.get(position).isSelected;
        if (images.get(position).isSelected) {
            countSelected++;
        } else {
            countSelected--;
        }
        customImageSelectAdapter.notifyDataSetChanged();
        if (countSelected == Constants.limit) {
            toolbar_next.setTextColor(getResources().getColor(R.color.white));
        } else {
            toolbar_next.setTextColor(getResources().getColor(R.color.half_white));
        }
    }

    private void deselectAll() {
        for (int i = 0, l = images.size(); i < l; i++) {
            images.get(i).isSelected = false;
        }
        countSelected = 0;
        customImageSelectAdapter.notifyDataSetChanged();
    }

    private ArrayList<Image> getSelected() {
        ArrayList<Image> selectedImages = new ArrayList<>();
        for (int i = 0, l = images.size(); i < l; i++) {
            if (images.get(i).isSelected) {
                selectedImages.add(images.get(i));
            }
        }
        return selectedImages;
    }

    private void sendIntent() {
        login_progress.setVisibility(View.VISIBLE);
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES, getSelected());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startImageLoading() {
        abortImageLoading();

        ImageLoaderRunnable runnable = new ImageLoaderRunnable();
        thread = new Thread(runnable);
        thread.start();
    }

    private void abortImageLoading() {
        if (thread == null) {
            return;
        }

        if (thread.isAlive()) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ImageLoaderRunnable implements Runnable {
        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            Message message;
            if (customImageSelectAdapter == null) {
                message = handler.obtainMessage();
                /*
                If the adapter is null, this is first time this activity's view is
                being shown, hence send FETCH_STARTED message to show progress bar
                while images are loaded from phone
                 */
                message.what = Constants.FETCH_STARTED;
                message.sendToTarget();
            }

            if (Thread.interrupted()) {
                return;
            }

            File file;
            HashSet<Long> selectedImages = new HashSet<>();
            if (images != null) {
                Image image;
                for (int i = 0, l = images.size(); i < l; i++) {
                    image = images.get(i);
                    file = new File(image.path);
                    if (file.exists() && image.isSelected) {
                        selectedImages.add(image.id);
                    }
                }
            }

            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?", new String[]{album}, MediaStore.Images.Media.DATE_ADDED);

            /*
            In case this runnable is executed to onChange calling startImageLoading,
            using countSelected variable can result in a race condition. To avoid that,
            tempCountSelected keeps track of number of selected images. On handling
            FETCH_COMPLETED message, countSelected is assigned value of tempCountSelected.
             */
            int tempCountSelected = 0;
            ArrayList<Image> temp = new ArrayList<>(cursor.getCount());

            if (cursor.moveToLast()) {
                do {
                    if (Thread.interrupted()) {
                        return;
                    }

                    long id = cursor.getLong(cursor.getColumnIndex(projection[0]));
                    String name = cursor.getString(cursor.getColumnIndex(projection[1]));
                    String path = cursor.getString(cursor.getColumnIndex(projection[2]));
                    boolean isSelected = selectedImages.contains(id);
                    if (isSelected) {
                        tempCountSelected++;
                    }

                    file = new File(path);
                    if (file.exists()) {
                        temp.add(new Image(id, name, path, isSelected));
                    }

                } while (cursor.moveToPrevious());
            }
            cursor.close();

            if (images == null) {
                images = new ArrayList<>();
            }
            images.clear();
            images.addAll(temp);

            message = handler.obtainMessage();
            message.what = Constants.FETCH_COMPLETED;
            message.arg1 = tempCountSelected;
            message.sendToTarget();

            Thread.interrupted();
        }
    }
}
