package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;

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
        //  ObjectGraph objectGraph = ((AbstractActivity) getActivity()).getObjectGraph();
        //  ObjectGraph objectGraph = TrypApplication.getInstance().getApplicationObjectGraph();
        //  objectGraph.plus(getModules().toArray());

        //ObjectGraph objectGraph = ObjectGraph.create(getModules());
        ObjectGraph objectGraph = ObjectGraph.create(getModule());
        objectGraph.inject(this);

        init();
    }

    protected void init() {
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }

    protected Object getModule() {
        return new Object();
    }
}
