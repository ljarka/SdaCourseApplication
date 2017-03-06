package com.github.ljarka.sdacourseapplication.quiz;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ljarka.sdacourseapplication.MainActivity;
import com.github.ljarka.sdacourseapplication.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QuizActivity extends AppCompatActivity implements OnClickListener {

    private static final String INDEX_KEY = "index_key";

    private int currentQuestionIndex;
    private boolean wasViewClicked;
    private QuizContainer quizContainer;
    private ValueAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_app);

        currentQuestionIndex = getIntent().getIntExtra(INDEX_KEY, 0);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        objectAnimator = ObjectAnimator.ofInt(0, 100);
        objectAnimator.setDuration(10000);
        objectAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressBar.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        progressBar.setProgress(0);
        objectAnimator.start();

        String json = null;
        try {
            json = loadQuizJson();
            quizContainer = new Gson().fromJson(json, QuizContainer.class);
            TextView question = (TextView) findViewById(R.id.question);
            QuizQuestion quizQuestion = quizContainer.getQuestions().get(currentQuestionIndex);
            question.setText(quizQuestion.getQuestion());

            Button answer1 = (Button) findViewById(R.id.first);
            Button answer2 = (Button) findViewById(R.id.second);
            Button answer3 = (Button) findViewById(R.id.third);
            Button answer4 = (Button) findViewById(R.id.fourth);

            SingleAnswer firstAnswer = quizQuestion.getAnswers().get(0);
            answer1.setText(firstAnswer.getText());
            answer1.setTag(firstAnswer.isCorrect());

            SingleAnswer secondAnswer = quizQuestion.getAnswers().get(1);
            answer2.setText(secondAnswer.getText());
            answer2.setTag(secondAnswer.isCorrect());

            SingleAnswer thirdAnswer = quizQuestion.getAnswers().get(2);
            answer3.setText(thirdAnswer.getText());
            answer3.setTag(thirdAnswer.isCorrect());

            SingleAnswer fourthAnswer = quizQuestion.getAnswers().get(3);
            answer4.setText(fourthAnswer.getText());
            answer4.setTag(fourthAnswer.isCorrect());

            answer1.setOnClickListener(this);
            answer2.setOnClickListener(this);
            answer3.setOnClickListener(this);
            answer4.setOnClickListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (!wasViewClicked) {
            if ((Boolean) view.getTag()) {
                Toast.makeText(view.getContext(), "Odpowiedź poprawna", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "Zła odpowiedź", Toast.LENGTH_LONG).show();
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (currentQuestionIndex < quizContainer.getQuestionsCount() - 1) {
                        Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                        intent.putExtra(INDEX_KEY, ++currentQuestionIndex);
                        startActivity(intent);
                    } else {
                        Toast.makeText(QuizActivity.this, "Quiz ends", Toast.LENGTH_LONG).show();
                    }

                }
            }, 3000);

            wasViewClicked = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        objectAnimator.removeAllUpdateListeners();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String loadQuizJson() throws IOException {
        StringBuilder buf = new StringBuilder();
        InputStream json = getAssets().open("quiz_data.json");
        BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }

        in.close();

        return buf.toString();
    }
}
