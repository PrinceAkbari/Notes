package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class settingsFragment extends Fragment {

    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox7, checkBox8;
    LinearLayout alignment, fontsize;
    LinearLayout Theme;
    TextView fonttextview,alignmenttextview;

    public settingsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize checkboxes
        checkBox1 = view.findViewById(R.id.checkbox1);
        checkBox2 = view.findViewById(R.id.checkbox2);
        checkBox3 = view.findViewById(R.id.checkbox3);
        checkBox4 = view.findViewById(R.id.checkbox4);  // Ensure that checkBox4 is initialized
        checkBox5 = view.findViewById(R.id.checkbox5);
        checkBox7 = view.findViewById(R.id.checkbox7);
        checkBox8 = view.findViewById(R.id.checkbox8);

        alignment = view.findViewById(R.id.alignment);
        fontsize = view.findViewById(R.id.fontsize);
        Theme = view.findViewById(R.id.Theme);

        fonttextview = view.findViewById(R.id.fonttextview);
        alignmenttextview = view.findViewById(R.id.alignmenttextview);

        // Set up checkboxes with custom color change behavior
        setUpCheckBox(checkBox1);
        setUpCheckBox(checkBox2);
        setUpCheckBox(checkBox3);
        setUpCheckBox(checkBox4);  // Properly setting up checkBox4
        setUpCheckBox(checkBox5);
        setUpCheckBox(checkBox7);
        setUpCheckBox(checkBox8);

        // Find the language layout
        LinearLayout languageLayout = view.findViewById(R.id.languageLayout);

        // Set OnClickListener on the language layout
        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Language_Screen activity
                Intent intent = new Intent(getActivity(), Language_Screen.class);
                startActivity(intent);
            }
        });

        Theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Theme.class);
                startActivity(intent);
            }
        });

        alignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlignmentDialog();
            }
        });

        fontsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontsizeDialog();
            }
        });
        return view;
    }

    private void setUpCheckBox(CheckBox checkBox) {
        if (checkBox != null) {
            // Get original color of the checkbox
            ColorStateList originalColor = checkBox.getButtonTintList();

            // Set OnCheckedChangeListener to change the color when checked/unchecked
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Change color to yellow when checked
                    checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
                } else {
                    // Revert to original color when unchecked
                    checkBox.setButtonTintList(originalColor);
                }
            });
        }
    }

    private void showAlignmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.alignment, null);

        // Find the radio buttons in the dialog
        RadioButton leftRadioButton = view.findViewById(R.id.left);
        RadioButton centerRadioButton = view.findViewById(R.id.center);
        RadioButton rightRadioButton = view.findViewById(R.id.right);

        // Set the color state list programmatically (optional if you already set it in XML)
        ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.radio_button_color);
        if (colorStateList != null) {
            leftRadioButton.setButtonTintList(colorStateList);
            centerRadioButton.setButtonTintList(colorStateList);
            rightRadioButton.setButtonTintList(colorStateList);
        }

        // Set OnCheckedChangeListener for the radio group
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String text = "";
            if (checkedId == R.id.left) {
                text = "Left";
            } else if (checkedId == R.id.center) {
                text = "Center";
            } else if (checkedId == R.id.right) {
                text = "Right";
            }
            // Set the text immediately on selection
            alignmenttextview.setText(text);
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        // Set an OnDismissListener to update the TextView after the dialog is dismissed
        dialog.setOnDismissListener(dialogInterface -> {
            // Retrieve the selected radio button
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            String text = "";
            if (checkedRadioButtonId == R.id.left) {
                text = "Left";
            } else if (checkedRadioButtonId == R.id.center) {
                text = "Center";
            } else if (checkedRadioButtonId == R.id.right) {
                text = "Right";
            }
            alignmenttextview.setText(text);
        });

        dialog.show();
    }

    private void showFontsizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fontsize, null);

        // Find the radio buttons in the dialog
        RadioButton fifty = view.findViewById(R.id.fifty);
        RadioButton seventyfive = view.findViewById(R.id.seventyfive);
        RadioButton ninety = view.findViewById(R.id.ninety);
        RadioButton onehundred = view.findViewById(R.id.onehundred);
        RadioButton onehundredtwentyfive = view.findViewById(R.id.onehundredtwentyfive);
        RadioButton onehundredfifty = view.findViewById(R.id.onehundredfifty);
        RadioButton onehundredseventyfive = view.findViewById(R.id.onehundredseventyfive);
        RadioButton twohundred = view.findViewById(R.id.twohundred);
        RadioButton twohundredfifty = view.findViewById(R.id.twohundredfifty);

        // Set the color state list programmatically (optional if you already set it in XML)
        ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.radio_button_color);
        if (colorStateList != null) {
            fifty.setButtonTintList(colorStateList);
            seventyfive.setButtonTintList(colorStateList);
            ninety.setButtonTintList(colorStateList);
            onehundred.setButtonTintList(colorStateList);
            onehundredtwentyfive.setButtonTintList(colorStateList);
            onehundredfifty.setButtonTintList(colorStateList);
            onehundredseventyfive.setButtonTintList(colorStateList);
            twohundred.setButtonTintList(colorStateList);
            twohundredfifty.setButtonTintList(colorStateList);
        }

        // Set OnCheckedChangeListener for the radio group
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RadioGroup radioGroup = view.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String text = "";
            if (checkedId == R.id.fifty) {
                text = "50%";
            } else if (checkedId == R.id.seventyfive) {
                text = "75%";
            } else if (checkedId == R.id.ninety) {
                text = "90%";
            } else if (checkedId == R.id.onehundred) {
                text = "100%";
            } else if (checkedId == R.id.onehundredtwentyfive) {
                text = "125%";
            } else if (checkedId == R.id.onehundredfifty) {
                text = "150%";
            } else if (checkedId == R.id.onehundredseventyfive) {
                text = "175%";
            } else if (checkedId == R.id.twohundred) {
                text = "200%";
            } else if (checkedId == R.id.twohundredfifty) {
                text = "250%";
            }
            // Set the text immediately on selection
            fonttextview.setText(text);
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        // Set an OnDismissListener to update the TextView after the dialog is dismissed
        dialog.setOnDismissListener(dialogInterface -> {
            // Retrieve the selected radio button
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            String text = "";
            if (checkedRadioButtonId == R.id.fifty) {
                text = "50%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.seventyfive) {
                text = "75%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.ninety) {
                text = "90%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.onehundred) {
                text = "100%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.onehundredtwentyfive) {
                text = "125%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.onehundredfifty) {
                text = "150%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.onehundredseventyfive) {
                text = "175%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.twohundred) {
                text = "200%";
                dialog.dismiss();
            } else if (checkedRadioButtonId == R.id.twohundredfifty) {
                text = "250%";

                dialog.dismiss();
            }
            fonttextview.setText(text);
            dialog.dismiss();

        });

        dialog.show();
    }


}
