package com.alanmbennett.petcare;


import java.io.Serializable;

public class Pet implements Serializable {
    private String petId;
    private String name;
    private String weight;
    private int thumbnail;
    private String birthdate;

    public Pet(String petId, String name, String birthdate, String weight, int thumbnail) {
        this.petId = petId;
        this.name = name;
        this.birthdate = birthdate;

        this.weight = weight;
        this.thumbnail = thumbnail;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.birthdate = birthdate;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getWeight() {
        return weight;
    }

    public int getThumbnail() {
        return thumbnail;
    }

}
