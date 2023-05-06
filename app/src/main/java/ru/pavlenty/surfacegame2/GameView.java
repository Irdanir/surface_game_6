package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Explosion Explosion;
    private Enemy enemy;
    private Bullet bullet;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Star> stars = new ArrayList<Star>();

    int screenX;
    int rounds = 0;

    boolean flag ;


    private boolean isGameOver;


    static int score;


    int highScore[] = new int[4];


    SharedPreferences sharedPreferences;
    boolean finover = false;
    static MediaPlayer gameOnsound;
    final MediaPlayer gameOversound;
    static MediaPlayer hitsound;
    static MediaPlayer shootingsound;
    static MediaPlayer victorysound;
    Context context;
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);
        Explosion = new Explosion(context);
        enemy = new Enemy(context, screenX + 50, screenY - 50);
        bullet = new Bullet(context, screenX + 50, screenY - 50);
        surfaceHolder = getHolder();
        paint = new Paint();

        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        this.screenX = screenX;
        isGameOver = false;


        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);


        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        this.context = context;


        gameOnsound = MediaPlayer.create(context,R.raw.gameon);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);
        hitsound = MediaPlayer.create(context,R.raw.hit);
        shootingsound = MediaPlayer.create(context,R.raw.shooting);
        victorysound = MediaPlayer.create(context,R.raw.victory);
        gameOnsound.setVolume(MenuActivity.volume, MenuActivity.volume);
        gameOversound.setVolume(MenuActivity.volume, MenuActivity.volume);
        gameOnsound.start();

    }
    boolean bulletspawn = false;
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Context mContext = context;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int screenWidthDp = (int) (displayMetrics.widthPixels / displayMetrics.density);
        int screenHeightDp = (int) (displayMetrics.heightPixels / displayMetrics.density);
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int x1 = 0; int x2 = 300; int y1 = 1500; int y2 = 1600;
        int x3 = 700; int x4 = 1200; int y3 = 1500; int y4 = 1600;
        int x5 = 700; int x6 = 1200; int y5 = 1700; int y6 = 1800;
        if (motionEvent.getAction() == MotionEvent.ACTION_UP && x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            if (!bulletspawn) {
                //System.out.println("SPAWNING A BULLET");
                bullet.x = player.getX() + 60;
                bulletspawn = true;
                shootingsound.start();
            } else {
                //System.out.println("DELETING A BULLET");
                bulletspawn = false;
            }
        }
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP && x >= x3 && x <= x4 && y >= y3 && y <= y4) {
                    player.move_up();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && x >= x5 && x <= x6 && y >= y5 && y <= y6) {
                    player.move_down();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                break;
        }
        if (finover){
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context, MenuActivity.class));
                stopMusic();
            }
        }
        return true;
    }
    boolean isgamewon = false;
    @Override
    public void run() {
        while (playing) {
            if (!finover) {
                update();
                draw();
                control();
            }
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            paint.setTextSize(50);
            canvas.drawText("Время выживания: "+score,100,50,paint);
            canvas.drawText("ХП противника: " + (health-hits),100,150,paint);
            canvas.drawText("Стрельба",100,1600,paint);
            canvas.drawText("Вверх", 800, 1600, paint);
            canvas.drawText("Вниз", 800, 1800, paint);
            if (!isGameOver) {
                canvas.drawBitmap(
                        player.getBitmap(),
                        player.getX(),
                        player.getY(),
                        paint);
                if (rounds % 5 != 0) {
                    enemy.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss);
                    canvas.drawBitmap(
                            enemy.getBitmap(),
                            enemy.getX(),
                            enemy.getY(),
                            paint);
                } else {
                    enemy.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss);
                    canvas.drawBitmap(
                            enemy.getBitmap(),
                            enemy.getX(),
                            enemy.getY(),
                            paint);
                }
                if (bulletspawn) {
                    canvas.drawBitmap(
                            bullet.getBitmap(),
                            bullet.getX(),
                            bullet.getY(),
                            paint);
                }
            } else {
                if (isgamewon) {
                    canvas.drawBitmap(
                            player.getBitmap(),
                            player.getX(),
                            player.getY(),
                            paint);
                    canvas.drawBitmap(
                            Explosion.getBitmap(),
                            enemy.getX(),
                            enemy.getY(),
                            paint);
                } else {
                    canvas.drawBitmap(
                            Explosion.getBitmap(),
                            player.getX(),
                            player.getY(),
                            paint);
                }
            }
            if(isGameOver){
                paint.setTextSize(40);
                paint.setTextAlign(Paint.Align.CENTER);
                finover = true;

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                gameOnsound.stop();
                gameOversound.start();
                if (isgamewon) {
                    canvas.drawText("Вы выиграли. Нажмите, чтобы начать заново.", canvas.getWidth() / 2, yPos, paint);
                } else {
                    canvas.drawText("Вы проиграли. Нажмите, чтобы начать заново.", canvas.getWidth() / 2, yPos, paint);
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }
    static int counter = 0;
    int health = 4;
    int hits = 0;
    public static void stopMusic(){
        gameOnsound.stop();
    }
    private void update() {
        if (hits >= health) {
            hits = 0;
            enemy.y = 1400;
            counter++;
            health = 4 + counter;
            if (rounds % 5 == 0) {
                health *= 10;
            } else {
                health = 4 + counter;
            }
            victorysound.start();
        }
        score++;
        player.update();
        enemy.update();
        bullet.update();
        if (bulletspawn == false) {
            bullet.x = 0;
            bullet.y = player.getY();
        }
        //System.out.println(bullet.x + " " + bullet.y);
        if (bullet.getY() > 1600) {
            bullet.y = player.getY();
            bulletspawn = false;
        }
        boolean isCollision = Enemy.getIsCollision_bullet();
        hits += isCollision ? 1 : 0;
        isGameOver = (Enemy.getIsCollision_player() || Enemy.getY() < Player.getY());
        if (Enemy.getIsCollision_bullet() && !Enemy.getIsCollision_player()) {
            if (hits % 3 == 0) {
                gameOversound.start();
                hits++;
            }
            isgamewon = true;
            bulletspawn = false;
            rounds++;
        } else if (Enemy.getIsCollision_player()) {
            isgamewon = false;
        }
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


}