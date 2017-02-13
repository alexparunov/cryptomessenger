package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;

import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class Embedding {

  @Nullable
  public static Bitmap embedSecretImage(Bitmap coverImage, Bitmap secretImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    String sImageInBin = HelperMethods.bitmapToBinaryStream(secretImage);

    int secretImageLen = sImageInBin.length();
    int action1, action2;
    int embImPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret image is too long (6 bits in each pixel)
    if (secretImageLen > width * height * 6) {
      return null;
    }

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embImPos < secretImageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embImPos == secretImageLen) {
              break;
            }

            //Action for LSB1
            action1 = action1(colors[c], sImageInBin.charAt(embImPos));
            colors[c] += action1;
            embImPos++;

            if (embImPos == secretImageLen) {
              break;
            }

            //Action for LSB2
            action2 = action2(colors[c], sImageInBin.charAt(embImPos));
            colors[c] += action2;
            embImPos++;

          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {
          break outerloop;
        }
      }
    }

    return stegoImage;
  }

  @Nullable
  public static Bitmap embedSecretText(String secretText, Bitmap coverImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    String sTextInBin = HelperMethods.stringToBinaryStream(secretText);

    int secretMessageLen = sTextInBin.length();
    int action1, action2;
    int embMesPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret message is too long (6 bits in each pixel)
    if (secretMessageLen > width * height * 6) {
      return null;
    }

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embMesPos < secretMessageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embMesPos == secretMessageLen) {
              break;
            }

            //Action for LSB1
            action1 = action1(colors[c], sTextInBin.charAt(embMesPos));
            colors[c] += action1;
            embMesPos++;

            if (embMesPos == secretMessageLen) {
              break;
            }

            //Action for LSB2
            action2 = action2(colors[c], sTextInBin.charAt(embMesPos));
            colors[c] += action2;
            embMesPos++;

          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {
          break outerloop;
        }
      }
    }

    return stegoImage;
  }

  private static int LSB1(int number) {
    return number & 1;
  }

  private static int action1(int color, char bit) {
    if (LSB1(color) == 1 && bit == '0') {
      return -1;
    } else if (LSB1(color) == 0 && bit == '1') {
      return 1;
    } else {
      return 0;
    }
  }

  private static int LSB2(int number) {
    return (number >> 1) & 1;
  }

  private static int action2(int color, char bit) {
    if (LSB2(color) == 1 && bit == '0') {
      return -2;
    } else if (LSB2(color) == 0 && bit == '1') {
      return 2;
    } else {
      return 0;
    }
  }

}
