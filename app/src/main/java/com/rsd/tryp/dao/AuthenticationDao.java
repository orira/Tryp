package com.rsd.tryp.dao;

import com.rsd.tryp.dto.AuthenticationDto;
import com.rsd.tryp.exception.NetworkException;

/**
 * Created by wadereweti on 2/07/14.
 */
public interface AuthenticationDao {
    void register(AuthenticationDto dto);
    void authenticate(AuthenticationDto dto);
}
