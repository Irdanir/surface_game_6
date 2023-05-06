package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Player {
    private static Bitmap bitmap;
    private static int x;
    private static int y;
    private int speed;
    private boolean boosting;
    private final int GRAVITY = -10;
    private int maxX;
    private int minX;
    private int screenWidthDp;
    private int screenHeightDp;
    private final int MIN_SPEED = 1;

    public static Rect detectCollisiona;

    public Player(Context context, int screenX, int screenY) {
        x = 100;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxX = screenX - bitmap.getWidth();
        minX = 0;
        boosting = false;
        Context mContext = context;
        detectCollisiona =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        screenWidthDp  = (int) (displayMetrics.widthPixels / displayMetrics.density);
        screenHeightDp = (int) (displayMetrics.heightPixels / displayMetrics.density);
    }

    public void move_up() {
        x += screenWidthDp / 3;
    }

    public void move_down() {
        x -= screenWidthDp / 3;
    }

    public void update() {
        detectCollisiona.left = x;
        detectCollisiona.top = y;
        detectCollisiona.right = x + bitmap.getWidth();
        detectCollisiona.bottom = y + bitmap.getHeight();
    }
    public static Bitmap getBitmap() {
        return bitmap;
    }
    public static int getX() {
        return x;
    }
    public static int getY() {
        return y;
    }
    public int getSpeed() {
        return speed;
    }
}