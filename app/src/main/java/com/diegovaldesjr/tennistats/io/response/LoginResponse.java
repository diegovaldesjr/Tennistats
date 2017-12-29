package com.diegovaldesjr.tennistats.io.response;

/**
 * Created by diego on 25/11/2017.
 */

public class LoginResponse {

    private String username, access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
