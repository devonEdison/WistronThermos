package multipleimageselect.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wits.wistronthermos.R;

import java.util.ArrayList;

import multipleimageselect.models.Image;

/**
 * Created by Darshan on 4/18/2015.
 */
public class CustomImageSelectAdapter extends CustomGenericAdapter<Image> {
    public CustomImageSelectAdapter(Context context, ArrayList<Image> images) {
        super(context, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ImageView imageView_check;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item_image_select, null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            imageView = (ImageView) convertView.findViewById(R.id.image_view_image_select);
            imageView_check = (ImageView) convertView.findViewById(R.id.imageView_check);

        } else {
            imageView = (ImageView) convertView.getTag(R.id.image_view_image_select);
            imageView_check = (ImageView) convertView.findViewById(R.id.imageView_check);
        }

        convertView.setTag(R.id.image_view_image_select, imageView);

        imageView.getLayoutParams().width = size;
        imageView.getLayoutParams().height = size;

        if (arrayList.get(position).isSelected) {
            imageView.setAlpha((float) 0.2);
//            ((FrameLayout) convertView).setForeground(context.getResources().getDrawable(R.drawable.ic_cab_done_mtrl_alpha));
            imageView_check.setVisibility(View.VISIBLE);
        } else {
            imageView.setAlpha((float) 1.0);
//            ((FrameLayout) convertView).setForeground(null);
            imageView_check.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(arrayList.get(position).path)
                .placeholder(R.drawable.image_placeholder).centerCrop().into(imageView);

        return convertView;
    }
}
