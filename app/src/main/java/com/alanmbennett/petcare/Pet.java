package com.alanmbennett.petcare;

public class Pet {
    private String petId;
    private String name;
    private String age;
    private String weight;
    private int Thumbnail;

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public Pet(String petId, String name, String age, String weight, int thumbnail) {
        this.petId = petId;
        this.name = name;
        this.age = age;
        this.weight = weight;
        Thumbnail = thumbnail;
    }
}
