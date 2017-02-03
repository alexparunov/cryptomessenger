package alexparunov.cryptomessenger.encrypt;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.squareup.picasso.Picasso;

import alexparunov.cryptomessenger.R;
import alexparunov.cryptomessenger.utils.Constants;
import alexparunov.cryptomessenger.utils.StandardMethods;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class EncryptActivity extends AppCompatActivity implements EncryptView {

  @BindView(R.id.etSecretMessage)
  EditText etSecretMessage;
  @BindView(R.id.ivCoverImage)
  ImageView ivCoverImage;
  @BindView(R.id.ivSecretImage)
  ImageView ivSecretImage;

  @BindView(R.id.rbText)
  RadioButton rbText;
  @BindView(R.id.rbImage)
  RadioButton rbImage;

  @OnCheckedChanged({R.id.rbText, R.id.rbImage})
  public void onRadioButtonClick() {
    if (rbImage.isChecked()) {
      etSecretMessage.setVisibility(View.GONE);
      ivSecretImage.setVisibility(View.VISIBLE);
    } else if (rbText.isChecked()) {
      etSecretMessage.setVisibility(View.VISIBLE);
      ivSecretImage.setVisibility(View.GONE);
    }
  }

  @OnClick({R.id.ivCoverImage, R.id.ivSecretImage})
  public void onCoverSecretImageClick() {

    final CharSequence[] items = {
      getString(R.string.take_image_dialog),
      getString(R.string.select_image_dialog)
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(EncryptActivity.this);
    builder.setTitle(getString(R.string.select_image_title));
    builder.setCancelable(false);
    builder.setItems(items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int item) {
        if (items[item].equals(getString(R.string.take_image_dialog))) {

          if (ContextCompat.checkSelfPermission(getApplicationContext(),
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(getApplicationContext(),
              Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(EncryptActivity.this,
              new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
              Constants.PERMISSIONS_CAMERA);

          } else {
            //openCamera();
          }
        } else if(items[item].equals(getString(R.string.select_image_dialog))) {

          if(ContextCompat.checkSelfPermission(getApplicationContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(EncryptActivity.this,
              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
              Constants.PERMISSIONS_EXTERNAL_STORAGE);

          } else {
            //chooseImage()
          }
        }
      }
    });

    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }
    });

    builder.show();
  }

  ProgressDialog progressDialog;
  EncryptPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_encrypt);

    ButterKnife.bind(this);

    initToolbar();

    progressDialog = new ProgressDialog(EncryptActivity.this);
    progressDialog.setMessage("Please wait...");

    mPresenter = new EncryptPresenterImpl(this);
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
  public Bitmap getCoverImage() {
    return ((BitmapDrawable) ivCoverImage.getDrawable()).getBitmap();
  }

  @Override
  public void setCoverImage(String filePath) {
    Picasso.with(this)
      .load(filePath)
      .placeholder(R.mipmap.ic_launcher)
      .into(ivCoverImage);
  }

  @Override
  public Bitmap getSecretImage() {
    return ((BitmapDrawable) ivSecretImage.getDrawable()).getBitmap();
  }

  @Override
  public void setSecretImage(String filePath) {
    Picasso.with(this)
      .load(filePath)
      .placeholder(R.mipmap.ic_launcher)
      .into(ivSecretImage);
  }

  @Override
  public String getSecretMessage() {
    return etSecretMessage.getText().toString().trim();
  }

  @Override
  public void setSecretMessage(String secretMessage) {
    etSecretMessage.setText(secretMessage);
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
