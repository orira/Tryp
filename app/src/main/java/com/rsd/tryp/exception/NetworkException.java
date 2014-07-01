package com.rsd.tryp.exception;

import org.apache.http.HttpStatus;

/**
 * Created by wadereweti on 2/07/14.
 */
public class NetworkException extends Exception {
    private final HttpStatus statusCode;

    public NetworkException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
