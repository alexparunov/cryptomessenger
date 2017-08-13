package alexparunov.steganographer.activities.decrypt;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Alexander Parunov on 3/9/17.
 */

interface DecryptView {

  /**
   *
   * @return Bitmap which is stored in stegoimage imageView
   */
  Bitmap getStegoImage();

  /**
   * Initializes settings for toolbar. (title, onBackArrow, etc.)
   */
  void initToolbar();

  /**
   * Sets Stego Image to stegoImage imageView
   * @param file image itself, which is got from the path
   */
  void setStegoImage(File file);

  /**
   * Shows the toast message
   * @param message is stored in @string resources
   */
  void showToast(int message);

  /**
   * Opens media chooser
   */
  void chooseImage();

  /**
   * Shows progress dialog
   */
  void showProgressDialog();

  /**
   * Dismisses progress dialog
   */
  void stopProgressDialog();

  /**
   * Starts Decrypt Result Activity
   * @param secretMessage Result secret message decoded from stream of bits. Null if doesn't exist
   * @param secretImagePath Result secret image decoded from stream of bits. Null if doesn't exist
   */
  void startDecryptResultActivity(String secretMessage, String secretImagePath);
}
