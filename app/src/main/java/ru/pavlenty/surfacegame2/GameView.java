package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private boom Boom;
    private Enemy obstacle;
    private Bullet bullet;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Star> stars = new ArrayList<Star>();
    int screenX;
    private boolean isGameOver;


    SharedPreferences sharedPreferences;
    boolean finover = false;
    static MediaPlayer gameOnsound;
    final MediaPlayer gameOversound;

    Context context;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);
        Boom = new boom(context);
        obstacle = new Enemy(context, screenX + 50, screenY - 50);
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
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
        this.context = context;


        gameOnsound = MediaPlayer.create(context, R.raw.gameon);
        gameOversound = MediaPlayer.create(context, R.raw.gameover);

        gameOnsound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                gameOnsound.setVolume(MainActivity.volume, MainActivity.volume);
            }
        });

        gameOversound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                gameOversound.setVolume(MainActivity.volume, MainActivity.volume);
            }
        });


        gameOnsound.start();

    }
    boolean bulletspawn = false;
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int x1 = 0; int x2 = 300; int y1 = 1500; int y2 = 1700;
        int x3 = 700; int x4 = 1200; int y3 = 1500; int y4 = 1700;
        if (motionEvent.getAction() == MotionEvent.ACTION_UP && x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            if (!bulletspawn) {
                //System.out.println("SPAWNING A BULLET");
                bullet.x = player.getX();
                bulletspawn = true;
            } else {
                //System.out.println("DELETING A BULLET");
                bulletspawn = false;
            }
        }
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;

        }
        if(finover){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
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
            canvas.drawText("ХП противника: " + (n-counter),100,150,paint);
            canvas.drawText("Стрельба",100,1600,paint);
            canvas.drawText("Движение", 800, 1600, paint);
            if (!isGameOver) {
                canvas.drawBitmap(
                        player.getBitmap(),
                        player.getX(),
                        player.getY(),
                        paint);
                canvas.drawBitmap(
                        obstacle.getBitmap(),
                        obstacle.getX(),
                        obstacle.getY(),
                        paint);
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
                            boom.getBitmap(),
                            obstacle.getX(),
                            obstacle.getY(),
                            paint);
                } else {
                    canvas.drawBitmap(
                            boom.getBitmap(),
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
    int counter = 0;
    int n = 4;
    public static void stopMusic(){
        gameOnsound.stop();
    }
    private void update() {
        Rect qwerty = player.getDetectCollisiona();
        player.update();
        obstacle.update();
        bullet.update();
        if (bulletspawn == false) {
            bullet.x = 0;
            bullet.y = player.getY();
        }
        //System.out.println(bullet.x + " " + bullet.y);
        if (obstacle.getX() < -200) {
            obstacle.x = 1200;
        }
        if (bullet.getY() > 1600) {
            bullet.y = player.getY();
            bulletspawn = false;
        }
        boolean isCollision = Enemy.getIsCollision_bullet();
        counter += isCollision ? 1 : 0;
        System.out.println(counter);
        isGameOver = (Enemy.getIsCollision_player() || counter == n || Enemy.getY() < Player.getY());
        if (Enemy.getIsCollision_bullet() && !Enemy.getIsCollision_player()) {
            isgamewon = true;
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
}