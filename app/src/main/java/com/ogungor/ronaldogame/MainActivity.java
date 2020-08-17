package com.ogungor.ronaldogame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textTime;
    TextView textScore;
    ImageButton imageButton;
    LinearLayout layoutTime;
    LinearLayout layoutScore;
    int gameScore;
    float randomLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textScore = findViewById(R.id.textScore);
        textTime = findViewById(R.id.textTime);
        imageButton = findViewById(R.id.imageButton);
        layoutTime = findViewById(R.id.layoutTime);
        layoutScore = findViewById(R.id.layoutScore);
        gameScore = 0;
        textScore.setText(getString(R.string.score,gameScore));

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {
                textTime.setText(getString(R.string.time, (l / 1000)));
                imageButton.setX(generateRandomAxis(1));
                imageButton.setY(generateRandomAxis(2));
            }

            @Override
            public void onFinish() {
                
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
                alert.show();
                textScore.setVisibility(View.INVISIBLE);
                textTime.setText(getString(R.string.time_finish));
                ;
            }
        }.start();


    }

    ;

    public float generateRandomAxis(int position) {
        int imageWidth = imageButton.getWidth();
        int imageHeight = imageButton.getHeight();
        int randomMin;
        int randomMax;

        if (position == 1) {
            randomMin = 0;
            randomMax = layoutTime.getWidth() - imageWidth;

        } else {
            randomMin = layoutTime.getHeight();
            int windowsHeight = getWindowManager().getDefaultDisplay().getHeight();
            int layoutTimeHeight = layoutTime.getHeight();
            int layoutScoreHeight = layoutScore.getHeight();
            randomMax = (windowsHeight - (layoutScoreHeight + imageHeight + layoutTimeHeight));
        }

        Random generateRandom = new Random();
        return randomLocation = generateRandom.nextInt((randomMax - randomMin) + 1) + randomMin;
    }

    public void score(View view) {
        gameScore++;
        textScore.setText(getString(R.string.score, gameScore));
    }
}