package alexparunov.cryptomessenger.activities.encrypt;

import android.graphics.Bitmap;

public interface EncryptInteractor {

  void performSteganography(String message, Bitmap coverImage, Bitmap secretImage);
}
