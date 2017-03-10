package alexparunov.cryptomessenger.activities.decrypt;

import java.io.File;
import java.util.Map;

import alexparunov.cryptomessenger.R;


/**
 * Created by Alexander Parunov on 3/9/17.
 */

class DecryptPresenterImpl implements DecryptPresenter, DecryptInteractorImpl.DecryptInteractorListener{

  private DecryptView mView;
  private DecryptInteractor mInteractor;
  private String stegoImagePath = "";

  DecryptPresenterImpl(DecryptView decryptView) {

    this.mView = decryptView;
    this.mInteractor = new DecryptInteractorImpl(this);
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
    if(stegoImagePath.isEmpty()) {
      mView.showToast(R.string.stego_image_not_selected);
    } else {
      mInteractor.performDecryption(stegoImagePath);
    }
  }

  @Override
  public void onPerformDecryptionSuccess(Map map) {

  }

  @Override
  public void onPerformDecryptionFailure() {
    mView.stopProgressDialog();
    mView.showToast(R.string.decrypt_fail);
  }
}
