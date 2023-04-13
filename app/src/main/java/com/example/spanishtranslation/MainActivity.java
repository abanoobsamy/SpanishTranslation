package com.example.spanishtranslation;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//<ImageView
//        android:id="@+id/imageView"
//                android:layout_width="24dp"
//                android:layout_height="24dp"
//                android:layout_alignParentEnd="true"
//                android:layout_alignParentRight="true"
//                android:layout_centerVertical="true"
//                android:layout_marginRight="16dp"
//                android:layout_weight="1"
//                app:srcCompat="@drawable/baseline_play_arrow_white" />

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(categoryAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
    }

}
