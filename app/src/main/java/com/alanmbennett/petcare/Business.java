package com.alanmbennett.petcare;

public class Business {
    private String name;
    private String address;
    private String image_url;

    public Business(){
        this.name = null;
        this.address = null;
        this.image_url = null;
    }

    public Business(String name, String address, String image_url){
        this.name = name;
        this.address = address;
        this.image_url = image_url;
    }
}
