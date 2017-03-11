package alexparunov.cryptomessenger.activities.encrypt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import alexparunov.cryptomessenger.R;
import alexparunov.cryptomessenger.utils.Constants;
import alexparunov.cryptomessenger.utils.StandardMethods;

class EncryptPresenterImpl implements EncryptPresenter, EncryptInteractorImpl.EncryptInteractorListener {

  private EncryptView mView;
  private EncryptInteractor mInteractor;
  private int whichImage = -1;
  private Bitmap coverImage, secretImage;

  EncryptPresenterImpl(EncryptView encryptView) {
    this.mView = encryptView;
    mInteractor = new EncryptInteractorImpl(this);
  }

  @Override
  public void selectImage(int whichImage, String tempPath) {
    mView.showProgressDialog();

    int IMAGE_SIZE = 1000;

    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap bitmap = BitmapFactory.decodeFile(tempPath, bitmapOptions);

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);

    //We want to be able to hide secret image in cover image, so size should be less
    //200x200 image
    if (whichImage == Constants.SECRET_IMAGE) {
      IMAGE_SIZE  = IMAGE_SIZE/3 - 133;
    }

    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);
    scaledBitmap.setPremultiplied(false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger";

    File folder = new File(path);

    String photoName = getPhotoName(whichImage);
    if (!folder.exists()) {
      if (folder.mkdirs()) {
        File file = new File(path, photoName);
        this.whichImage = whichImage;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      File file = new File(path, photoName);
      this.whichImage = whichImage;
      compressFile(file, scaledBitmap);
    }
  }

  @Override
  public void selectImageCamera(int whichImage) {
    mView.showProgressDialog();

    int IMAGE_SIZE = 1000;

    File file = new File(Environment.getExternalStorageDirectory().toString());
    for (File temp : file.listFiles()) {
      if (temp.getName().equals("temp.png")) {
        file = temp;
        break;
      }
    }

    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
    file.delete();

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);

    //We want to be able to hide secret image in cover image, so size should be less
    //200x200 image
    if (whichImage == Constants.SECRET_IMAGE) {
      IMAGE_SIZE  = IMAGE_SIZE/3 - 133;
    }

    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);
    scaledBitmap.setPremultiplied(false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger";
    File folder = new File(path);

    String photoName = getPhotoName(whichImage);
    if (!folder.exists()) {
      if (folder.mkdirs()) {
        file = new File(path, photoName);
        this.whichImage = whichImage;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      file = new File(path, photoName);
      this.whichImage = whichImage;
      compressFile(file, scaledBitmap);
    }
  }

  private String getPhotoName(int whichImage) {
    return whichImage == Constants.COVER_IMAGE ?
      System.currentTimeMillis() % 10000 + "_cover_image.png" :
      System.currentTimeMillis() % 10000 + "_secret_image.png";

  }

  private void compressFile(File file, Bitmap bitmap) {
    try {
      OutputStream outputStream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
      outputStream.flush();
      outputStream.close();

      if (whichImage == Constants.COVER_IMAGE) {
        this.coverImage = bitmap;
        mView.setCoverImage(file);
      } else if (whichImage == Constants.SECRET_IMAGE) {
        this.secretImage = bitmap;
        mView.setSecretImage(file);
      } else {
        showParsingImageError();
      }
    } catch (Exception e) {
      e.printStackTrace();
      showParsingImageError();
    }
  }

  private void showParsingImageError() {
    mView.stopProgressDialog();
    mView.showToast(R.string.compress_error);
  }

  @Override
  public void encryptText() {
    if (coverImage == null) {
      mView.stopProgressDialog();
      mView.showToast(R.string.cover_image_empty);
      return;
    }

    mView.showProgressDialog();
    mInteractor.performSteganography(mView.getSecretMessage(), coverImage, null);
  }

  @Override
  public void encryptImage() {
    if (coverImage == null) {
      coverImage = mView.getCoverImage();
      mView.stopProgressDialog();
      mView.showToast(R.string.cover_image_empty);
      return;
    }

    if (secretImage == null) {
      secretImage = mView.getSecretImage();
      mView.stopProgressDialog();
      mView.showToast(R.string.secret_image_empty);
      return;
    }

    mView.showProgressDialog();
    mInteractor.performSteganography(null, coverImage, secretImage);
  }

  @Override
  public void onPerformSteganographySuccessful(Bitmap stegoImage) {
    mView.stopProgressDialog();
    mView.showToast(R.string.encrypted_success);
    String filePath = storeStegoImage(stegoImage);
    mView.startStegoActivity(filePath);
  }

  @Override
  public void onPerformSteganographyFailure() {
    mView.stopProgressDialog();
    mView.showToast(R.string.secret_message_long);
  }

  private String storeStegoImage(Bitmap stegoImage) {
    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger";

    File folder = new File(path);
    File file = null;
    String filePath = "";

    if (!folder.exists()) {
      if (folder.mkdirs()) {
        file = new File(path,"SI_"+System.currentTimeMillis() + ".png");
      } else {
        showParsingImageError();
      }
    } else {
      file = new File(path,"SI_"+System.currentTimeMillis() + ".png");
    }

    if (file != null) {
      try {
        FileOutputStream fos = new FileOutputStream(file);
        stegoImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        fos.flush();
        fos.close();

        filePath = file.getAbsolutePath();

      } catch (FileNotFoundException e1) {
        StandardMethods.showLog("EPI/Error", e1.getMessage());
      } catch (IOException e2) {
        StandardMethods.showLog("EPI/Error", e2.getMessage());
      }
    }

    return filePath;
  }
}
