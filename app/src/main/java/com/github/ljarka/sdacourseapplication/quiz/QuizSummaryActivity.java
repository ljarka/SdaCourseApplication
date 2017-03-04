package com.github.ljarka.sdacourseapplication.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.ljarka.sdacourseapplication.R;

public class QuizSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        TextView resultOk = (TextView) findViewById(R.id.result_ok);
        TextView resultNotOk = (TextView) findViewById(R.id.result_not_ok);

        resultOk.setText("Poprawnych odpowiedzi " +
                getIntent().getIntExtra(QuizActivity.CORRECT_ANSWERS, 0));

        resultNotOk.setText("Niepoprawnych odpowiedzi " +
                getIntent().getIntExtra(QuizActivity.INCORRECT_ANSWERS, 0));

        Button button = (Button) findViewById(R.id.try_again);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizSummaryActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
