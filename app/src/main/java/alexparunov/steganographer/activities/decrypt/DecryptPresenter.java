package alexparunov.steganographer.activities.decrypt;

/**
 * Created by Alexander Parunov on 3/9/17.
 */

interface DecryptPresenter {

  /**
   * Opens a media chooser to select an image from Internal Storage
   * @param path to selected image
   */
  void selectImage(String path);

  /**
   * Interacts with interactor to start decryption process
   */
  void decryptMessage();
}
