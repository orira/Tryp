package com.rsd.tryp.module;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.module.annotation.LoginModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module (
    injects = {
        TrypApplication.class
    },
    includes = {
        LoginModule.class
    }
)
public class ApplicationModule {
    private TrypApplication mApplication;

    public ApplicationModule(TrypApplication application) {
        mApplication = application;
    }

    @Provides
    public TrypApplication provideApplication() {
        return mApplication;
    }
}
