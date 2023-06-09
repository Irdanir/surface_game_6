package ru.pavlenty.surfacegame2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.media.MediaPlayer;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonPlay;
    private ImageButton buttonScore;
    private MediaPlayer mediaPlayer;
    private EditText soundInput;
    public static float volume = 0.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);
        buttonPlay.setOnClickListener(this);
        buttonScore.setOnClickListener(this);
        soundInput = findViewById(R.id.soundinput);
        soundInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    volume = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    // Handle invalid input here
                }
                mediaPlayer.setVolume(volume, volume);
            }
            @Override
            public void afterTextChanged(Editable s) {
                //Do nothing
            }
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music2);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    @Override
    public void onClick(View v) {
        if (v == buttonPlay) {
            if (LoginActivity.isLoggedIn) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mediaPlayer.stop();
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                mediaPlayer.stop();
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
            }
        }

        if (v == buttonScore) {
            if (LoginActivity.isLoggedIn == true) {
                startActivity(new Intent(MenuActivity.this, HightScore.class));
            } else {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                startActivity(new Intent(MenuActivity.this, HightScore.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity", "onBackPressed()");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы хотите выйти?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GameView.stopMusic();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}