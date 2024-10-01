package com.example.notes.adapter;

public class Note {
    private int id;
    private String title;
    private String text;
    private String date;
    private String category;

    public Note(int id, String title, String text, String date, String category) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;  // Initialize category
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {  // Getter for category
        return category;
    }

    public void setCategory(String category) {  // Setter for category
        this.category = category;
    }
}
