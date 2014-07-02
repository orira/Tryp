package com.rsd.tryp;

import android.app.Application;

import com.rsd.tryp.module.ApplicationModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class TrypApplication extends Application {

    private ObjectGraph mApplicationObjectGraph;

    private static TrypApplication mTrypApplication;

    public TrypApplication() {
        mTrypApplication = this;
    }

    public static TrypApplication getInstance() {
        return mTrypApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationObjectGraph = ObjectGraph.create(getModules().toArray());
        mApplicationObjectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new ApplicationModule(this));
    }

    public ObjectGraph getApplicationObjectGraph() {
        return mApplicationObjectGraph;
    }

    public ObjectGraph createScopedGraph(Object ... modules) {
        return mApplicationObjectGraph.plus(modules);
    }
}
