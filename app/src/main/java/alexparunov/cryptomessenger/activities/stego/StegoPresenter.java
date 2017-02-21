package alexparunov.cryptomessenger.activities.stego;

/**
 * Created by Alexander Parunov on 2/21/17.
 */

public interface StegoPresenter {

  boolean saveStegoImage(String stegoPath);

  void shareStegoImage(String stegoPath);

}
