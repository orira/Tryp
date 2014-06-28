package com.rsd.tryp.service;

import android.os.Handler;

import com.rsd.tryp.presenter.LoginPresenter;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class LoginServiceImpl implements LoginService {

    private LoginPresenter mPresenter;

    public LoginServiceImpl(LoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void login(String username, String password) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.onSuccess();
            }
        }, 2000);
    }
}
