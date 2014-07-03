package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.activity.AbstractActivity;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 3/07/14.
 */
public class AbstractFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ObjectGraph objectGraph = ((AbstractActivity) getActivity()).getObjectGraph();
        List<Object> fragmentModules = getModules();
        mObjectGraph = appGraph.plus(fragmentModules.toArray());

    }

    protected ObjectGraph getObjectGraph() {
        return ((AbstractActivity) getActivity()).getObjectGraph();
    }
}
