package com.rsd.tryp.service;

import com.rsd.tryp.TrypApplication;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 3/07/14.
 */
public abstract class AbstractService {
    private ObjectGraph mServiceObjectGraph;

    public AbstractService() {
        mServiceObjectGraph = TrypApplication.getInstance().createScopedGraph(getModules().toArray());
        mServiceObjectGraph.inject(this);
    }

    protected abstract List<Object> getModules();
}
