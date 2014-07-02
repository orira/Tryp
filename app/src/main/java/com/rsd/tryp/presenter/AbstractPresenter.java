package com.rsd.tryp.presenter;

import com.rsd.tryp.TrypApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class AbstractPresenter {
    private ObjectGraph mObjectGraph;

    public AbstractPresenter(TrypApplication trypApplication) {
        mObjectGraph = trypApplication.getApplicationObjectGraph();
        mObjectGraph.inject(this);
    }

    /*protected List<Object> getModules() {
        return Arrays.<Object>asList(new ActivityModule(this));
    }*/
}
