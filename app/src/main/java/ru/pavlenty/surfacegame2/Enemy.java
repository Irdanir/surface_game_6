package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

public class Enemy {
    static Bitmap bitmap;
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
        Rect playerRect = new Rect(Player.getX(), Player.getY(), Player.getX() + Player.getBitmap().getWidth(), Player.getY() + Player.getBitmap().getHeight());
        Rect enemyRect = new Rect(Enemy.getX(), Enemy.getY(), Enemy.getX() + Enemy.getBitmap().getWidth(), Enemy.getY() + Enemy.getBitmap().getHeight());
        return playerRect.intersect(enemyRect);
    }
    public static boolean getIsCollision_bullet() {
        Rect bulletRect = new Rect(Bullet.getX(), Bullet.getY(), Bullet.getX() + Bullet.getBitmap().getWidth(), Bullet.getY() + Bullet.getBitmap().getHeight());
        Rect enemyRect = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
        return bulletRect.intersect(enemyRect);
    }
    public static int getY() {
        return y;
    }
}