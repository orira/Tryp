package com.rsd.tryp.module;

import com.rsd.tryp.presenter.LoginPresenterImpl;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.service.AuthenticationServiceImpl;
import com.rsd.tryp.service.Service;
import com.squareup.otto.Bus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by wadereweti on 9/02/14.
 */
@Module(
    injects = {
            AuthenticationServiceImpl.class
    },
    includes = {
        RestAdapterModule.class,
        BusModule.class
    },

    library = true
)
public class AuthenticationServiceModule {

    @Singleton
    @Provides
    @Named(Service.AUTHENTICATION_IMPL)
    AuthenticationService provideAuthenticationServiceImpl() {
        return new AuthenticationServiceImpl();
    }
}
