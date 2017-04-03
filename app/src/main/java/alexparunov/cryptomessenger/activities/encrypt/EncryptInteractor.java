package alexparunov.cryptomessenger.activities.encrypt;

import android.graphics.Bitmap;

interface EncryptInteractor {

  /**
   * Performs Steganography on either message or image
   *
   * @param message Secret message String
   * @param coverImage Cover Image as Bitmap which is used to hide data
   * @param secretImage Secret Image as Bitmap which is hidden inside coverImage
   *
   * The method is using listeners to interact with resulted actions
   */
  void performSteganography(String message, Bitmap coverImage, Bitmap secretImage);
}
