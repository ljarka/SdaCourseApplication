package com.github.ljarka.sdacourseapplication.mvp;

public class LongRunningTask {

    public String execute() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Udało się !!!!!!";
    }
}
