package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import alexparunov.cryptomessenger.utils.Constants;
import alexparunov.cryptomessenger.utils.HelperMethods;
import alexparunov.cryptomessenger.utils.StandardMethods;

public class Extracting {

  public static Map extractSecretMessage(Bitmap stegoImage) {
    Map map = new HashMap();

    StringBuilder secretMessage = new StringBuilder();

    int width = stegoImage.getWidth();
    int height = stegoImage.getHeight();

    int keyPos = 0;

    int key[] = new int[24];

    //Extract Key
    int keyPixel = stegoImage.getPixel(0, 0);

    int red = Color.red(keyPixel);
    int green = Color.green(keyPixel);
    int blue = Color.blue(keyPixel);

    StandardMethods.showLog("EXT","Key2: "+red+" "+green+" "+blue);

    int k = 7;
    for (int i = 7; i >= 0; --i) {
      key[k] = red % 2;
      red /= 2;
      k--;
    }

    k = 13;
    for (int i = 7; i >= 0; --i) {
      key[k] = green % 2;
      green /= 2;
      k--;
    }

    k = 23;
    for (int i = 7; i >= 0; --i) {
      key[k] = blue % 2;
      blue /= 2;
      k--;
    }

    for(int a = 0; a < 24;a++) Log.w("EXT",key[a]+"");

    int typePixel = stegoImage.getPixel(0, 1);
    int tRed = Color.red(typePixel);
    int tGreen = Color.green(typePixel);
    int tBlue = Color.blue(typePixel);

    if (tRed == 0 && tGreen == 0 && tBlue == Constants.COLOR_RGB_TEXT) {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_TEXT);

    } else if (tRed == 0 && tGreen == 0 && tBlue == Constants.COLOR_RGB_IMAGE) {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_IMAGE);

    } else {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_UNDEFINED);
      map.put(Constants.MESSAGE_BITS, "");
      return map;

    }

    outerloop:
    for (int x = 0; x < width; ++x) {
      for (int y = 2; y < height; ++y) {
        int pixel = stegoImage.getPixel(x, y);

        int colors[] = { Color.red(pixel), Color.green(pixel), Color.blue(pixel) };

        if (colors[0] == 0 && colors[1] == 0 && colors[2] == Constants.COLOR_RGB_END) {
          break outerloop;
        } else {

          for (int c = 0; c < 3; c++) {

            if ((key[keyPos] ^ LSB2(colors[c])) == 1) {
              int lsb = LSB(colors[c]);
              secretMessage.append(lsb);
              keyPos = (keyPos + 1) % key.length;
            }
          }
        }
      }
    }

    String secretMessageStr = secretMessage.toString();
    int secretLen = secretMessageStr.length();

    //Cut unnecessary (0-8) pixels
    String secretMessageFinal = secretMessageStr.substring(0, secretLen - secretLen % 8);
    byte[] messageBytes = HelperMethods.bitsStreamToByteArray(secretMessageFinal);
    String message = new String(messageBytes);

    StandardMethods.showLog("EXT","Decrypted Message: "+secretMessageFinal);
    StandardMethods.showLog("EXT","Decrypted Message Length: "+secretMessageFinal.length());

    map.put(Constants.MESSAGE_BITS, message);
    return map;
  }

  private static int LSB(int number) {
    return number & 1;
  }

  private static int LSB2(int number) {
    return (number >> 1) & 1;
  }
}
