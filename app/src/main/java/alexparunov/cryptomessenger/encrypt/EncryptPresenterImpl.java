package alexparunov.cryptomessenger.encrypt;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import alexparunov.cryptomessenger.R;
import alexparunov.cryptomessenger.utils.Constants;

class EncryptPresenterImpl implements EncryptPresenter {

  private EncryptView mView;
  private static final int IMAGE_SIZE = 600;
  private int whichImage = -1;

  EncryptPresenterImpl(EncryptView encryptView) {
    this.mView = encryptView;
  }

  @Override
  public SharedPreferences getSharedPreferences() {

    return null;
  }

  @Override
  public void selectCoverImage(String tempPath) {

    Bitmap bitmap;
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmap = BitmapFactory.decodeFile(tempPath, bitmapOptions);

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger" + File.separator + "CoverImage";

    File folder = new File(path);
    if (!folder.exists()) {
      if (folder.mkdirs()) {
        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
        whichImage = Constants.COVER_IMAGE;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
      whichImage = Constants.COVER_IMAGE;
      compressFile(file, scaledBitmap);
    }
  }

  @Override
  public void selectCoverImageCamera() {
    mView.showProgressDialog();

    File file = new File(Environment.getExternalStorageDirectory().toString());
    for (File temp : file.listFiles()) {
      if (temp.getName().equals("temp.jpg")) {
        file = temp;
        break;
      }
    }

    Bitmap bitmap;
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
    file.delete();

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger" + File.separator + "CoverImage";
    File folder = new File(path);

    if (!folder.exists()) {
      if (folder.mkdirs()) {
        file = new File(path, String.valueOf(System.currentTimeMillis() + ".jped"));
        whichImage = Constants.COVER_IMAGE;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
      whichImage = Constants.COVER_IMAGE;
      compressFile(file, scaledBitmap);
    }
  }

  @Override
  public void selectSecretImage(String tempPath) {
    Bitmap bitmap;
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmap = BitmapFactory.decodeFile(tempPath, bitmapOptions);

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger" + File.separator + "CoverImage";

    File folder = new File(path);
    if (!folder.exists()) {
      if (folder.mkdirs()) {
        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
        whichImage = Constants.SECRET_IMAGE;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
      whichImage = Constants.SECRET_IMAGE;
      compressFile(file, scaledBitmap);
    }
  }

  @Override
  public void selectSecretImageCamera() {
    mView.showProgressDialog();

    File file = new File(Environment.getExternalStorageDirectory().toString());
    for (File temp : file.listFiles()) {
      if (temp.getName().equals("temp.jpg")) {
        file = temp;
        break;
      }
    }

    Bitmap bitmap;
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
    file.delete();

    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);

    String path = Environment.getExternalStorageDirectory() + File.separator + "CryptoMessenger" + File.separator + "CoverImage";
    File folder = new File(path);

    if (!folder.exists()) {
      if (folder.mkdirs()) {
        file = new File(path, String.valueOf(System.currentTimeMillis() + ".jpg"));
        whichImage = Constants.SECRET_IMAGE;
        compressFile(file, scaledBitmap);
      } else {
        showParsingImageError();
      }
    } else {
      file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
      whichImage = Constants.SECRET_IMAGE;
      compressFile(file, scaledBitmap);
    }
  }

  private void compressFile(File file, Bitmap bitmap) {
    try {
      OutputStream outputStream;
      outputStream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
      outputStream.flush();
      outputStream.close();

      if (whichImage == Constants.COVER_IMAGE) {
        mView.setCoverImage(file);
      } else if (whichImage == Constants.SECRET_IMAGE) {
        mView.setSecretImage(file);
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

}
