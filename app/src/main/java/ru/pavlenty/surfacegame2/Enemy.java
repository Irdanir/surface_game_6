package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

public class Enemy {
    private static Bitmap bitmap;
    static int x;
    static int y;
    private int speed = 0;
    private int maxY;
    private int minY;
    public static Rect detectCollision;
    public Enemy(Context context, int screenX, int screenY) {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        y = 1400;
        x = randomNumber + 300;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    boolean movingup = false;
    public void update() {
        y -= speed * 0.5;
        if (x > 800) {
            movingup = true;
        }
        if (x < -50) {
            movingup = false;
        }
        if (movingup) {
            x -= 10;
        } else {
            x += 10;
        }
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
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
        int x_centre = x + bitmap.getWidth() / 2;
        int y_centre = y + bitmap.getHeight() / 2;
        int x_centre_bullet = Bullet.getX() + Bullet.getBitmap().getWidth() / 2;
        int y_centre_bullet = Bullet.getY() + Bullet.getBitmap().getHeight() / 2;
        boolean x_collide_bullet = Math.abs(x_centre_bullet - x_centre) < 125;
        boolean y_collide_bullet = Math.abs(y_centre_bullet - y_centre) < 100;
        if (x_collide_bullet && y_collide_bullet) {
            return true;
        }
        return false;
    }
    public static int getY() {
        return y;
    }
}