package com.rsd.tryp.exception;

import org.apache.http.HttpStatus;

/**
 * Created by wadereweti on 2/07/14.
 */
public class RegistrationException extends NetworkException {

    public RegistrationException(HttpStatus statusCode) {
        super(statusCode);
    }
}
