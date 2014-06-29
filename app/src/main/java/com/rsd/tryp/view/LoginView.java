package com.rsd.tryp.view;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Raukawa on 6/28/2014.
 */
public interface LoginView {
    void blurBackground(BitmapDrawable drawable);
    void showTitle();
    void translateTitle();
    void hideInputContainer();
    void showInputContainer();
    void showProgress();
    void hideProgress();

    void setKeyboardShowingListener();
    void removeKeyboardShowingListener();

    void hideRegisterContainer();
    void translateRegisterContainer(boolean translateLeft);
    void showRegisterContainer();

    void translateInputContainerOut();
    void translateRegisterContainerIn();
}
