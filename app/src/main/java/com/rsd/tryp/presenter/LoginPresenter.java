package com.rsd.tryp.presenter;

import android.widget.Button;

/**
 * Created by Raukawa on 6/28/2014.
 */
public interface LoginPresenter {
    void blurBackground();
    void onInitialiseButtonSelected(Button button);
    void submitCredentials(String email, String password);
    void onSuccess();
}
