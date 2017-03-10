package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;

import java.util.Random;

import alexparunov.cryptomessenger.utils.Constants;
import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class Embedding {

  @Nullable
  public static Bitmap embedSecretImage(Bitmap coverImage, Bitmap secretImage) {
    Bitmap stegoImage = coverImage.copy(Bitmap.Config.RGB_565, true);

    String sImageInBin = HelperMethods.bitmapToBinaryStream(secretImage);
    int secretImageLen = sImageInBin.length();
    int action, embImPos = 0, keyPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret image is too long (3 bits in each pixel + skipping)
    if (secretImageLen > width * height * 2) {
      return null;
    }

    //Generate and place random 16 bit array of 0-1 in (0,0) pixel
    int key[] = generateKey();

    int red_sum = 0;
    for(int j=0;j<=4;++j) {
      int number = (int) Math.pow(key[j]*2,4-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      red_sum += number;
    }

    int green_sum = 0;
    for(int j=5;j<=10;++j) {
      int number = (int) Math.pow(key[j]*2,10-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      green_sum += number;
    }

    int blue_sum = 0;
    for(int j=11;j<=15;++j) {
      int number = (int) Math.pow(key[j]*2,15-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      blue_sum += number;
    }

    stegoImage.setPixel(0, 0, Color.rgb(red_sum, green_sum, blue_sum));

    //To check if secret message is image
    stegoImage.setPixel(0, 1, Color.rgb(Constants.COLOR_RGB_IMAGE,
      Constants.COLOR_RGB_IMAGE,
      Constants.COLOR_RGB_IMAGE));
    int endX = 0, endY = 2;

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 2; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embImPos < secretImageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embImPos == secretImageLen) {
              break;
            }

            //Action for LSB
            if ((key[keyPos] ^ LSB2(colors[c])) == 1) {
              action = action(colors[c], sImageInBin.charAt(embImPos));
              colors[c] += action;
              embImPos++;
              keyPos = (keyPos + 1) % 13;
            }
          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {

          if (y < height - 1) {
            endX = x;
            endY = y + 1;
          } else if (endX < width - 1) {
            endX = x + 1;
            endY = y;
          } else {
            endX = width - 1;
            endY = height - 1;
          }

          break outerloop;
        }
      }
    }

    //End of secret message flag
    stegoImage.setPixel(endX, endY, Color.rgb(Constants.COLOR_RGB_END,
      Constants.COLOR_RGB_END,
      Constants.COLOR_RGB_END));

    return stegoImage;
  }

  @Nullable
  public static Bitmap embedSecretText(String secretText, Bitmap coverImage) {
    Bitmap stegoImage = coverImage.copy(Bitmap.Config.RGB_565, true);

    String sTextInBin = HelperMethods.stringToBinaryStream(secretText);

    int secretMessageLen = sTextInBin.length();
    int action, embMesPos = 0, keyPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret message is too long (3 bits in each pixel + skipping of some pixels)
    if (secretMessageLen > width * height * 2) {
      return null;
    }

    //Generate and place random 16 bit array of 0-1 in (0,0) pixel
    int key[] = generateKey();

    int red_sum = 0;
    for(int j=0;j<=4;++j) {
      int number = (int) Math.pow(key[j]*2,4-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      red_sum += number;
    }

    int green_sum = 0;
    for(int j=5;j<=10;++j) {
      int number = (int) Math.pow(key[j]*2,10-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      green_sum += number;
    }

    int blue_sum = 0;
    for(int j=11;j<=15;++j) {
      int number = (int) Math.pow(key[j]*2,15-j);
      StandardMethods.showLog("EMB",j+": "+key[j]+" num: "+number);
      blue_sum += number;
    }

    StandardMethods.showLog("EMB","(r,g,b)_2: ("+red_sum+","+green_sum+","+blue_sum+")");
    stegoImage.setPixel(0,0, Color.rgb(red_sum,green_sum,blue_sum));
    int pix = stegoImage.getPixel(0,0);
    StandardMethods.showLog("EMB","(r,g,b): ("+Color.red(pix)+","+Color.green(pix)+","+Color.blue(pix)+")");

    //for (int j = 0; j < 16; j++) StandardMethods.showLog("EMB", j+": " + key[j]);

    //To check if secret message is text
    stegoImage.setPixel(0, 1, Color.rgb(Constants.COLOR_RGB_TEXT,
      Constants.COLOR_RGB_TEXT,
      Constants.COLOR_RGB_TEXT));
    int endX = 0, endY = 2;

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 2; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embMesPos < secretMessageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embMesPos == secretMessageLen) {
              break;
            }

            //Action for LSB
            if ((key[keyPos] ^ LSB2(colors[c])) == 1) {
              action = action(colors[c], sTextInBin.charAt(embMesPos));
              colors[c] += action;
              embMesPos++;
              keyPos = (keyPos + 1) % 13;
            }
          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {

          if (y < height - 1) {
            endX = x;
            endY = y + 1;
          } else if (endX < width - 1) {
            endX = x + 1;
            endY = y;
          } else {
            endX = width - 1;
            endY = height - 1;
          }

          break outerloop;
        }
      }
    }

    //End of secret message flag
    stegoImage.setPixel(endX, endY, Color.rgb(Constants.COLOR_RGB_END,
      Constants.COLOR_RGB_END,
      Constants.COLOR_RGB_END));
    return stegoImage;
  }

  private static int LSB(int number) {
    return number & 1;
  }

  private static int LSB2(int number) {
    return (number >> 1) & 1;
  }

  private static int action(int color, char bit) {
    if (LSB(color) == 1 && bit == '0') {
      return -1;
    } else if (LSB(color) == 0 && bit == '1') {
      return 1;
    } else {
      return 0;
    }
  }

  private static int[] generateKey() {
    final int[] bits = {0, 1};
    int[] result = new int[16];

    int n, i;
    Random random = new Random();

    for (i = 0; i < 16; ++i) {
      n = random.nextInt(2);
      result[i] = bits[n];
    }
    return result;
  }

}
