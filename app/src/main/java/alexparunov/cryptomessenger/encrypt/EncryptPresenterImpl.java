package alexparunov.cryptomessenger.encrypt;

import android.content.SharedPreferences;

public class EncryptPresenterImpl implements EncryptPresenter {

  private EncryptView encryptView;

  EncryptPresenterImpl(EncryptView encryptView) {
    this.encryptView = encryptView;
  }

  @Override
  public SharedPreferences getSharedPreferences() {
    return null;
  }

  @Override
  public void selectCoverImage() {

  }

  @Override
  public void selectSecretImage() {

  }

  @Override
  public void saveUserPreferences() {

  }

  @Override
  public void showUserPreferredInfo() {

  }
}
