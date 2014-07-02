package com.rsd.tryp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.Button;

import com.rsd.tryp.R;
import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.module.ApplicationModule;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.service.Service;
import com.rsd.tryp.util.AnimationDuration;
import com.rsd.tryp.util.BlurUtil;
import com.rsd.tryp.view.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";
    private static final int INITIALISATION_DELAY = 1500;

    private LoginView mLoginView;

    TrypApplication mContext;

    AuthenticationService mService;

    public LoginPresenterImpl(TrypApplication application, LoginView loginView) {
        mContext = application;
        mLoginView = loginView;
    }

    public void init() {
        mLoginView.setRegisterContainerOffscreen();
        mLoginView.setInputContainerOffscreen();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initialiseView();
            }
        }, AnimationDuration.STANDARD);
    }

    private void initialiseView() {
        setBlurredBackground();
        mLoginView.showTitle();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            mLoginView.translateTitle();
            mLoginView.showRegisterContainer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.showGoogleSignIn();
                    }
                }, 200);
                }

        }, INITIALISATION_DELAY);
    }

    @Override
    public void setBlurredBackground() {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_login);
        Bitmap blurredBitmap = BlurUtil.blurBitmap(bitmap, mContext);
        final BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), blurredBitmap);
        mLoginView.blurBackground(drawable);
    }

    @Override
    public void onInitialiseButtonSelected(Button button) {
        mLoginView.hideGoogleSignIn();
        mLoginView.translateRegisterContainerOut(isSignInButtonSelected(button));
        mLoginView.translateInputContainerIn();
        mLoginView.setKeyboardShowingListener();
    }

    @Override
    public void submitCredentials(String email, String password) {

    }

    @Override
    public void registerCredentials(String email, String password) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onInitialStateRequested() {
        mLoginView.removeKeyboardShowingListener();
        mLoginView.translateInputContainerOut();
        mLoginView.translateRegisterContainerIn();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginView.showGoogleSignIn();
            }
        }, AnimationDuration.FAST);
    }

    private boolean isSignInButtonSelected(Button button) {
        return button.getText().toString().equals(button.getResources().getString(R.string.button_text_sign_in));
    }
}
