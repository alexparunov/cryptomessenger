package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;

import alexparunov.cryptomessenger.utils.HelperMethods;

public class Embedding {

  public static Bitmap embedSecretImage(Bitmap coverImage, Bitmap secretImage) {
    Bitmap stegoImage = null;

    return stegoImage;
  }

  public static Bitmap embedSecretText(String secretText, Bitmap coverImage) {
    Bitmap stegoImage = null;
    String secretTextInBinaryStream = HelperMethods.stringToBinaryStream(secretText);

    return stegoImage;
  }
}
