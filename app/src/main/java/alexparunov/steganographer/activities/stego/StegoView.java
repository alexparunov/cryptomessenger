package alexparunov.steganographer.activities.stego;

import android.content.Intent;

/**
 * Created by Alexander Parunov on 2/21/17.
 */

interface StegoView {

  /**
   * Shows toast message
   * @param message is id of a message which is stored in @string resources
   */
  void showToast(int message);

  /**
   * Shows progress dialog
   */
  void showProgressDialog();

  /**
   * Dismisses progress dialog
   */
  void stopProgressDialog();

  /**
   * Initializes toolbar. (Title, onBackArrow, etc.)
   */
  void initToolbar();

  /**
   * Sets stego image to stegoImage imageView
   * @param path to image in Internal Memory
   */
  void setStegoImage(String path);

  /**
   * Saves Stego Image to media so that it can easily accessed in media chooser
   * @param intent
   */
  void saveToMedia(Intent intent);

  /**
   * Create sharer to external applications (Messenger, WhatsApp, Skype, etc.)
   * @param path
   */
  void shareStegoImage(String path);
}
