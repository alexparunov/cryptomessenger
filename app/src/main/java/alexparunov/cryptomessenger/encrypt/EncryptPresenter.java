package alexparunov.cryptomessenger.encrypt;

import android.content.SharedPreferences;

public interface EncryptPresenter {

  SharedPreferences getSharedPreferences();

  void showUserPreferredInfo();

  void selectCoverImage();

  void selectSecretImage();

  void saveUserPreferences();
}
