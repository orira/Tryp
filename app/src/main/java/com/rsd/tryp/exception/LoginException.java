package com.rsd.tryp.exception;

import org.apache.http.HttpStatus;

/**
 * Created by wadereweti on 2/07/14.
 */
public class LoginException extends NetworkException {

    public LoginException(HttpStatus statusCode) {
        super(statusCode);
    }
}
