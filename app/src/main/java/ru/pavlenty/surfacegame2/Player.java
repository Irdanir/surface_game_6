package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    private static Bitmap bitmap;
    private static int x;
    private static int y;
    private int speed = 0;
    private boolean boosting;
    private final int GRAVITY = -10;
    private int maxX;
    private int minX;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public static Rect detectCollisiona;

    public Player(Context context, int screenX, int screenY) {
        x = 100;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxX = screenX - bitmap.getWidth();
        minX = 0;
        boosting = false;


        detectCollisiona =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

    public void update() {
        if (boosting) {
            speed += 2;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        x -= speed + GRAVITY;

        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }


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