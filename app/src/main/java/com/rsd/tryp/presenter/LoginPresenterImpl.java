package com.rsd.tryp.presenter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.Button;

import com.rsd.tryp.R;
import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.module.AuthenticationServiceModule;
import com.rsd.tryp.module.annotation.ForApplication;
import com.rsd.tryp.service.AuthenticationService;
import com.rsd.tryp.service.Service;
import com.rsd.tryp.util.AnimationDuration;
import com.rsd.tryp.util.BlurUtil;
import com.rsd.tryp.util.OrientationUtil;
import com.rsd.tryp.view.LoginView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";
    private static final int INITIALISATION_DELAY = 1500;

    private LoginView mLoginView;

    @Inject @ForApplication
    TrypApplication mContext;

    @Inject
    Resources mResources;

    @Inject @Named(Service.AUTHENTICATION_IMPL)
    AuthenticationService mService;

    public LoginPresenterImpl(LoginView loginView) {
        super();
        mLoginView = loginView;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AuthenticationServiceModule());
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
        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.img_login);
        Bitmap blurredBitmap = BlurUtil.blurBitmap(bitmap, mContext);
        final BitmapDrawable drawable = new BitmapDrawable(mResources, blurredBitmap);
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

        long startDelay = OrientationUtil.isLandscape(mContext) ? AnimationDuration.LONG : AnimationDuration.FAST;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginView.showGoogleSignIn();
            }
        }, startDelay);
    }

    private boolean isSignInButtonSelected(Button button) {
        return button.getText().toString().equals(mResources.getString(R.string.button_text_sign_in));
    }
}
