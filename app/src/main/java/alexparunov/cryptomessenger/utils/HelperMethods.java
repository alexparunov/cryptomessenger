package alexparunov.cryptomessenger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class HelperMethods {

  public static byte[] bitmapToByArray(Bitmap bitmap) {

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

    return stream.toByteArray();
  }

  public static Bitmap byteArrayToBitmap(byte[] byteArray) {

    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
  }
}
