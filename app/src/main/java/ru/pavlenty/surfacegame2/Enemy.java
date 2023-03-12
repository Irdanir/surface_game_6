package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

public class Enemy {
    private static Bitmap bitmap;
    static int x;
    private static int y;
    private int speed = 0;
    private boolean boosting;
    private final int GRAVITY = -10;
    private int maxY;
    private int minY;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public static Rect detectCollision;
    public static boolean isCollision;
    public Enemy(Context context, int screenX, int screenY) {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        y = 1400;
        x = randomNumber + 300;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        boosting = false;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    boolean movingup = false;
    public void update() {
        y -= speed * 0.5;
        if (x > 1200) {
            movingup = true;
        }
        if (x < 0) {
            movingup = false;
        }
        if (movingup) {
            x -= 20;
        } else {
            x += 20;
        }
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }


    public static Rect getDetectCollision() {
        return detectCollision;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static int getX() {
        return x;
    }

    public static boolean getIsCollision_player() {
        boolean x_collide_player = ((Enemy.getX() <= Player.getX() + 150) && (Enemy.getX() >= Player.getX() - 150));
        boolean y_collide_player = ((Enemy.getY() <= Player.getY() + 150) && (Enemy.getY() >= Player.getY() - 150));
        boolean collide_player = x_collide_player && y_collide_player;
        if (collide_player) {
            return true;
        }
        return false;
    }
    public static boolean getIsCollision_bullet() {
        boolean x_collide_bullet = ((Enemy.getX() <= Bullet.getX() + 75) && (Enemy.getX() >= Bullet.getX() - 75));
        boolean y_collide_bullet = ((Enemy.getY() <= Bullet.getY() + 50) && (Enemy.getY() >= Bullet.getY() - 50));
        boolean collide_bullet = x_collide_bullet && y_collide_bullet;
        if (collide_bullet) {
            return true;
        }
        return false;
    }
    public static int getY() {
        return y;
    }
}