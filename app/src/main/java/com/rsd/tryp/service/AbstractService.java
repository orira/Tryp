package com.rsd.tryp.service;

import com.rsd.tryp.TrypApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 3/07/14.
 */
public class AbstractService {
    private ObjectGraph mServiceObjectGraph;

    public AbstractService() {
        mServiceObjectGraph = TrypApplication.getInstance().createScopedGraph(getModules().toArray());
        mServiceObjectGraph.inject(this);
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }
}
