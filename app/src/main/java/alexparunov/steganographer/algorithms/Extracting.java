package alexparunov.steganographer.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

import alexparunov.steganographer.utils.Constants;
import alexparunov.steganographer.utils.StandardMethods;

public class Extracting {

  /**
   * Extract the secret message from Stego image in the following way:
   * 1) Initialize Hash Map which will store 2 key-value pairs (message_type and stream_of_bits)
   * 2) Extract random 24 bit key from (0,0)th pixel and store in array of length 24
   * 3) Extract message type (TEXT, IMAGE, or UNDEFINED) from (0,1)th pixel and store in HashMap
   * 4) Perform extraction of secret message in the following way:
   *      i) Initialize 2 nested for-loops in respective interval [0 <= x <= width],[2 <= y <= height]
   *      ii) Select each pixel of stego image from (x,y) coordinates
   *      iii) Extract Red, Green, Blue colors from each pixel and store in integer array
   *      iv) Extract 1 bit of secret message from each color. Decision of which color to choose
   *          is conducted by xor operation of one bit of secret key and 2nd LSB of Stego image.
   *          If xor equals to 1 then we extract LSB of that color, otherwise we skip the color.
   *      v) Append each bit to StringBuilder
   *      vi) Repeat above given steps until we hit end flag at (x,y) coordinates
   * 5) We cut unnecessary [0-7] bits from StringBuilder
   * 6) Store stream of bits of secret message as String in HashMap
   * @param stegoImage is Bitmap image where the secret data is hidden
   * @return HashMap which contains the message_type and stream_of_bits values
   */
  public static Map extractSecretMessage(Bitmap stegoImage) {
    Map<String, Object> map = new HashMap<String, Object>();

    int width = stegoImage.getWidth();
    int height = stegoImage.getHeight();

    int key[] = new int[24];

    //Extract Key
    int keyPixel = stegoImage.getPixel(0, 0);

    int red = Color.red(keyPixel);
    int green = Color.green(keyPixel);
    int blue = Color.blue(keyPixel);

    StandardMethods.showLog("EXT", "Key2: " + red + " " + green + " " + blue);

    String red_bin = Integer.toBinaryString(red);
    red_bin = "00000000" + red_bin;
    red_bin = red_bin.substring(red_bin.length() - 8);

    for (int i = 0; i <= 7; i++) {
      key[i] = (red_bin.charAt(i) == '1' ? 1 : 0);
    }

    String green_bin = Integer.toBinaryString(green);
    green_bin = "00000000" + green_bin;
    green_bin = green_bin.substring(green_bin.length() - 8);

    for (int i = 0; i <= 7; i++) {
      key[i + 8] = (green_bin.charAt(i) == '1' ? 1 : 0);
    }

    String blue_bin = Integer.toBinaryString(blue);
    blue_bin = "00000000" + blue_bin;
    blue_bin = blue_bin.substring(blue_bin.length() - 8);

    for (int i = 0; i <= 7; i++) {
      key[i + 16] = (blue_bin.charAt(i) == '1' ? 1 : 0);
    }

    int typePixel = stegoImage.getPixel(0, 1);
    int tRed = Color.red(typePixel);
    int tGreen = Color.green(typePixel);
    int tBlue = Color.blue(typePixel);

    //Constants.COLOR_RGB_TEXT
    if (tRed == 135 && tGreen == 197 && tBlue == 245) {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_TEXT);

    //Constants.COLOR_RGB_IMAGE
    } else if (tRed == 255 && tGreen == 105 && tBlue == 180) {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_IMAGE);

    } else {

      map.put(Constants.MESSAGE_TYPE, Constants.TYPE_UNDEFINED);
      map.put(Constants.MESSAGE_BITS, "");
      return map;

    }

    StringBuilder sb = new StringBuilder();

    int keyPos = 0;
    outerloop:
    for (int x = 0; x < width; ++x) {
      for (int y = 2; y < height; ++y) {
        int pixel = stegoImage.getPixel(x, y);

        int colors[] = {Color.red(pixel), Color.green(pixel), Color.blue(pixel)};

        //Colors.COLOR_RGB_END
        if (colors[0] == 96 && colors[1] == 62 && colors[2] == 148) {
          break outerloop;
        } else {

          for (int c = 0; c < 3; c++) {

            if ((key[keyPos] ^ LSB2(colors[c])) == 1) {
              int lsb = LSB(colors[c]);
              sb.append(lsb);
              keyPos = (keyPos + 1) % key.length;
            }
          }
        }
      }
    }

    String sm = sb.toString();
    int sL = sm.length();

    //Cut unnecessary [0-7] pixels
    sm = sm.substring(0, sL - sL % 8);

    map.put(Constants.MESSAGE_BITS, sm);
    return map;
  }

  /**
   * @param number is either Red, Green, or Blue presented as integer (0-255)
   * @return least significant bit, i.e. the left-most
   */
  @Contract(pure = true)
  private static int LSB(int number) {
    return number & 1;
  }

  /**
   * @param number is either Red, Green, or Blue presented as integer (0-255)
   * @return second least significant bit, i.e. second to the left-most
   */
  @Contract(pure = true)
  private static int LSB2(int number) {
    return (number >> 1) & 1;
  }
}
