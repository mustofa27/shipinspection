package com.example.mustofa.shipinspector;

/**
 * Created by mustofa on 3/3/2016.
 */
public class User {
    private int id_user;
    private String username,password,email,role;
    public User(int id_user, String username, String email, String password, String role)
    {
        setId_user(id_user);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }
    public User(String email, String password)
    {
        setEmail(email);
        setPassword(password);
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_user() {
        return id_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
