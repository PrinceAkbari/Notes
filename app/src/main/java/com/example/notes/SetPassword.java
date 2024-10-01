package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SetPassword extends AppCompatActivity {

    private StringBuilder password = new StringBuilder();
    private View[] dots;
    private int currentDot = 0;
    private Drawable originalDrawable; // Store the original drawable
    private boolean isPasswordComplete = false;
    private TextView instructionText; // Reference to the instruction text view
    private String originalInstructionText; // Store the original instruction text
    private AppCompatButton buttonCancel, buttonAction;
    ImageView pattern,backsetpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        pattern=findViewById(R.id.pattern);
        backsetpass=findViewById(R.id.backsetpass);



        pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SetPassword.this, SetPattern.class);
                startActivity(i);
            }
        });

        // Initialize dot indicators
        dots = new View[]{
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3),
                findViewById(R.id.dot4)
        };

        // Store the original drawable of buttons
        originalDrawable = getResources().getDrawable(R.drawable.circlebtn);

        // Initialize instruction text and store its original value
        instructionText = findViewById(R.id.instructionText);
        originalInstructionText = instructionText.getText().toString();

        // Set button click listeners
        setButtonListeners();
        setCancelAndActionListeners();
        backsetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setButtonListeners() {
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this::onButtonClick);
        }
    }

    private void setCancelAndActionListeners() {
        buttonCancel = findViewById(R.id.buttoncancle);
        buttonAction = findViewById(R.id.buttonaction);

        buttonCancel.setOnClickListener(view -> onCancelClick());
        buttonAction.setOnClickListener(view -> onActionClick());
    }

    private void onButtonClick(View view) {
        if (isPasswordComplete) return; // Prevent further input after 4 clicks

        AppCompatButton button = (AppCompatButton) view;
        button.setBackground(getResources().getDrawable(R.drawable.circle4));

        if (currentDot < 4) {
            dots[currentDot].setBackground(getResources().getDrawable(R.drawable.circle4));
            password.append(button.getText().toString());
            currentDot++;

            if (currentDot == 4) {
                isPasswordComplete = true;
                generatePassword();
            }
        }

        // After a short delay, revert the button to the original state
        button.postDelayed(() -> {
            button.setBackground(originalDrawable);
        }, 500); // Delay of 500ms, adjust as needed
    }

    private void generatePassword() {
        // Handle the generated password
        String finalPassword = password.toString();
        instructionText.setText("Password: " + finalPassword);
    }

    private void onCancelClick() {
        // Clear the password, reset the views to original state, and reset instruction text
        password.setLength(0);
        currentDot = 0;
        isPasswordComplete = false;

        for (View dot : dots) {
            dot.setBackground(getResources().getDrawable(R.drawable.circle2));
        }

        // Reset instruction text to its original value
        instructionText.setText(originalInstructionText);
    }

    private void onActionClick() {
        if (currentDot > 0) {
            // Remove the last entered number
            password.deleteCharAt(password.length() - 1);
            currentDot--;

            // Reset the last dot to the original state
            dots[currentDot].setBackground(getResources().getDrawable(R.drawable.circle2));

            // Update the instruction text by removing the last character from the displayed password
            instructionText.setText("Password: " + password.toString());

            // Allow further inputs again if the password is not complete
            isPasswordComplete = false;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
