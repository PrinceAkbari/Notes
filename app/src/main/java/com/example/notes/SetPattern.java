package com.example.notes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;

public class SetPattern extends AppCompatActivity {

    private ImageView pin,backsetpattern;
    private PatternLockView patternLockView;
    private TextView patternLockText;

    private String savedPattern = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern);

        pin = findViewById(R.id.pin);
        backsetpattern = findViewById(R.id.backsetpattern);
        patternLockView = findViewById(R.id.patternLockView);
        patternLockText = findViewById(R.id.pattern_lock_text);

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetPattern.this, SetPassword.class);
                startActivity(intent);
            }
        });

        backsetpattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
