package alexparunov.cryptomessenger.activities.stego;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import alexparunov.cryptomessenger.R;
import alexparunov.cryptomessenger.utils.Constants;
import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class StegoActivity extends AppCompatActivity implements StegoView {


  private ProgressDialog progressDialog;
  private Bitmap stegoImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stego);

    Intent intent = getIntent();
    if (intent != null) {
      byte[] byteArrayExtra = intent.getByteArrayExtra(Constants.EXTRA_STEGO_IMAGE_ARRAY);
      stegoImage = HelperMethods.byteArrayToBitmap(byteArrayExtra);
      setStegoImage(stegoImage);
    }

    initToolbar();
    progressDialog = new ProgressDialog(StegoActivity.this);
    progressDialog.setMessage("Please wait...");
  }

  @Override
  public void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public Bitmap getStegoImage() {
    return stegoImage;
  }

  @Override
  public void setStegoImage(Bitmap stegoImage) {
    this.stegoImage = stegoImage;
  }

  @Override
  public void showToast(int message) {
    StandardMethods.showToast(this, message);
  }

  @Override
  public void showProgressDialog() {
    if (progressDialog != null && !progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  @Override
  public void stopProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
}
