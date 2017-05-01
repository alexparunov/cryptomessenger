package alexparunov.stegomessenger.activities_fragments.decrypt;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import alexparunov.stegomessenger.R;
import alexparunov.stegomessenger.utils.Constants;
import alexparunov.stegomessenger.utils.StandardMethods;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DecryptFragment extends Fragment implements DecryptView {

  @BindView(R.id.ivStegoImage)
  ImageView ivStegoImage;

  @OnClick(R.id.ivStegoImage)
  public void onStegoImageClick() {
    if (ContextCompat.checkSelfPermission(getActivity(),
      Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

      ActivityCompat.requestPermissions(getActivity(),
        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
        Constants.PERMISSIONS_EXTERNAL_STORAGE);

    } else {
      chooseImage();
    }

  }

  @OnClick(R.id.bDecrypt)
  public void onButtonClick() {
    if (isSISelected) {
      mPresenter.decryptMessage();
    } else {
      showToast(R.string.stego_image_not_selected);
    }
  }

  private ProgressDialog progressDialog;
  private DecryptPresenter mPresenter;
  private boolean isSISelected = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    progressDialog = new ProgressDialog(getContext());
    progressDialog.setMessage("Please wait...");

    mPresenter = new DecryptPresenterImpl(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_decrypt, container, false);

    ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case Constants.PERMISSIONS_EXTERNAL_STORAGE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          chooseImage();
        }
        break;
    }
  }

  @Override
  public void chooseImage() {
    Intent intent = new Intent(
      Intent.ACTION_PICK,
      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*");
    startActivityForResult(
      Intent.createChooser(intent, getString(R.string.choose_image)),
      Constants.SELECT_FILE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) {
      if (requestCode == Constants.SELECT_FILE) {
        Uri selectedImageUri = data.getData();
        String tempPath = getPath(selectedImageUri, getActivity());
        if (tempPath != null) {
          mPresenter.selectImage(tempPath);
        }
      }
    }
  }

  @Override
  public void startDecryptResultActivity(String secretMessage, String secretImagePath) {
    Intent intent = new Intent(getContext(), DecryptResultActivity.class);

    if (secretMessage != null) {
      intent.putExtra(Constants.EXTRA_SECRET_TEXT_RESULT, secretMessage);
    }

    if (secretImagePath != null) {
      intent.putExtra(Constants.EXTRA_SECRET_IMAGE_RESULT, secretImagePath);
    }

    startActivity(intent);
  }

  public String getPath(Uri uri, Activity activity) {
    String[] projection = {MediaStore.MediaColumns.DATA};
    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
    cursor.moveToFirst();
    return cursor.getString(column_index);
  }

  @Override
  public Bitmap getStegoImage() {
    return ((BitmapDrawable) ivStegoImage.getDrawable()).getBitmap();
  }

  @Override
  public void setStegoImage(File file) {
    showProgressDialog();
    Picasso.with(getContext())
      .load(file)
      .fit()
      .placeholder(R.mipmap.ic_launcher)
      .into(ivStegoImage);
    stopProgressDialog();
    isSISelected = true;
  }

  @Override
  public void showToast(int message) {
    StandardMethods.showToast(getContext(), message);
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
