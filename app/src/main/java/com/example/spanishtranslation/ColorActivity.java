package com.example.spanishtranslation;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ColorFragment()).commit();

    }
}
