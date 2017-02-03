package alexparunov.cryptomessenger.encrypt;

import android.graphics.Bitmap;

public interface EncryptView {

  String getSecretMessage();

  Bitmap getCoverImage();

  Bitmap getSecretImage();

  void initToolbar();

  void setSecretMessage(String secretMessage);

  void setCoverImage(String filePath);

  void setSecretImage(String filePath);

  void showToast(int message);

  void showProgressDialog();

  void stopProgressDialog();

}
