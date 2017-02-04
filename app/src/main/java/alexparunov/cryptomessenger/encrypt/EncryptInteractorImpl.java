package alexparunov.cryptomessenger.encrypt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import alexparunov.cryptomessenger.utils.HelperMethods;

public class EncryptInteractorImpl implements EncryptInteractor {

  private Context context;
  private EncryptInteractorListener listener;

  EncryptInteractorImpl(Context context, EncryptInteractorListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public void performSteganography(String message, Bitmap coverImage, Bitmap secretImage) {
    if (secretImage == null) {
      encryptSecretMessage(message, coverImage);
    } else {
      encryptSecretImage(coverImage, secretImage);
    }
  }

  private void encryptSecretMessage(String message, Bitmap coverImage) {

  }

  private void encryptSecretImage(Bitmap coverImage, Bitmap secretImage) {
    byte[] coverImageInBytes = HelperMethods.bitmapToByArray(coverImage);
  }

  interface EncryptInteractorListener {

    void onPerformSteganographySuccessful();

    void onPerformSteganographyFailure();
  }
}
