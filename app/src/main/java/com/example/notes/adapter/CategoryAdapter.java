package com.example.notes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<Category> categoryList;
    private CategoryDatabaseHelper categoryDatabaseHelper;  // Initialize the database helper
    private int noteIdToDelete;
    private int positionToDelete;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;

        // Initialize the CategoryDatabaseHelper here
        categoryDatabaseHelper = new CategoryDatabaseHelper(context);  // Ensure this is not null
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(categoryList.get(position).getName());
        Log.e("@@@TAG", categoryList.get(position).getName());
        holder.categoryDate.setText(categoryList.get(position).getDate());

        holder.categorypop.setOnClickListener(v -> showPopupMenu(v, category.getId(), position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName, categoryDate;
        ImageView categoryIcon;
        LinearLayout categorypop;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categorytext);
            categoryDate = itemView.findViewById(R.id.categorydate);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categorypop = itemView.findViewById(R.id.categorypop);
        }
    }

    private void showPopupMenu(View view, int noteId, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.category_folder_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.category_delete) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.categorydelete, null);

        // Create the AlertDialog and assign it to the dialog variable
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        if (position >= 0 && position < categoryList.size()) {
            // Check if categoryDatabaseHelper is initialized
            if (categoryDatabaseHelper != null) {
                categoryDatabaseHelper.deleteNote(noteId); // Delete from the database
            } else {
                Log.e("CategoryAdapter", "DatabaseHelper is null!");
                return;
            }

            categoryList.remove(position); // Remove from the list
            notifyItemRemoved(position); // Notify adapter of item removal
            notifyItemRangeChanged(position, categoryList.size()); // Ensure that item range is updated
        }
    }
}
