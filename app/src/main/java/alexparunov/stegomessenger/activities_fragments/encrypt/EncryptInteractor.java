package alexparunov.stegomessenger.activities_fragments.encrypt;

import android.graphics.Bitmap;

interface EncryptInteractor {

  /**
   * Performs Steganography on either message or image
   *
   * @param message Secret message String. Null if we encrypt image
   * @param coverImage Cover Image as Bitmap which is used to hide data
   * @param secretImage Secret Image as Bitmap which is hidden inside coverImage. Null if we encrypt String
   *
   * The method is using listeners to interact with result actions
   */
  void performSteganography(String message, Bitmap coverImage, Bitmap secretImage);
}
