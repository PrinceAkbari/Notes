package com.example.notes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.AddNote;
import com.example.notes.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> notesList;
    private Activity activity;
    private NoteDatabaseHelper noteDatabaseHelper;
    private RecyclerView recyclerView;
    private View emptyView;
    private int noteIdToDelete;
    private int positionToDelete;

    public NotesAdapter(FragmentActivity fragmentActivity, ArrayList<Note> notesList, RecyclerView recyclerView, View emptyView) {
        this.activity = fragmentActivity;
        this.notesList = notesList;
        this.noteDatabaseHelper = new NoteDatabaseHelper(activity);
        this.recyclerView = recyclerView;
        this.emptyView = emptyView;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);

        holder.titleTextView.setText(note.getTitle());
        holder.noteTextView.setText(note.getText());
        holder.dateTextView.setText(note.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AddNote.class);
            intent.putExtra("NOTE_ID", note.getId());
            intent.putExtra("TEXT", note.getTitle());
            intent.putExtra("SELECTED_CATEGORY", note.getCategory()); // Pass the selected category
            activity.startActivity(intent);
        });

        holder.popdata.setOnClickListener(v -> showPopupMenu(v, note.getId(), position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, noteTextView, dateTextView;
        RelativeLayout popdata;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            noteTextView = itemView.findViewById(R.id.textViewNote);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            popdata = itemView.findViewById(R.id.popdata);
        }
    }

    private void showPopupMenu(View view, int noteId, int position) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.pop_data, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Deletedata) {
                noteIdToDelete = noteId;
                positionToDelete = position;
                showDeleteDialog();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void showDeleteDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.categorydelete, null);

        // Create the AlertDialog and assign it to the dialog variable
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();  // Assign the created dialog to the local variable

        // Find and set up the buttons in the dialog
        dialogView.findViewById(R.id.button_cancel).setOnClickListener(v -> {
            // Dismiss the dialog on Cancel
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.button_delete).setOnClickListener(v -> {
            // Handle the delete action
            deleteNoteConfirmed(noteIdToDelete, positionToDelete);

            // Dismiss the dialog after deleting
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    private void deleteNoteConfirmed(int noteId, int position) {
        if (position >= 0 && position < notesList.size()) {
            noteDatabaseHelper.deleteNote(noteId); // Delete from the database

            notesList.remove(position); // Remove from the list
            notifyItemRemoved(position); // Notify adapter of item removal
            notifyItemRangeChanged(position, notesList.size()); // Ensure that item range is updated

            // Check if the list is empty and update the visibility accordingly
            if (notesList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        } else {
            // As a fallback to handle cases where position might be invalid, refresh entire list
            notifyDataSetChanged();
            Toast.makeText(activity, "Error: Invalid position", Toast.LENGTH_SHORT).show();
        }
    }
}
