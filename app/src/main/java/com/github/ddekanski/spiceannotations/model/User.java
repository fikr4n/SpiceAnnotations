package com.github.ddekanski.spiceannotations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

    private String name;
    private String bio;
    private String email;
    private String blog;
    private int followers;
    private String type;

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getBlog() {
        return blog;
    }

    public int getFollowers() {
        return followers;
    }

    public String getType() {
        return type;
    }
}