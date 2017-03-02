package com.github.ljarka.sdacourseapplication.books;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.ljarka.sdacourseapplication.R;

import java.util.Arrays;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ViewPager viewPager = (ViewPager) findViewById(R.id.books_view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Book effectiveJava = new Book(1, R.drawable.effective_java, "Effective Java");
        Book cleanCode = new Book(2, R.drawable.clean_code, "Clean Code");
        Book headFirstDesing = new Book(3, R.drawable.head_first_design_patterns,
                "Head First Design Patterns");

        List<Book> list = Arrays.asList(effectiveJava, cleanCode, headFirstDesing);
        BooksPagerAdapter adapter = new BooksPagerAdapter(list);
        viewPager.setAdapter(adapter);
    }
}
