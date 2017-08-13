package alexparunov.steganographer.activities.encrypt;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.io.File;

interface EncryptView {

  /**
   *
   * @return String secretMessage which is stored inside the editTextView
   */
  String getSecretMessage();

  /**
   *
   * @return Bitmap coverImage which is stored inside the imageView
   */
  Bitmap getCoverImage();

  /**
   *
   * @return Bitmap secretImage which is stored inside the imageView
   */
  Bitmap getSecretImage();

  /**
   *
   * @return SharedPreferences
   */
  SharedPreferences getSharedPrefs();

  /**
   * Sets up the toolbar. Title, onBackButton
   */
  void initToolbar();

  /**
   * Sets the secret message editTextView
   * @param secretMessage
   */
  void setSecretMessage(String secretMessage);

  /**
   * Sets the cover image imageView
   * @param file
   */
  void setCoverImage(File file);

  /**
   * Sets the secret image imageview
   * @param file
   */
  void setSecretImage(File file);

  /**
   * Shows a toast
   * @param message which is stored in the @string resources
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
   * Opens camera activity
   */
  void openCamera();

  /**
   * Opens media chooser
   */
  void chooseImage();

  /**
   * Starts Stego Activity with image path
   * @param filePath which is the temp stego image path after successfullu
   *                 performed steganography
   */
  void startStegoActivity(String filePath);

}
