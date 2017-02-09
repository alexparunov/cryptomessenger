package alexparunov.cryptomessenger.activities.stego;

import android.graphics.Bitmap;

public interface StegoView {

  Bitmap getStegoImage();

  void setStegoImage(Bitmap stegoImage);

  void showToast(int message);

  void showProgressDialog();

  void stopProgressDialog();

  void initToolbar();
}
