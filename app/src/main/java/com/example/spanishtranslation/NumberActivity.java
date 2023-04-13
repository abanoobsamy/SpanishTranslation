package com.example.spanishtranslation;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class NumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new NumberFragment()).commit();
    }
}
