package alexparunov.cryptomessenger.activities.stego;

import android.content.Intent;

/**
 * Created by Alexander Parunov on 2/21/17.
 */

interface StegoView {

  void showToast(int message);

  void showProgressDialog();

  void stopProgressDialog();

  void initToolbar();

  void setStegoImage(String path);

  void saveToMedia(Intent intent);
}
