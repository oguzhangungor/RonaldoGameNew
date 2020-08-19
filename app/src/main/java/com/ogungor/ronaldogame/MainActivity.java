package com.ogungor.ronaldogame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    long x;
    TextView textTime;
    TextView textScore;
    ImageButton imageButton;
    LinearLayout layoutTime;
    LinearLayout layoutScore;
    int gameScore;
    float randomLocation;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textScore = findViewById(R.id.textScore);
        textTime = findViewById(R.id.textTime);
        imageButton = findViewById(R.id.imageButton);
        layoutTime = findViewById(R.id.layoutTime);
        layoutScore = findViewById(R.id.layoutScore);
        gameScore = 0;
        textScore.setText(getString(R.string.score, gameScore));

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(final long l) {

                textTime.setText(getString(R.string.time, (l / 1000)));
                x = 0;
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(runnable, 500);
                        imageButton.setX(generateRandomAxis(1));
                        imageButton.setY(generateRandomAxis(2));
                        x++;
                        if ((l / 1000) == 1) {
                            stopHandler();
                        }
                        if (x == 2) {
                            stopHandler();
                        }

                    }
                };
                handler.post(runnable);
            }

            @Override
            public void onFinish() {
                textScore.setVisibility(View.INVISIBLE);
                textTime.setText(getString(R.string.time_finish));

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle(getString(R.string.dialog_title));
                alert.setMessage(getString(R.string.dialog_question));
                alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton((getString(R.string.no)), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, getString(R.string.gameover) + getString(R.string.score, gameScore), Toast.LENGTH_LONG).show();
                    }
                });
                if (!isFinishing()) {
                    alert.show();
                }


                ;
            }
        }.start();


    }

    public void stopHandler() {

        handler.removeCallbacks(runnable);
    }

    ;

    public float generateRandomAxis(int position) {
        int imageWidth = imageButton.getWidth();
        int imageHeight = imageButton.getHeight();
        int randomMin;
        int randomMax;

        if (position == 1) {
            randomMin = 0;
            int layoutTimeWidth= layoutTime.getWidth();
            randomMax = layoutTimeWidth - imageWidth;

        } else {
            randomMin = layoutTime.getHeight();
            int windowsHeight = getWindowManager().getDefaultDisplay().getHeight();
            int layoutScoreHeight = layoutScore.getHeight();
            randomMax = (windowsHeight - (layoutScoreHeight + imageHeight));

        }
        Random random = new Random();
        randomLocation = random.nextInt((randomMax - randomMin))+randomMin;


        return randomLocation;
    }

    public void score(View view) {
        gameScore++;
        textScore.setText(getString(R.string.score, gameScore));
    }
}


