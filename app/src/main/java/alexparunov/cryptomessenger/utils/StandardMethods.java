package alexparunov.cryptomessenger.utils;

import android.content.Context;
import android.widget.Toast;

public class StandardMethods {

  public static void showToast(Context context, int message) {
    Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show();
  }
}
