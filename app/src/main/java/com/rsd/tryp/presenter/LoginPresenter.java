package com.rsd.tryp.presenter;

/**
 * Created by Raukawa on 6/28/2014.
 */
public interface LoginPresenter {
    void blurBackground();
    void submitCredentials(String email, String password);
    void validateCredentials(String email, String password);
    void onSuccess();
}
