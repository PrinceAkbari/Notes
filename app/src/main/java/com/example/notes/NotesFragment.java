package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.notes.adapter.Note;
import com.example.notes.adapter.NoteDatabaseHelper;
import com.example.notes.adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private static final int REQUEST_CODE_ADD_NOTE = 1;
    private static final int REQUEST_CODE_EDIT_NOTE = 2;

    private RecyclerView recyclerView;
    private View emptyView;
    private NotesAdapter notesAdapter;
    private ArrayList<Note> notesList = new ArrayList<>();
    private NoteDatabaseHelper noteDatabaseHelper;
    private RelativeLayout popdata;
    private Spinner spinner1;
    private String selectedCategory = "All Notes";  // Default category

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotes(selectedCategory);  // Load notes based on selected category
    }

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        LinearLayout llSpinner1 = view.findViewById(R.id.llspinner1);
        // Set up the spinner
        spinner1 = view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        llSpinner1.setOnClickListener(v -> spinner1.performClick());

        TextView selectedItemTextView1 = view.findViewById(R.id.textView1);

        // Set up the onItemSelectedListener
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();  // Update selected category
                selectedItemTextView1.setText(selectedCategory);
                loadNotes(selectedCategory);  // Load notes based on category
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);
        popdata = view.findViewById(R.id.popdata);

        noteDatabaseHelper = new NoteDatabaseHelper(getContext());

        notesAdapter = new NotesAdapter(requireActivity(), notesList, recyclerView, emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notesAdapter);

        return view;
    }


    private void loadNotes(String category) {
        notesList.clear();

        if (category.equals("All Notes")) {
            notesList.addAll(noteDatabaseHelper.getAllNotes());
        } else {
            notesList.addAll(noteDatabaseHelper.getNotesByCategory(category));
        }

        notesAdapter.notifyDataSetChanged();

        if (notesList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            loadNotes(selectedCategory);  // Refresh after adding or editing
        }
    }
}
