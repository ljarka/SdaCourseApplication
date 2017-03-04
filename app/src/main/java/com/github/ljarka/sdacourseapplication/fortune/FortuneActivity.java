package com.github.ljarka.sdacourseapplication.fortune;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ljarka.sdacourseapplication.R;
import com.squareup.seismic.ShakeDetector;
import com.squareup.seismic.ShakeDetector.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class FortuneActivity extends AppCompatActivity implements ShakeDetector.Listener {
    private Random random = new Random();
    private List<String> list = Arrays.asList("Kto chce ogarnąć cały świat, nie ogarnie niczego.",
            "Miłość fałszuje obraz świata. Ukazuje go dobrym mimo wszystko.",
            "Nauka nie daje satysfakcji moralnej, albowiem nie udziela odpowiedzi na podstawowe pytania");
    private FrameLayout layer;
    private Animator hidingAnimator;
    private Animator showingAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);


        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        layer = (FrameLayout) findViewById(R.id.fortune_container);

        FrameLayout parentLayout = (FrameLayout) findViewById(R.id.parent_layout);

        parentLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    animateCircularReveal((int) event.getX(), (int) event.getY(), layer);
                }

                return true;
            }
        });
    }

    @Override
    public void hearShake() {
        animateCircularReveal(random.nextInt(layer.getWidth()), random.nextInt(layer.getHeight()), layer);
    }

    private void animateCircularReveal(int x, int y, final FrameLayout layer) {
        if((hidingAnimator != null && hidingAnimator.isRunning()) ||
                (showingAnimator != null && showingAnimator.isRunning())){
            return;
        }

        if (layer.getVisibility() == View.VISIBLE) {
            hidingAnimator = ViewAnimationUtils.createCircularReveal(layer, x, y, layer.getHeight(), 0);
            hidingAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    layer.setVisibility(View.INVISIBLE);
                }
            });
            hidingAnimator.start();
        } else {
            int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
            layer.setBackgroundColor(color);
            TextView text = (TextView) layer.findViewById(R.id.my_text);
            text.setText(list.get(random.nextInt(list.size())));
            showingAnimator = ViewAnimationUtils.createCircularReveal(layer, x, y, 0, layer.getHeight());
            layer.setVisibility(View.VISIBLE);
            showingAnimator.start();
        }
    }
}
