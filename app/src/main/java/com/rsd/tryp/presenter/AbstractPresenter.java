package com.rsd.tryp.presenter;

import com.rsd.tryp.module.AuthenticationServiceModule;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 2/07/14.
 */
public class AbstractPresenter {

    public AbstractPresenter() {
        ObjectGraph objectGraph;

        if (this instanceof LoginPresenter) {
            ObjectGraph.create(new AuthenticationServiceModule()).inject(this);
        }
    }
}
