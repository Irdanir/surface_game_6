package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.IllegalFormatWidthException;

public class Bullet {
    private static Bitmap bitmap;
    static int x;
    static int y;
    private int speed = 0;
    private boolean boosting;
    private final int GRAVITY = -10;
    private int maxY;
    private int minY;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public static Rect detectCollisiona;

    public Bullet(Context context, int screenX, int screenY) {
        x = 0;
        y = 100;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.friend);
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        boosting = false;
        detectCollisiona =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    public void update() {
        y += speed * 100;
        detectCollisiona.left = x;
        detectCollisiona.top = y;
        detectCollisiona.right = x + bitmap.getWidth();
        detectCollisiona.bottom = y + bitmap.getHeight();

    }


    public static Rect getDetectCollisiona() {
        return detectCollisiona;
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