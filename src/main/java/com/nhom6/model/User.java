package com.nhom6.model;

public class User {

    private String userId;
    private String fullName;
    private String phone;
    private String email;

    public User() {
    }

    public User(String userId, String fullName, String phone, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    public String getUserId() {

        return userId;
    }

    public String getFullName() {

        return fullName;
    }

    public String getPhone() {

        return phone;
    }

    public String getEmail() {

        return email;
    }

    @Override
    public String toString() {
        return fullName;
    }
}