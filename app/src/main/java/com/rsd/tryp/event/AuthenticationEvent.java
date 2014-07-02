package com.rsd.tryp.event;

import org.apache.http.HttpStatus;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class AuthenticationEvent {
    private final HttpStatus mHttpStatus;

    public AuthenticationEvent(HttpStatus httpStatus) {
        this.mHttpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return mHttpStatus;
    }
}
