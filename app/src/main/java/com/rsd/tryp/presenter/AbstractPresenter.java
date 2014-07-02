package com.rsd.tryp.presenter;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.module.annotation.ForApplication;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class AbstractPresenter {
    private ObjectGraph mObjectGraph;

    //@Inject @ForApplication
    TrypApplication mTrypApplication;

    public AbstractPresenter() {
        mObjectGraph = mTrypApplication.getApplicationObjectGraph();
        mObjectGraph.inject(this);
    }

    /*protected List<Object> getModules() {
        return Arrays.<Object>asList(new ActivityModule(this));
    }*/
}
