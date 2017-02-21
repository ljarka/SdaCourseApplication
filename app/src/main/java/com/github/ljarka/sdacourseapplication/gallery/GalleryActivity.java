package com.github.ljarka.sdacourseapplication.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.ljarka.sdacourseapplication.R;
import com.github.ljarka.sdacourseapplication.drawing.DrawingActivity;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    private DrawingPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        File dir = getExternalFilesDir(DrawingActivity.DRAWING_GALLERY_DIR);
        File[] files = dir.listFiles();
        pagerAdapter = new DrawingPagerAdapter(files);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            pagerAdapter.deleteItem(viewPager.getCurrentItem());
        }
        return super.onOptionsItemSelected(item);
    }
}
