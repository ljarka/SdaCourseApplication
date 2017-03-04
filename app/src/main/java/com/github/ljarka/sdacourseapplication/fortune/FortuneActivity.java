package com.github.ljarka.sdacourseapplication.fortune;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;

import com.github.ljarka.sdacourseapplication.R;

@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class FortuneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);

        final FrameLayout layer = (FrameLayout) findViewById(R.id.fortune_container);

        FrameLayout parentLayout = (FrameLayout) findViewById(R.id.parent_layout);

        parentLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if (layer.getVisibility() == View.VISIBLE) {
                        Animator circularReveal = ViewAnimationUtils
                                .createCircularReveal(layer, (int) event.getX(),
                                        (int) event.getY(), layer.getHeight(), 0);
                        circularReveal.addListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                layer.setVisibility(View.INVISIBLE);
                            }
                        });
                        circularReveal.start();
                    } else {
                        Animator circularReveal = ViewAnimationUtils
                                .createCircularReveal(layer, (int) event.getX(), (int) event.getY(),
                                        0, layer.getHeight());
                        layer.setVisibility(View.VISIBLE);
                        circularReveal.start();
                    }
                }

                return true;
            }
        });
    }
}
