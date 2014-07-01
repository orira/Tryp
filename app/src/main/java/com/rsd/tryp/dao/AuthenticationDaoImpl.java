package com.rsd.tryp.dao;

import com.rsd.tryp.dto.AuthenticationDto;
import com.rsd.tryp.exception.LoginException;
import com.rsd.tryp.exception.RegistrationException;

import org.apache.http.HttpStatus;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wadereweti on 2/07/14.
 */
public class AuthenticationDaoImpl implements AuthenticationDao {

    private static final String API_URL = "http://api.foo.bar";

    private RestAdapter restAdapter;

    public AuthenticationDaoImpl() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();
    }

    @Override
    public void register(AuthenticationDto dto) {
        RegistrationDao dao = restAdapter.create(RegistrationDao.class);
        Callback<Void> callback = new Callback<Void>(){

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
            }

            @Override
            public void success(Void voidResponse, Response response) {
                //BusProvider.getInstance().post(produceFlickrEvent(flickrResult));
            }
        };

        dao.register(dto, callback);
    }

    @Override
    public void authenticate(AuthenticationDto dto) {
        LoginDao dao = restAdapter.create(LoginDao.class);
        Callback<Void> callback = new Callback<Void>(){

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
            }

            @Override
            public void success(Void voidResponse, Response response) {
                //BusProvider.getInstance().post(produceFlickrEvent(flickrResult));
            }
        };

        dao.login(dto, callback);
    }
}
