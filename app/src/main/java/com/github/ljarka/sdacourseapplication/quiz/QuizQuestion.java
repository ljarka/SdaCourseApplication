package com.github.ljarka.sdacourseapplication.quiz;

import java.util.List;

public class QuizQuestion {

    private int id;
    private String question;
    private List<SingleAnswer> answers;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<SingleAnswer> getAnswers() {
        return answers;
    }
}
