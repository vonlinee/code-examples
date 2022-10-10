package com.example.myapplication;

import android.text.Layout;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(Layout.BREAK_STRATEGY_BALANCED);

        view.setOnClickListener();
    }

}