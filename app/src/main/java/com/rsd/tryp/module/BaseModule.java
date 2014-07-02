package com.rsd.tryp.module;

import dagger.Module;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module (
    includes = {
        ApplicationModule.class,
        AuthenticationServiceModule.class,
        BusModule.class,
        LoginModule.class,
        RestAdapterModule.class
    }
)
public class BaseModule {

}
