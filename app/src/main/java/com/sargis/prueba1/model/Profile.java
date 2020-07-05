package com.sargis.prueba1.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Profile implements Serializable {
    private String name;
    private String email;
    private String token;

    public Profile(){}
    public Profile(String name,String email,String token) {
        this.name = name;
        this.email=email;
        this.token=token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
