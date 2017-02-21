package com.github.ljarka.sdacourseapplication.drawing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.github.ljarka.sdacourseapplication.R;
import com.github.ljarka.sdacourseapplication.gallery.GalleryActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawingActivity extends AppCompatActivity {

    public static final String DRAWING_GALLERY_DIR = "drawing_gallery2";
    private SimpleDrawingView simpleDrawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_activity_main);
        simpleDrawingView = (SimpleDrawingView) findViewById(R.id.drawing_view);

        Button blueButton = (Button) findViewById(R.id.blue_button);
        Button redButton = (Button) findViewById(R.id.red_button);

        blueButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                simpleDrawingView.setCurrentColor(ContextCompat.getColor(DrawingActivity.this, R.color.blue));
            }
        });

        redButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                simpleDrawingView.setCurrentColor(ContextCompat.getColor(DrawingActivity.this, R.color.red));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear) {
            simpleDrawingView.clear();
        } else if (item.getItemId() == R.id.save) {
            saveDrawingToFile();
        }else if(item.getItemId() == R.id.drawing_gallery){
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDrawingToFile() {
        File drawingFile = new File(getDrawingGalleryDirectory(),
                createFileName());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(drawingFile);
            Bitmap bitmap = convertViewToBitmap(simpleDrawingView);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String createFileName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        return "my_drawing" + timeStamp + ".png" ;
    }

    private File getDrawingGalleryDirectory() {
        return getExternalFilesDir(DRAWING_GALLERY_DIR);
    }

    private Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
