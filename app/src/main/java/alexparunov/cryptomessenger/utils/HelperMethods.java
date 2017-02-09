package alexparunov.cryptomessenger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class HelperMethods {

  public static byte[] bitmapToByArray(Bitmap bitmap) {

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

    return stream.toByteArray();
  }

  public static Bitmap byteArrayToBitmap(byte[] byteArray) {

    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
  }

  public static String stringToBinaryStream(String string) {
    byte[] stringInBytes = string.getBytes(Charset.forName("UTF-8"));
    StringBuilder binary = new StringBuilder();

    for (byte b : stringInBytes) {
      int val = b;
      for (int i = 0; i < 8; i++) {
        binary.append((val & 128) == 0 ? 0 : 1);
        val <<= 1;
      }
    }

    return binary.toString();
  }
}
