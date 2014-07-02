package com.rsd.tryp.service;

import com.rsd.tryp.dao.LoginDao;
import com.rsd.tryp.dao.RegistrationDao;
import com.rsd.tryp.dto.AuthenticationDto;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import org.apache.http.HttpStatus;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wadereweti on 2/07/14.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    RestAdapter mRestAdapter;
    Bus mBus;

    public AuthenticationServiceImpl(RestAdapter restAdapter, Bus bus) {
        mRestAdapter = restAdapter;
        mBus = bus;
    }

    @Produce
    public boolean produceAuthenticateEvent(int httpStatus) {
        return httpStatus == HttpStatus.SC_OK;
    }

    @Produce
    public boolean produceRegistrationEvent(int httpStatus) {
        return httpStatus == HttpStatus.SC_OK;
    }

    @Override
    public void registerUser(AuthenticationDto dto) {
        RegistrationDao dao = mRestAdapter.create(RegistrationDao.class);
        Callback<Void> callback = new Callback<Void>(){

            @Override
            public void failure(RetrofitError error) {
                mBus.post(error.getResponse().getStatus());
            }

            @Override
            public void success(Void voidResponse, Response response) {
                mBus.post(produceRegistrationEvent(response.getStatus()));
            }
        };

        dao.register(dto, callback);
    }

    @Override
    public void authenticateUser(AuthenticationDto dto) {
        LoginDao dao = mRestAdapter.create(LoginDao.class);
        Callback<Void> callback = new Callback<Void>(){

            @Override
            public void failure(RetrofitError error) {
                mBus.post(produceAuthenticateEvent(error.getResponse().getStatus()));
            }

            @Override
            public void success(Void voidResponse, Response response) {
                mBus.post(produceAuthenticateEvent(response.getStatus()));
            }
        };

        dao.login(dto, callback);
    }
}
