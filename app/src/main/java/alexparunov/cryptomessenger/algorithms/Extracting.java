package alexparunov.cryptomessenger.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

import alexparunov.cryptomessenger.utils.StandardMethods;

public class Extracting {

  public static Map extractSecretMessage(Bitmap stegoImage) {
    Map map = new HashMap();

    StringBuilder secretMessage = new StringBuilder();

    int width = stegoImage.getWidth();
    int height = stegoImage.getHeight();

    int key[] = new int[13];
    int i = 0;

    //Extract Key
    int keyPixel = stegoImage.getPixel(0,0);

    int red = Color.red(keyPixel);
    int green = Color.green(keyPixel);
    int blue = Color.blue(keyPixel);
    StandardMethods.showLog("EXT","(r,g,b): ("+red+","+green+","+blue+")");

    for(int j = 0; j < 4; ++j) {
      key[i] = red & 8;
      red <<= 1;
      i++;
    }

    for(int j = 0; j < 5; ++j) {
      key[i] = green & 16;
      green <<= 1;
      i++;
    }

    for(int j = 0; j < 4; ++j) {
      key[i] = blue & 8;
      blue <<= 1;
      i++;
    }

    for(int j = 0; j< 13;j++) {
      StandardMethods.showLog("EXT",key[j]);
    }

    for(int x = 0; x < width; ++x) {
      for(int y = 2; y < height; ++y) {

      }
    }
    return map;
  }
}
