package com.rsd.tryp.module.annotation;

import android.content.Context;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.activity.LoginActivity;
import com.rsd.tryp.module.ApplicationModule;
import com.rsd.tryp.module.AuthenticationServiceModule;
import com.rsd.tryp.presenter.LoginPresenter;
import com.rsd.tryp.presenter.LoginPresenterImpl;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.view.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module(
    injects = LoginActivity.class,
    includes = AuthenticationServiceModule.class
)
public class LoginModule {
    private LoginView mLoginView;

    public LoginModule(LoginView loginView) {
        mLoginView = loginView;
    }

    @Provides @Singleton
    public LoginView provideView() {
        return mLoginView;
    }

    @Provides @Singleton
    public LoginPresenter providePresenter(TrypApplication application, LoginView loginView) {
        return new LoginPresenterImpl(application, loginView);
    }
}
