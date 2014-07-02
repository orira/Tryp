package com.rsd.tryp.event;

import org.apache.http.HttpStatus;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class RegistrationEvent {
    private final HttpStatus mHttpStatus;

    public RegistrationEvent(HttpStatus mHttpStatus) {
        this.mHttpStatus = mHttpStatus;
    }

    public HttpStatus getHttpStatus() {
        return mHttpStatus;
    }
}
