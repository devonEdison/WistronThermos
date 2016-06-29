package multipleimageselect.helpers;

/**
 * Created by Devon on 5/26/2015.
 */
public class Constants {
    public static final int REQUEST_CODE = 2000;
    public static final int FETCH_STARTED = 2001;
    public static final int FETCH_COMPLETED = 2002;
    public static final String INTENT_EXTRA_ALBUM = "album";
    public static final String INTENT_EXTRA_IMAGES = "images";
    public static final String INTENT_EXTRA_LIMIT = "limit";
    public static final String TITLE = "title";
    public static final int DEFAULT_LIMIT = 10;

    //Maximum number of images that can be selected at a time
    public static int limit;

    //permission & onActivityResult Request
    public final static int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    public final static int BLE_TURNON=1;
    public final static int CAMERA_WRITE_EXTERNAL_STORAGE = 2;
    public final static int GET_FITBIT_REQUEST = 3;
}
