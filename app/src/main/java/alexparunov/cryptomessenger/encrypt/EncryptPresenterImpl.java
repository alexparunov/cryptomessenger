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
  public void selectCoverImage(String path) {

  }

  @Override
  public void selectCoverImageCamera() {

  }

  @Override
  public void selectSecretImage(String path) {

  }

  @Override
  public void selectSecretImageCamera() {

  }

  @Override
  public void saveUserPreferences() {

  }

  @Override
  public void showUserPreferredInfo() {

  }
}
