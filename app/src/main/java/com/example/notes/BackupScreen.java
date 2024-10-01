package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BackupScreen extends AppCompatActivity {

    LinearLayout backup,email,restore;
    RelativeLayout back_backup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_screen);
        backup=findViewById(R.id.backup);
        email=findViewById(R.id.email);
        restore=findViewById(R.id.restore);
        back_backup=findViewById(R.id.back_backup);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTagDialog();
            }
        });
        back_backup.setOnClickListener(new View.OnClickListener() {
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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.backup, null);
        dialog.setContentView(dialogView);

        // Set the dialog's width and height
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        // Set up the Cancel and Add buttons
        TextView buttonCancel = dialogView.findViewById(R.id.button_cancel);
        TextView buttonbackupnow = dialogView.findViewById(R.id.button_backupnow);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        buttonbackupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the save logic here
                // For example, you can retrieve the input from the EditText
                dialog.dismiss();
                Intent i=new Intent(BackupScreen.this, BackupFile.class);
                startActivity(i);

            }
        });


        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}