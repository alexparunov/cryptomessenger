package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class Embedding {

  public static Bitmap embedSecretImage(Bitmap coverImage, Bitmap secretImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    return stegoImage;
  }

  public static Bitmap embedSecretText(String secretText, Bitmap coverImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    String sTextInBin = HelperMethods.stringToBinaryStream(secretText);

    int secretMessageLen = sTextInBin.length();
    int action;
    int embMesPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret message is too long
    if(secretMessageLen >= (width + height)*16) {
      return null;
    }

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embMesPos < secretMessageLen) {
          int colors[] = { Color.red(pixel), Color.blue(pixel), Color.green(pixel) };

          for (int c = 0; c < 3; c++) {
            if (embMesPos == secretMessageLen) {
              break;
            }

            action = action(colors[c], sTextInBin.charAt(embMesPos));
            colors[c] += action;

            embMesPos++;
          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          StandardMethods.showLog("EMB", "After: " + newPixel);
          stegoImage.setPixel(x, y, newPixel);
        } else {
          break outerloop;
        }
      }
    }

    return stegoImage;
  }

  private static int LSB(int number) {
    return number & 1;
  }

  private static int action(int color, char letter) {
    if (LSB(color) == 1 && letter == '0') {
      return -1;
    } else if (LSB(color) == 0 && letter == '1') {
      return +1;
    } else {
      return 0;
    }
  }
}
