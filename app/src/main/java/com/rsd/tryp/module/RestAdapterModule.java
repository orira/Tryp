package com.rsd.tryp.module;

import com.rsd.tryp.service.AuthenticationServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by Raukawa on 7/2/2014.
 */
@Module (
    library = true
)
public class RestAdapterModule {
    private static final String API_URL = "http://api.foo.bar";

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();
    }
}
