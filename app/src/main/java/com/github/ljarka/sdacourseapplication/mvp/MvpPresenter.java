package com.github.ljarka.sdacourseapplication.mvp;

import nucleus.presenter.Presenter;

public class MvpPresenter extends Presenter<MvpActivity> {

    private LongRunningTask longRunningTask = new LongRunningTask();

    public void executeLongRunningTask() {
        new Thread() {

            @Override
            public void run() {
                final String result = longRunningTask.execute();
                getView().setTextOnUiThread(result);
            }
        }.start();

    }

}
