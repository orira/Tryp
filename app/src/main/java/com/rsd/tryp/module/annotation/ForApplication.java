package com.rsd.tryp.module.annotation;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Qualifier @Retention(RUNTIME)
public @interface ForApplication {
}
