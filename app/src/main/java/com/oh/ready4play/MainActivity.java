package com.oh.ready4play;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static MainActivity INSTANCE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.INSTANCE = this;
        setContentView(R.layout.activity_main);
    }
}