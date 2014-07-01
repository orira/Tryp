package com.rsd.tryp.dto;

/**
 * Created by wadereweti on 2/07/14.
 */
public class AuthenticationDto {
    private final String username;
    private final String password;

    public AuthenticationDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
