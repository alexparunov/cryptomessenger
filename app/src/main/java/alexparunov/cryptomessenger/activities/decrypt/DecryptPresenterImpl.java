package alexparunov.cryptomessenger.activities.decrypt;

import java.io.File;


/**
 * Created by Alexander Parunov on 3/9/17.
 */

class DecryptPresenterImpl implements DecryptPresenter {

  private DecryptView mView;
  private String stegoImagePath = "";

  DecryptPresenterImpl(DecryptView decryptView) {
    this.mView = decryptView;
  }

  @Override
  public void selectImage(String path) {
    mView.showProgressDialog();

    File stegoFile = new File(path);

    stegoImagePath = path;

    mView.setStegoImage(stegoFile);
  }

  @Override
  public void decryptMessage() {

  }
}
