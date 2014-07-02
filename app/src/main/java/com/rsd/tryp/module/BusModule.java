package com.rsd.tryp.module;

import com.rsd.tryp.activity.AbstractActivity;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.service.AuthenticationServiceImpl;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module (
    library = true
)
public class BusModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
