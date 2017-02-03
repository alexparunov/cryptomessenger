package alexparunov.cryptomessenger.encrypt;

import android.content.SharedPreferences;

public interface EncryptPresenter {

  SharedPreferences getSharedPreferences();

  void selectCoverImage(String path);
  void selectCoverImageCamera();

  void selectSecretImage(String path);
  void selectSecretImageCamera();

}
