package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class boom {
    private static Bitmap bitmap;
    public boom(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
    }
    public static Bitmap getBitmap() {
        return bitmap;
    }
}