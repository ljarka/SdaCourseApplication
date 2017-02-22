package com.github.ljarka.sdacourseapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ljarka.sdacourseapplication.drawing.DrawingActivity;

public class MainActivity extends AppCompatActivity {

    public static final String NOTES_KEY = "notes";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);

        TextView textView = (TextView) findViewById(R.id.drawing_application_button);
        textView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DrawingActivity.class);
                startActivity(intent);
            }
        });

        final EditText notesEditText = (EditText) findViewById(R.id.my_note_edittext);
        notesEditText.setText(readText());

        Button saveButton = (Button) findViewById(R.id.save_note);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                saveText(notesEditText.getText().toString());
            }
        });
    }

    private String readText(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(NOTES_KEY, "");
    }

    private void saveText(String text) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences
                .edit()
                .putString(NOTES_KEY, text)
                .apply();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
