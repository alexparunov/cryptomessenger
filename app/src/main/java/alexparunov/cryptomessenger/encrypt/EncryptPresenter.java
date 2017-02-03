package alexparunov.cryptomessenger.encrypt;

import android.content.SharedPreferences;

public interface EncryptPresenter {

  SharedPreferences getSharedPreferences();

  void showUserPreferredInfo();

  void selectCoverImage(String path);
  void selectCoverImageCamera();

  void selectSecretImage(String path);
  void selectSecretImageCamera();

  void saveUserPreferences();
}
