package com.rsd.tryp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.rsd.tryp.TrypApplication;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public abstract class AbstractActivity extends FragmentActivity {

    protected ObjectGraph mActivityObjectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityObjectGraph = ((TrypApplication) getApplication()).createScopedGraph(getModules().toArray());
        mActivityObjectGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as soon as possible.
        super.onDestroy();
        mActivityObjectGraph = null;
    }

    protected abstract List<Object> getModules();
}
