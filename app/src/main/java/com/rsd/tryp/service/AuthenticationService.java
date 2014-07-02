package com.rsd.tryp.service;

import com.rsd.tryp.dto.AuthenticationDto;
import com.rsd.tryp.exception.NetworkException;

/**
 * Created by wadereweti on 2/07/14.
 */
public interface AuthenticationService {
    void registerUser(AuthenticationDto dto);
    void authenticateUser(AuthenticationDto dto);
}
