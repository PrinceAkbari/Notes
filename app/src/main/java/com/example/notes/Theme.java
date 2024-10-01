package com.example.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Theme extends AppCompatActivity {
    ImageView backtheme, imgText, imgBackground, imgItembackground, imgPrimary, imgAppicon;
    CardView textcolor, backgroundcolor, itembackgroundcolor, primarycolor, appiconcolor;

    // Keys for saving the colors in SharedPreferences
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_TEXT_COLOR = "text_color";
    private static final String KEY_BACKGROUND_COLOR = "background_color";
    private static final String KEY_ITEM_BACKGROUND_COLOR = "item_background_color";
    private static final String KEY_PRIMARY_COLOR = "primary_color";
    private static final String KEY_APP_ICON_COLOR = "app_icon_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

           // Initialize views
        backtheme =findViewById(R.id.backtheme);
        textcolor=findViewById(R.id.textcolor);
        backgroundcolor=findViewById(R.id.backgroundcolor);
        itembackgroundcolor=findViewById(R.id.itembackgroundcolor);
        primarycolor=findViewById(R.id.primarycolor);
        appiconcolor=findViewById(R.id.appiconcolor);

        imgText=findViewById(R.id.imgText);
        imgBackground=findViewById(R.id.imgBackground);
        imgItembackground=findViewById(R.id.imgItembackground);
        imgPrimary=findViewById(R.id.imgPrimary);
        imgAppicon=findViewById(R.id.imgAppicon);

        // Load the saved colors from SharedPreferences
        SharedPreferences prefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        imgText.setColorFilter(prefs.getInt(KEY_TEXT_COLOR, 0xFFFFFFFF)); // Default to white
        imgBackground.setColorFilter(prefs.getInt(KEY_BACKGROUND_COLOR, 0xFFFFFFFF));
        imgItembackground.setColorFilter(prefs.getInt(KEY_ITEM_BACKGROUND_COLOR, 0xFFFFFFFF));
        imgPrimary.setColorFilter(prefs.getInt(KEY_PRIMARY_COLOR, 0xFFFFFFFF));
        imgAppicon.setColorFilter(prefs.getInt(KEY_APP_ICON_COLOR, 0xFFFFFFFF));



        // Set up click listeners for each color picker
        textcolor.setOnClickListener(v -> openColorPickerDialog(KEY_TEXT_COLOR, imgText));
        backgroundcolor.setOnClickListener(v -> openColorPickerDialog(KEY_BACKGROUND_COLOR, imgBackground));
        itembackgroundcolor.setOnClickListener(v -> openColorPickerDialog(KEY_ITEM_BACKGROUND_COLOR, imgItembackground));
        primarycolor.setOnClickListener(v -> openColorPickerDialog(KEY_PRIMARY_COLOR, imgPrimary));
        appiconcolor.setOnClickListener(v -> openColorPickerDialog(KEY_APP_ICON_COLOR, imgAppicon));
        backtheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private  void  openColorPickerDialog(String colorKey,ImageView imageView){

        SharedPreferences prefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        int currentcolr =prefs.getInt(colorKey,0xFFFFFFFF);

        Intent intent=new Intent(Theme.this,ColorPickerActivity.class);
        intent.putExtra("colorKey",colorKey);
        intent.putExtra("defaultColor",currentcolr);

        startActivityForResult(intent,1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            String colorKey = data.getStringExtra("colorKey");
            int selectedColor = data.getIntExtra("selectedColor", 0xFFFFFFFF);

            // Update the corresponding ImageView and save the selected color
            ImageView imageView = getImageViewByKey(colorKey);
            if (imageView != null) {
                imageView.setColorFilter(selectedColor);
                saveColorInPreferences(colorKey, selectedColor);
            }
        }
    }


    private ImageView getImageViewByKey(String colorKey) {
        switch (colorKey) {
            case KEY_TEXT_COLOR:
                return imgText;
            case KEY_BACKGROUND_COLOR:
                return imgBackground;
            case KEY_ITEM_BACKGROUND_COLOR:
                return imgItembackground;
            case KEY_PRIMARY_COLOR:
                return imgPrimary;
            case KEY_APP_ICON_COLOR:
                return imgAppicon;
            default:
                return null;
        }
    }

    private void saveColorInPreferences(String colorKey, int color) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(colorKey, color);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
