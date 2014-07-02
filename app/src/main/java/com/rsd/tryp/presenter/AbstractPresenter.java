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
public abstract class AbstractPresenter {
    private ObjectGraph mPresenterObjectGraph;

    public AbstractPresenter() {
        mPresenterObjectGraph = TrypApplication.getInstance().createScopedGraph(getModules().toArray());
        mPresenterObjectGraph.inject(this);
    }

    protected abstract List<Object> getModules();
}
