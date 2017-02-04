package alexparunov.cryptomessenger.encrypt;

import android.content.Context;
import android.graphics.Bitmap;

public class EncryptInteractorImpl implements EncryptInteractor {

  private Context context;
  private EncryptInteractorListener listener;

  public EncryptInteractorImpl(Context context, EncryptInteractorListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public void performSteganography(String message, Bitmap coverImage, Bitmap secretImage) {

  }


  interface EncryptInteractorListener {

    void onPerformSteganographySuccessful();

    void onPerformSteganographyFailure();
  }
}
