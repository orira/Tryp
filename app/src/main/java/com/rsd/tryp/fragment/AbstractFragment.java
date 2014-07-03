package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.activity.AbstractActivity;
import com.rsd.tryp.module.InlineInputModule;
import com.rsd.tryp.view.InlineInputView;

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

        ObjectGraph objectGraph = ObjectGraph.create(new InlineInputModule((InlineInputView) this));
        objectGraph.inject(this);
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }
}
