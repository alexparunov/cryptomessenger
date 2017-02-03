package alexparunov.cryptomessenger.encrypt;

interface EncryptPresenter {

  void selectCoverImage(String path);
  void selectCoverImageCamera();

  void selectSecretImage(String path);
  void selectSecretImageCamera();

  void encryptText();
  void encryptImage();
}
