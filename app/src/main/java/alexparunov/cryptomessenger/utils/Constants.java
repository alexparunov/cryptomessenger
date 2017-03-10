package alexparunov.cryptomessenger.utils;

public class Constants {

  // Permissions
  public static final int PERMISSIONS_CAMERA = 0;
  public static final int PERMISSIONS_EXTERNAL_STORAGE = 1;

  // Request Codes
  public static final int REQUEST_CAMERA = 0;
  public static final int SELECT_FILE = 1;

  // Cover/Secret
  public static final int COVER_IMAGE = 0;
  public static final int SECRET_IMAGE = 1;

  //SharedPreferences
  public static final String SHARED_PREF_NAME = "CryptoMessengerSP";
  public static final String PREF_COVER = "coverImagePref";

  // Secret Message Types
  public static final int TYPE_TEXT = 0;
  public static final int TYPE_IMAGE = 1;

  //Bundle arguments
  public static final String EXTRA_STEGO_IMAGE_PATH = "stego_image_path";

  //Colors for Stego Image
  public static final int COLOR_RGB_END = 0;
  public static final int COLOR_RGB_TEXT = 0;
  public static final int COLOR_RGB_IMAGE = 255;
}
