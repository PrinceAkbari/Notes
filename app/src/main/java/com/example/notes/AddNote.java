package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.adapter.Note;
import com.example.notes.adapter.NoteDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddNote extends AppCompatActivity {

    private ImageView backaddnote;
    private TextView textViewTitle, selectedItemTextView3,llselectcategory;
    private EditText editTextNote;
    private Button saveButton;
    private LinearLayout llSpinner3;  // Move declaration here
    private NoteDatabaseHelper noteDatabaseHelper;
    private ArrayList<Note> noteArrayList;
    private int noteId = -1;  // Default value for new note
    private String selectedSpinnerItem;

    @SuppressLint({"MissingInflatedId", "LocalSuppress", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        backaddnote = findViewById(R.id.backaddnote);
        textViewTitle = findViewById(R.id.textViewTitle);
        selectedItemTextView3 = findViewById(R.id.textView3);
        editTextNote = findViewById(R.id.editTextNote);
        TextView selectedCategoryTextView = findViewById(R.id.selectedcategory);
        llselectcategory = findViewById(R.id.llselectcategory);
        saveButton = findViewById(R.id.saveButton);

        // Initialize llSpinner3 before using it
        llSpinner3 = findViewById(R.id.llspinner3);  // Correct initialization
        Spinner spinner3 = findViewById(R.id.spinner3);

        noteDatabaseHelper = new NoteDatabaseHelper(this);

        noteId = getIntent().getIntExtra("NOTE_ID", -1);
        String title = getIntent().getStringExtra("TEXT");
        String selectedCategory = getIntent().getStringExtra("SELECTED_CATEGORY");
        selectedSpinnerItem = getIntent().getStringExtra("SELECTED_SPINNER_ITEM");

        textViewTitle.setText(title);
        selectedItemTextView3.setText(selectedSpinnerItem);

        if (noteId != -1) {
            loadNoteDetails();  // Load note details
            llSpinner3.setVisibility(View.GONE);
            selectedCategoryTextView.setText(selectedCategory);
            llselectcategory.setVisibility(View.VISIBLE);
            selectedCategoryTextView.setVisibility(View.VISIBLE);
        } else {
            // For new notes, show the Spinner and hide the selected category text
            selectedCategoryTextView.setVisibility(View.GONE);
            llselectcategory.setVisibility(View.GONE);
            llSpinner3.setVisibility(View.VISIBLE);
        }

        saveButton.setOnClickListener(v -> saveNote());
        backaddnote.setOnClickListener(v -> finish());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddNote.this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        llSpinner3.setOnClickListener(v -> spinner3.performClick());

        // Set the selected category for spinner3 based on the received intent data
        if (selectedSpinnerItem != null) {
            int spinnerPosition = adapter.getPosition(selectedSpinnerItem);  // Find position of selected item
            spinner3.setSelection(spinnerPosition);  // Set the position of spinner3 to the received category
        }

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItem = parent.getItemAtPosition(position).toString();  // Update selected category
                selectedItemTextView3.setText(selectedSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void loadNoteDetails() {
        noteArrayList = noteDatabaseHelper.getAllNotes();

        Note currentNote = null;
        for (Note note : noteArrayList) {
            if (note.getId() == noteId) {
                currentNote = note;
                break;
            }
        }

        if (currentNote != null) {
            textViewTitle.setText(currentNote.getTitle());
            editTextNote.setText(currentNote.getText());
            selectedSpinnerItem = currentNote.getCategory(); // Update category
            selectedItemTextView3.setText(selectedSpinnerItem); // Set category text
        } else {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNote() {
        String noteTitle = textViewTitle.getText().toString();
        String noteText = editTextNote.getText().toString();
        String category = selectedItemTextView3.getText().toString();  // Get the updated selected category

        if (noteText.isEmpty()) {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (noteId == -1) {
            // Adding a new note
            long newNoteId = noteDatabaseHelper.addNote(noteTitle, noteText, currentDate, category);
            if (newNoteId > -1) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Updating an existing note
            noteDatabaseHelper.updateNote(noteId, noteTitle, noteText, category);
            Toast.makeText(AddNote.this, "Notes Updated", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }
}
