package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Extracting {

  public static Map extractSecretMessage(Bitmap stegoImage) {
    Map map = new HashMap();

    StringBuilder secretMessage = new StringBuilder();

    int width = stegoImage.getWidth();
    int height = stegoImage.getHeight();

    int key[] = new int[16];

    //Extract Key
    int keyPixel = stegoImage.getPixel(0, 0);

    int red = Color.red(keyPixel);
    int green = Color.green(keyPixel);
    int blue = Color.blue(keyPixel);

    int k = 4;
    int redA[] = new int[5];
    for (int i = 4; i >= 0; --i) {
      redA[i] = red % 2;
      red /= 2;
      key[k] = redA[i];
      k--;
    }

    k = 10;
    int greenA[] = new int[6];
    for (int i = 5; i >= 0; --i) {
      greenA[i] = green % 2;
      green /= 2;
      key[k] = greenA[i];
      k--;
    }

    k = 15;
    int blueA[] = new int[5];
    for (int i = 4; i >= 0; --i) {
      blueA[i] = blue % 2;
      blue /= 2;
      key[k] = blueA[i];
      k--;
    }


    for (int x = 0; x < width; ++x) {
      for (int y = 2; y < height; ++y) {

      }
    }
    return map;
  }
}
