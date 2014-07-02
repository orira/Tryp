package com.rsd.tryp.module;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.module.annotation.ForApplication;
import com.rsd.tryp.presenter.LoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module (
    injects = {
        TrypApplication.class,
        LoginPresenterImpl.class
    },
    includes = {
        AuthenticationServiceModule.class
    }
)
public class ApplicationModule {
    private TrypApplication mApplication;

    public ApplicationModule(TrypApplication application) {
        mApplication = application;
    }

    @Provides @ForApplication
    public TrypApplication provideApplication() {
        return mApplication;
    }
}
