package com.rsd.tryp.module;

import dagger.Module;

@Module(
        includes = {
                AuthenticationServiceModule.class
        }
)
public class BaseModule {
}
