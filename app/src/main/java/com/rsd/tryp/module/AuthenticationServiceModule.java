package com.rsd.tryp.module;

import com.rsd.tryp.presenter.LoginPresenter;
import com.rsd.tryp.presenter.LoginPresenterImpl;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.service.AuthenticationServiceImpl;
import com.rsd.tryp.service.Service;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 9/02/14.
 */
@Module(
    injects = LoginPresenterImpl.class
)
public class AuthenticationServiceModule {

    @Singleton
    @Provides
    @Named(Service.AUTHENTICATION_IMPL)
    AuthenticationService provideLoginServiceImpl() {
        return new AuthenticationServiceImpl();
    }
}
