package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ColorPickerActivity extends AppCompatActivity {

    private ImageView colorPickerImage;
    private TextView colorCodeTextView;
    private Button btnOk;
    View colors;
    private int selectedColor = 0xFFFFFFFF; // Default to White
    private Bitmap bitmap;
    private View selectedColorView;

    @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorPickerImage = findViewById(R.id.colorPickerImage);
        colorCodeTextView = findViewById(R.id.colorCodeTextView);
        selectedColorView = findViewById(R.id.selectedColorView);
        colors = findViewById(R.id.colors);
        btnOk = findViewById(R.id.btnOk);

        // Get the default color, if available
        if (getIntent() != null) {
            int defaultColor = getIntent().getIntExtra("defaultColor", 0xFFFFFFFF);
            selectedColor = defaultColor;
            colorCodeTextView.setText(String.format("#%06X", (0xFFFFFF & defaultColor)));
            selectedColorView.setBackgroundColor(defaultColor);
        }

        colorPickerImage.setDrawingCacheEnabled(true);
        colorPickerImage.buildDrawingCache(true);

        // Set the onTouchListener to the color picker image


        colorPickerImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap =colorPickerImage.getDrawingCache();
                    int x= (int) event.getX();
                    int y=(int) event.getY();

                    if(x >=0 && x< bitmap.getWidth() && y>=0 && y< bitmap.getHeight()){
                        int pixel= bitmap.getPixel(x,y);
                        selectedColor=pixel;

                        String hex=String.format("#%06X", (0xFFFFFF & pixel));
                        colorCodeTextView.setText(hex);
                        selectedColorView.setBackgroundColor(pixel);
                    }
                }
                return true;
            }
        });

        // Predefined color selections
        setColorSelectionListener(findViewById(R.id.colorRed), Color.RED);
        setColorSelectionListener(findViewById(R.id.colorBlue), Color.BLUE);
        setColorSelectionListener(findViewById(R.id.colorVadali), Color.parseColor("#2D9AFF")); // Assuming Vadali is teal
        setColorSelectionListener(findViewById(R.id.colorYellow), Color.YELLOW);
        setColorSelectionListener(findViewById(R.id.colorOrange), Color.parseColor("#FFA500"));

        // Set up OK button
        btnOk.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("colorKey", getIntent().getStringExtra("colorKey"));
            resultIntent.putExtra("selectedColor", selectedColor);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void setColorSelectionListener(View view, int color) {
        view.setOnClickListener(v -> {
            selectedColor = color;
            updateColorViews(selectedColor);
            colorCodeTextView.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
        });
    }

    private void updateColorViews(int color) {
        applyShadowEffect(color);
        selectedColorView.setBackgroundColor(color);
    }

    private void applyShadowEffect(int color) {
        // Apply selected color to the image view with blending effects
        colorPickerImage.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
