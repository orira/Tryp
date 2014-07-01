package com.rsd.tryp.service;

/**
 * Created by wadereweti on 9/02/14.
 */
public interface AuthenticationService {
    public void authenticateCredentials(String username, String password);
    public void registerCredentials(String username, String password);
}
