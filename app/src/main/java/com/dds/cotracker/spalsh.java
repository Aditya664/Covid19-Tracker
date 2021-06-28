package com.dds.cotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;

public class spalsh extends AppCompatActivity {
    private SpinKitView spinKitView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        spinKitView = findViewById(R.id.spin_kit);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(spalsh.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },4000);

    }

}