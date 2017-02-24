package com.github.ljarka.sdacourseapplication.reflex;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ljarka.sdacourseapplication.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReflexActivity extends AppCompatActivity implements OnClickListener {

    private List<Integer> images = Arrays.asList(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable
            .five);

    private Random random = new Random();
    private ImageView firstImageView;
    private ImageView secondImageView;
    private Button firstPlayerButton;
    private Button secondPlayerButton;
    private CountDownTimer countDownTimer;
    private Button startButton;
    private boolean isRunning = false;
    private Drawable defaultBackground;
    private IntroAnimator introAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflex_activity);

        firstImageView = (ImageView) findViewById(R.id.first_image_view);
        secondImageView = (ImageView) findViewById(R.id.second_image_view);
        startButton = (Button) findViewById(R.id.start_button);
        final TextView introTextView = (TextView) findViewById(R.id.intro_text_view);
        introAnimator = new IntroAnimator(introTextView);

        firstPlayerButton = (Button) findViewById(R.id.first_player_button);
        secondPlayerButton = (Button) findViewById(R.id.second_player_button);
        defaultBackground = firstPlayerButton.getBackground();
        firstPlayerButton.setOnClickListener(this);
        secondPlayerButton.setOnClickListener(this);

        countDownTimer = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int firstImage = images.get(random.nextInt(images.size()));
                int secondImage = images.get(random.nextInt(images.size()));
                firstImageView.setImageResource(firstImage);
                firstImageView.setTag(firstImage);
                secondImageView.setImageResource(secondImage);
                secondImageView.setTag(secondImage);
            }

            @Override
            public void onFinish() {
                startButton.setVisibility(View.VISIBLE);
                isRunning = false;
            }
        };

        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                introAnimator.showIntro(new Runnable() {

                    @Override
                    public void run() {
                        isRunning = true;
                        firstImageView.setVisibility(View.VISIBLE);
                        secondImageView.setVisibility(View.VISIBLE);
                        countDownTimer.start();
                    }
                });
                firstPlayerButton.setBackground(defaultBackground);
                secondPlayerButton.setBackground(defaultBackground);
                startButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!isRunning) {
            return;
        }

        if (firstImageView.getTag().equals(secondImageView.getTag())) {
            if (view.getId() == firstPlayerButton.getId()) {
                firstPlayerButton.setBackgroundColor(Color.GREEN);
                secondPlayerButton.setBackgroundColor(Color.RED);
            } else {
                firstPlayerButton.setBackgroundColor(Color.RED);
                secondPlayerButton.setBackgroundColor(Color.GREEN);
            }
        } else {
            if (view.getId() == firstPlayerButton.getId()) {
                firstPlayerButton.setBackgroundColor(Color.RED);
                secondPlayerButton.setBackgroundColor(Color.GREEN);
            } else {
                firstPlayerButton.setBackgroundColor(Color.GREEN);
                secondPlayerButton.setBackgroundColor(Color.RED);
            }
        }

        firstImageView.setVisibility(View.GONE);
        secondImageView.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        isRunning = false;
    }
}
