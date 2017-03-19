package alexparunov.cryptomessenger.activities.decrypt;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Alexander Parunov on 3/9/17.
 */

interface DecryptView {

  Bitmap getStegoImage();

  void initToolbar();

  void setStegoImage(File file);

  void showToast(int message);

  void chooseImage();

  void showProgressDialog();

  void stopProgressDialog();

  void startDecryptResultActivity(String secretMessage, String secretImagePath);
}
