package alexparunov.stegonographer.activities.decrypt;

/**
 * Created by Alexander Parunov on 3/10/17.
 */

interface DecryptInteractor {

  /**
   * Starts performing decryption of image selected from Internal Memory
   * @param path to selected image
   *
   */
  void performDecryption(String path);

}
