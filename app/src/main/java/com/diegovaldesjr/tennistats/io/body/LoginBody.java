package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 25/11/2017.
 */

public class LoginBody {

    private String username, password;

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
