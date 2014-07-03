package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.activity.AbstractActivity;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 3/07/14.
 */
public class AbstractFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ObjectGraph objectGraph = TrypApplication.getInstance().createScopedGraph(getModules().toArray());
        objectGraph.inject(this);
        init();
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }

    protected void init() {
    }
}
