package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class Embedding {

  @Nullable
  public static Bitmap embedSecretImage(Bitmap coverImage, Bitmap secretImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    String sImageInBin = HelperMethods.bitmapToBinaryStream(secretImage);

    int secretImageLen = sImageInBin.length();
    int action, embImPos = 0, keyPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret image is too long (3 bits in each pixel + skipping)
    if (secretImageLen > width * height * 2) {
      return null;
    }

    //Generate and place random 13 bit array of 0-1 in (0,0) pixel
    int key[] = generateKey();

    //Put [0,3] pixels into red
    int red_sum = -1;
    for(int i=0;i<=3;i++) {
      red_sum += (int) Math.pow(key[i]*2,3-i);
    }

    //Put [4,8] pixels into green
    int green_sum = -1;
    for(int i=4;i<=8;i++) {
      green_sum += (int) Math.pow(key[i]*2,8-i);
    }

    //Put[9,12] pixels into blue
    int blue_sum = -1;
    for(int i=9;i<=12;i++) {
      blue_sum += (int) Math.pow(key[i]*2,12-i);
    }

    int keyPixel = Color.rgb(red_sum,green_sum,blue_sum);
    stegoImage.setPixel(0,0,keyPixel);
    int endX = 0, endY = 1;

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 1; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embImPos < secretImageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embImPos == secretImageLen) {
              break;
            }

            //Action for LSB
            if((key[keyPos] ^ LSB2(colors[c])) == 1) {
              action = action(colors[c], sImageInBin.charAt(embImPos));
              colors[c] += action;
              embImPos++;
              keyPos = (keyPos + 1) % 13;
            }
          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {
          endX = x + 1;
          endY = y + 1;
          break outerloop;
        }
      }
    }

    //End of secret message flag
    stegoImage.setPixel(endX, endY, Color.rgb(0,0,0));

    return stegoImage;
  }

  @Nullable
  public static Bitmap embedSecretText(String secretText, Bitmap coverImage) {
    Bitmap stegoImage = Bitmap.createBitmap(coverImage);

    String sTextInBin = HelperMethods.stringToBinaryStream(secretText);

    int secretMessageLen = sTextInBin.length();
    int action, embMesPos = 0, keyPos = 0;

    int width = coverImage.getWidth();
    int height = coverImage.getHeight();

    //If secret message is too long (3 bits in each pixel + skipping of some pixels)
    if (secretMessageLen > width * height * 2) {
      return null;
    }

    //Generate and place random 13 bit array of 0-1 in (0,0) pixel
    int key[] = generateKey();

    //Put [0,3] pixels into red
    int red_sum = -1;
    for(int i=0;i<=3;i++) {
      red_sum += (int) Math.pow(key[i]*2,3-i);
    }

    //Put [4,8] pixels into green
    int green_sum = -1;
    for(int i=4;i<=8;i++) {
      green_sum += (int) Math.pow(key[i]*2,8-i);
    }

    //Put[9,12] pixels into blue
    int blue_sum = -1;
    for(int i=9;i<=12;i++) {
      blue_sum += (int) Math.pow(key[i]*2,12-i);
    }

    int keyPixel = Color.rgb(red_sum,green_sum,blue_sum);
    stegoImage.setPixel(0,0,keyPixel);
    int endX = 0, endY = 1;

    outerloop:
    for (int x = 0; x < width; x++) {
      for (int y = 1; y < height; y++) {
        int pixel = coverImage.getPixel(x, y);

        if (embMesPos < secretMessageLen) {
          int colors[] = {Color.red(pixel), Color.blue(pixel), Color.green(pixel)};

          for (int c = 0; c < 3; c++) {
            if (embMesPos == secretMessageLen) {
              break;
            }

            //Action for LSB
            if((key[keyPos] ^ LSB2(colors[c])) == 1) {
              action = action(colors[c], sTextInBin.charAt(embMesPos));
              colors[c] += action;
              embMesPos++;
              keyPos = (keyPos + 1) % 13;
            }
          }

          int newPixel = Color.rgb(colors[0], colors[1], colors[2]);
          stegoImage.setPixel(x, y, newPixel);
        } else {
          endX = x + 1;
          endY = y + 1;
          break outerloop;
        }
      }
    }

    //End of secret message flag
    stegoImage.setPixel(endX, endY, Color.rgb(0,0,0));

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
    final int[] bits = {0,1};
    int[] result = new int[13];

    int n,i;
    Random random = new Random();

    for(i=0;i<13;++i) {
      n = random.nextInt(2);
      result[i] = bits[n];
    }
    return result;
  }

}
