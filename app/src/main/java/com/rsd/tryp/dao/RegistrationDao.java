package com.rsd.tryp.dao;

import com.rsd.tryp.dto.AuthenticationDto;

import org.apache.http.HttpStatus;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by wadereweti on 2/07/14.
 */
public interface RegistrationDao {

    @POST("/registerUser")
    HttpStatus register(@Body AuthenticationDto user, Callback<Void> callback);
}
