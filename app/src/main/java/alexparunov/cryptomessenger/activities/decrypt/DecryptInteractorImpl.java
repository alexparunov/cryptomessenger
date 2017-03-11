package alexparunov.cryptomessenger.activities.decrypt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.util.Map;

import alexparunov.cryptomessenger.algorithms.Extracting;

/**
 * Created by Alexander Parunov on 3/10/17.
 */

class DecryptInteractorImpl implements DecryptInteractor {

  DecryptInteractorListener mListener;

  DecryptInteractorImpl(DecryptInteractorListener listener) {
    this.mListener = listener;
  }

  @Override
  public void performDecryption(String path) {
    if (!path.isEmpty()) {
      new ExtractSecretMessage(path).execute();
    } else {
      mListener.onPerformDecryptionFailure();
    }
  }

  private class ExtractSecretMessage extends AsyncTask<Void, Void, Map> {

    String stegoImagePath;

    ExtractSecretMessage(String stegoImagePath) {
      this.stegoImagePath = stegoImagePath;
    }


    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected Map doInBackground(Void... params) {
      Map map = null;
      Bitmap stegoImage = null;

      if (!stegoImagePath.isEmpty()) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inScaled = false;
        options.inPremultiplied = false;

        stegoImage = BitmapFactory.decodeFile(stegoImagePath, options);
      }

      if (stegoImage != null) {
        map = Extracting.extractSecretMessage(stegoImage);
      }

      return map;
    }

    @Override
    protected void onPostExecute(Map map) {
      if (map != null) {
        mListener.onPerformDecryptionSuccess(map);
      } else {
        mListener.onPerformDecryptionFailure();
      }
    }
  }

  interface DecryptInteractorListener {

    void onPerformDecryptionSuccess(Map map);

    void onPerformDecryptionFailure();
  }
}
