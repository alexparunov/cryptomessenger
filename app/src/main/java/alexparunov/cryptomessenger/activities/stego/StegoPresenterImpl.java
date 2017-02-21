package alexparunov.cryptomessenger.activities.stego;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import alexparunov.cryptomessenger.R;
import alexparunov.cryptomessenger.utils.StandardMethods;

/**
 * Created by Alexander Parunov on 2/21/17.
 */

public class StegoPresenterImpl implements StegoPresenter {

  private StegoView mView;

  StegoPresenterImpl(StegoView mView) {
    this.mView = mView;
  }

  @Override
  public boolean saveStegoImage(String stegoPath) {
    mView.showProgressDialog();

    String path = Environment.getExternalStorageDirectory() + File.separator
      + "CryptoMessenger" + File.separator + "Saved" + File.separator;

    InputStream in;
    OutputStream out;

    try {

      File folder = new File(path);

      if (!folder.exists()) {
        folder.mkdirs();
      }

      in = new FileInputStream(stegoPath);
      out = new FileOutputStream(path + "SI_" + System.currentTimeMillis() + ".png");

      byte[] buffer = new byte[1024];
      int read;

      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
      in.close();

      out.flush();
      out.close();

      new File(stegoPath).delete();

      mView.stopProgressDialog();
      mView.showToast(R.string.save_image_success);

      return true;

    } catch (FileNotFoundException e1) {

      StandardMethods.showLog("SPI", e1.getMessage());
      showSavingImageError();

      return false;
    } catch (IOException e2) {

      StandardMethods.showLog("SPI", e2.getMessage());
      showSavingImageError();

      return false;
    }
  }

  @Override
  public void shareStegoImage(String stegoPath) {

  }

  private void showSavingImageError() {
    mView.stopProgressDialog();
    mView.showToast(R.string.save_image_error);
  }
}
