package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.adapter.LanguageAdapter;
import com.example.notes.adapter.LanguageModel;

import java.util.ArrayList;

public class Language_Screen extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout done;
    RecyclerView recycle;
    LanguageAdapter languageAdapter;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<LanguageModel> arrlanguage=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_screen);

        done = findViewById(R.id.right);
        recycle = findViewById(R.id.recycle);
        layoutManager = new GridLayoutManager(this, 2);

        recycle.setLayoutManager(new GridLayoutManager(Language_Screen.this, 2));

        arrlanguage.add(new LanguageModel(R.drawable.img, "Afrikaans"));
        arrlanguage.add(new LanguageModel(R.drawable.img_15, "Arabic"));
        arrlanguage.add(new LanguageModel(R.drawable.img_2, "English"));
        arrlanguage.add(new LanguageModel(R.drawable.img_3, "French"));
        arrlanguage.add(new LanguageModel(R.drawable.img_4, "Hindi"));
        arrlanguage.add(new LanguageModel(R.drawable.img_5, "German"));
        arrlanguage.add(new LanguageModel(R.drawable.img_6, "Chinese"));
        arrlanguage.add(new LanguageModel(R.drawable.img_7, "Russians"));
        arrlanguage.add(new LanguageModel(R.drawable.img_8, "Spanish"));
        arrlanguage.add(new LanguageModel(R.drawable.img_9, "Japanese"));
        arrlanguage.add(new LanguageModel(R.drawable.img_10, "Indonesian"));
        arrlanguage.add(new LanguageModel(R.drawable.img_11, "Italian"));
        arrlanguage.add(new LanguageModel(R.drawable.img_12, "Portuguese"));
        arrlanguage.add(new LanguageModel(R.drawable.img_13, "Vietnamese"));

        languageAdapter = new LanguageAdapter(Language_Screen.this, arrlanguage);
        recycle.setAdapter(languageAdapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Language_Screen.this, Home.class);
                startActivity(i);
            }
        });

    }

}