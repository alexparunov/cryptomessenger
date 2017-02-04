package alexparunov.cryptomessenger.encrypt;

interface EncryptPresenter {

  void selectImage(int type, String path);

  void selectImageCamera(int type);

  void encryptText();

  void encryptImage();
}
