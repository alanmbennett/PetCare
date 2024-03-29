package com.alanmbennett.petcare;

public class Reminder {
    private String title;
    private String description;
    private String time;
    private String reoccuring;
    private String petName;

    public Reminder(String title, String description, String time, String reoccuring, String petName) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.reoccuring = reoccuring;
        this.petName = petName;
    }
    public Reminder(){
        this.title = null;
        this.description = null;
        this.time = null;
        this.reoccuring = null;
        this.petName = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReoccuring() {
        return reoccuring;
    }

    public void setReoccuring(String reoccuring) {
        this.reoccuring = reoccuring;
    }

    public String getPetName() {
        return this.petName;
    }

    public void setPetid(String petName) {
        this.petName = petName;
    }
}
