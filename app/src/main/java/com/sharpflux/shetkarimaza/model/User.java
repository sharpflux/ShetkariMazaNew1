package com.sharpflux.shetkarimaza.model;

public class User {

    private int id;
    private String username, email, mobile, middlename, lastname;//IsCompleteRegistration;

    /*public User(int id, String username, String email, String mobile,
                String middlename, String lastname, String isCompleteRegistration) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.middlename = middlename;
        this.lastname = lastname;
        IsCompleteRegistration = isCompleteRegistration;
    }
*/
    public User(int id, String username, String email, String mobile, String middlename, String lastname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}
