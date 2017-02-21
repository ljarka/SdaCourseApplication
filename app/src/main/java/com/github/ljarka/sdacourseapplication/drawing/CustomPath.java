package com.github.ljarka.sdacourseapplication.drawing;

import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.ColorInt;

public class CustomPath {

    @ColorInt
    private int color;
    private Path path;

    public CustomPath(int color, Point point) {
        this.color = color;
        this.path = new Path();
        this.path.moveTo(point.x, point.y);
    }

    public int getColor() {
        return color;
    }

    public Path getPath() {
        return path;
    }
}
