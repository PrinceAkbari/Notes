package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.adapter.Category;
import com.example.notes.adapter.CategoryAdapter;
import com.example.notes.adapter.CategoryDatabaseHelper;
import com.example.notes.adapter.Note;
import com.example.notes.adapter.NotesAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView text;
    private ImageView back, favorites, search;
    ImageView drawer;
    private FloatingActionButton fab;
    private LinearLayout popmenu, catrgorypopmenu,calenderpopmenu;
    private LinearLayout language, recycelbin, favorite, theme, settings, reminder, backup, lock, tag;
    private BottomNavigationView bottomNavigationView;
    private long backPressedTime;
    private Toast backToast;
    private NotesAdapter notesAdapter;
    private ArrayList<Note> notesList = new ArrayList<>();
    private static final int REQUEST_CODE_ADD_NOTE = 1;
    private NotesFragment notesFragment;
    private String which = "";
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryDatabaseHelper dbHelper;
    private ArrayList<Category> categoryList;




    @SuppressLint({"CutPasteId", "ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper = new CategoryDatabaseHelper(this);
        which = getIntent().getStringExtra("Which");
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawer = findViewById(R.id.open_drawer_button);
        favorites = findViewById(R.id.favorites);
        popmenu = findViewById(R.id.popmenu1);
        search = findViewById(R.id.search);
        catrgorypopmenu = findViewById(R.id.catrgorypopmenu);
        calenderpopmenu = findViewById(R.id.calenderpopmenu);
        text = findViewById(R.id.text);
        fab = findViewById(R.id.fab);
        back = findViewById(R.id.back);
        language = findViewById(R.id.language);
        recycelbin = findViewById(R.id.recycelbin);
        favorite = findViewById(R.id.favorite);
        theme = findViewById(R.id.theme);
        settings = findViewById(R.id.setting);
        reminder = findViewById(R.id.reminder);
        backup = findViewById(R.id.backup);
        lock = findViewById(R.id.lock);
        tag = findViewById(R.id.tags);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.activity_addnote_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();
            dialog.show();

            TextView btnCancel = dialogView.findViewById(R.id.button_cancel);
            TextView btnOk = dialogView.findViewById(R.id.button_ok);
            TextInputEditText edt = dialogView.findViewById(R.id.TextInputEditText);
            LinearLayout llSpinner2 = dialogView.findViewById(R.id.llspinner2);
            Spinner spinner2 = dialogView.findViewById(R.id.spinner2);
            TextView selectedItemTextView2 = dialogView.findViewById(R.id.textView2);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Home.this,
                    R.array.spinner_items, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);

            llSpinner2.setOnClickListener(v1 -> spinner2.performClick());

            final String[] selectedSpinnerItem = {""};

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedSpinnerItem[0] = parent.getItemAtPosition(position).toString();
                    selectedItemTextView2.setText(selectedSpinnerItem[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });



            catrgorypopmenu.setOnClickListener(this::categoryshowPopupMenu);





            RadioButton radioTextNote = dialogView.findViewById(R.id.radioTextNote);
            RadioButton radioChecklist = dialogView.findViewById(R.id.radioChecklist);

            ColorStateList colorStateList = ContextCompat.getColorStateList(Home.this, R.color.radio_button_color);

            if (radioTextNote != null && radioChecklist != null) {
                radioTextNote.setButtonTintList(colorStateList);
                radioChecklist.setButtonTintList(colorStateList);
            }

            btnOk.setOnClickListener(v12 -> {
                String text = edt.getText().toString();
                Intent intent = new Intent(Home.this, AddNote.class);
                intent.putExtra("NOTE_ID", -1);
                intent.putExtra("TEXT", text);
                intent.putExtra("SELECTED_SPINNER_ITEM", selectedSpinnerItem[0]);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
                dialog.dismiss();
            });

            btnCancel.setOnClickListener(v13 -> dialog.dismiss());
        });



        if (which == "NOTE"){
            notesFragment = new NotesFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, notesFragment)
                    .commit();
        }


        setupClickListeners();
        bottomNavigationView.setSelectedItemId(R.id.notes);

        drawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        popmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });



        catrgorypopmenu.setOnClickListener(this::categoryshowPopupMenu);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.category) {
                logselected(new CategoryFragment(), true);
                updateToolbarForCategory();
                return true;
            } else if (id == R.id.notes) {
                logselected(new NotesFragment(), true);
                updateToolbarForNotes();
                return true;
            } else if (id == R.id.calendar) {
                logselected(new CalanderFragment(), true);
                updateToolbarForCalendar();
                return true;
            } else if (id == R.id.settings) {
                logselected(new settingsFragment(), true);
                updateToolbarForSettings();
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.notes);
    }
    private void showAddCategoryDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextInputEditText categoryEditText = dialogView.findViewById(R.id.categoryedittext);
        dialogView.findViewById(R.id.Categorycancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.Categorysave).setOnClickListener(v -> {
            String categoryName = categoryEditText.getText().toString().trim();
            if (!categoryName.isEmpty()) {
                // Add category to the database
                dbHelper.addCategory(categoryName);
                logselected(new CategoryFragment(), true);

            }
            dialog.dismiss();
        });
    }

    private void setupClickListeners() {
        language.setOnClickListener(v -> startActivity(new Intent(Home.this, Language_Screen.class)));
        recycelbin.setOnClickListener(v -> startActivity(new Intent(Home.this, RecycleBin.class)));
        favorite.setOnClickListener(v -> startActivity(new Intent(Home.this, Favorites.class)));
        theme.setOnClickListener(v -> startActivity(new Intent(Home.this, Theme.class)));
        lock.setOnClickListener(v -> startActivity(new Intent(Home.this, LockScreen.class)));
        tag.setOnClickListener(v -> startActivity(new Intent(Home.this, TagScreen.class)));
        reminder.setOnClickListener(v -> startActivity(new Intent(Home.this, ReminderScreen.class)));
        backup.setOnClickListener(v -> startActivity(new Intent(Home.this, BackupScreen.class)));
        settings.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), settingsFragment.class)));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.opt_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    private void categoryshowPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(Home.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.category_mennu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.addcatrgory) {
                showAddCategoryDialog();
            }
            return false;
        });
        popupMenu.show();
    }


    private void logselected(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Get the current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        // Check if the current fragment is NotesFragment
        if (currentFragment instanceof NotesFragment) {
            // Exit the app
            finishAffinity();  // This closes all activities and exits the app
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (bottomNavigationView != null) {
            int selectedItemId = bottomNavigationView.getSelectedItemId();

            if (selectedItemId == R.id.notes) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    if (backToast != null) {
                        backToast.cancel();
                    }
                    super.onBackPressed();
                    return;
                } else {
                    backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                backPressedTime = System.currentTimeMillis();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.notes);
            }
        } else {
            // Handle the case where bottomNavigationView is null
            super.onBackPressed();
        }
    }
    private void updateToolbarForCategory() {
        text.setText("Category");
        drawer.setVisibility(View.INVISIBLE);
        favorites.setVisibility(View.GONE);
        popmenu.setVisibility(View.INVISIBLE);
        search.setVisibility(View.GONE);
        calenderpopmenu.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        catrgorypopmenu.setVisibility(View.VISIBLE);
    }

    private void updateToolbarForNotes() {
        text.setText("");
        drawer.setVisibility(View.VISIBLE);
        favorites.setVisibility(View.VISIBLE);
        popmenu.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        calenderpopmenu.setVisibility(View.GONE);
        catrgorypopmenu.setVisibility(View.INVISIBLE);
    }

    private void updateToolbarForCalendar() {
        text.setText("Calendar");
        drawer.setVisibility(View.GONE);
        favorites.setVisibility(View.GONE);
        popmenu.setVisibility(View.INVISIBLE);
        calenderpopmenu.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        catrgorypopmenu.setVisibility(View.GONE);
    }

    private void updateToolbarForSettings() {
        text.setText("Settings");
        drawer.setVisibility(View.GONE);
        favorites.setVisibility(View.GONE);
        calenderpopmenu.setVisibility(View.GONE);
        popmenu.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        catrgorypopmenu.setVisibility(View.GONE);
    }

}
