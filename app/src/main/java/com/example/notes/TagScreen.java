package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class TagScreen extends AppCompatActivity {

    AppCompatButton btnaddtags;
    ImageView backtag;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_screen);

        btnaddtags = findViewById(R.id.btn_add_tags);
        backtag = findViewById(R.id.backtag);

        btnaddtags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTagDialog();
            }
        });

        backtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showAddTagDialog() {
        // Create a custom dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Inflate the dialog's custom layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_tags, null);
        dialog.setContentView(dialogView);

        // Set the dialog's width and height
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        // Set up the Cancel and Add buttons
        TextView buttonCancel = dialogView.findViewById(R.id.button_cancel);
        TextView buttonSave = dialogView.findViewById(R.id.button_save);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the save logic here
                // For example, you can retrieve the input from the EditText
                dialog.dismiss(); // Close the dialog
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
