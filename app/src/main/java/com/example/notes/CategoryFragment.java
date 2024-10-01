package com.example.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.adapter.Category;
import com.example.notes.adapter.CategoryAdapter;
import com.example.notes.adapter.CategoryDatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class CategoryFragment extends Fragment {


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryDatabaseHelper dbHelper;
    private ArrayList<Category> categoryList;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);


        categoryRecyclerView = view.findViewById(R.id.categoryrecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new CategoryDatabaseHelper(getContext());

        categoryList = new ArrayList<>();
        categoryList.addAll(dbHelper.getAllCategories());
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        return view;
    }


}
