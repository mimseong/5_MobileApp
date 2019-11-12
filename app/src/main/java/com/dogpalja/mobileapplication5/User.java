package com.dogpalja.mobileapplication5;

public class User {
    int id;
    String email, username, image;

    public User(int id, String email, String username) {
        this.email = email;
        this.username = username;
    }

    public int getId() { return id; }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) { this.id = id; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
