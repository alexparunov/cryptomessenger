package alexparunov.stegomessenger.activities_fragments.decrypt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.util.Map;

import alexparunov.stegomessenger.R;
import alexparunov.stegomessenger.algorithms.Extracting;
import alexparunov.stegomessenger.utils.Constants;
import alexparunov.stegomessenger.utils.HelperMethods;

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
      mListener.onPerformDecryptionFailure(R.string.decrypt_fail);
    }
  }

  /**
   * AsyncTask which extracts a message in background in order not to block main frame
   */
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

        //Should be false so that set pixels are not pre-multiplied by alpha value
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

        //Either TEXT, IMAGE, or UNDEFINED
        int type = (int) map.get(Constants.MESSAGE_TYPE);

        /**
         * In case of TEXT or IMAGE stream of bits is extract from result map,
         * converted to byte array which is used to construct final String or Bitmap
         */
        if (type == Constants.TYPE_TEXT) {
          String bits = (String) map.get(Constants.MESSAGE_BITS);
          if(bits.isEmpty()) {
            mListener.onPerformDecryptionFailure(R.string.decrypt_fail);
            return;
          }
          byte[] messageBytes = HelperMethods.bitsStreamToByteArray(bits);
          String message = new String(messageBytes);
          mListener.onPerformDecryptionSuccessText(message);
        } else if (type == Constants.TYPE_IMAGE) {

          String bits = (String) map.get(Constants.MESSAGE_BITS);
          if(bits.isEmpty()) {
            mListener.onPerformDecryptionFailure(R.string.decrypt_fail);
            return;
          }
          byte[] imageBytes = HelperMethods.bitsStreamToByteArray(bits);
          Bitmap bitmap = HelperMethods.byteArrayToBitmap(imageBytes);
          mListener.onPerformDecryptionSuccessImage(bitmap);

        } else if (type == Constants.TYPE_UNDEFINED) {
          mListener.onPerformDecryptionFailure(R.string.non_stego_image_selected);
        }
      } else {
        mListener.onPerformDecryptionFailure(R.string.decrypt_fail);
      }
    }
  }

  interface DecryptInteractorListener {

    /**
     * Invoked when secret text is successfully decrypted
     *
     * @param text is decrypted text
     */
    void onPerformDecryptionSuccessText(String text);

    /**
     * Invoked when secret image is successfully decrypted
     *
     * @param bitmap is decrypted image represented as bitmap
     */
    void onPerformDecryptionSuccessImage(Bitmap bitmap);

    /**
     * Invoked when failure occurred during decryption
     *
     * @param message is id of an error message to be shown stored in @string resources
     */
    void onPerformDecryptionFailure(int message);
  }
}
