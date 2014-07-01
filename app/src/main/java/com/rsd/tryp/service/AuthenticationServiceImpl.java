package com.rsd.tryp.service;

import com.rsd.tryp.dao.AuthenticationDao;
import com.rsd.tryp.dao.Dao;
import com.rsd.tryp.dto.AuthenticationDto;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by wadereweti on 9/02/14.
 */
@Singleton
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    @Named(Dao.AUTHENTICATION_STUB)
    AuthenticationDao dao;

    @Override
    public void authenticateCredentials(String username, String password) {
        dao.authenticate(new AuthenticationDto(username, password));
    }

    @Override
    public void registerCredentials(String username, String password) {
        dao.register(new AuthenticationDto(username, password));
    }
}
