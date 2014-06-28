package com.rsd.tryp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;

import com.rsd.tryp.R;
import com.rsd.tryp.animation.AnimationDuration;
import com.rsd.tryp.service.LoginService;
import com.rsd.tryp.service.LoginServiceImpl;
import com.rsd.tryp.util.BlurUtil;
import com.rsd.tryp.view.LoginView;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class LoginPresenterImpl implements LoginPresenter {
    
    private static final int INITIALISATION_DELAY = 1500;

    private Context mContext;
    private LoginView mLoginView;
    private LoginService mService;

    public LoginPresenterImpl(Context context, LoginView mLoginView) {
        mContext = context;
        this.mLoginView = mLoginView;
        mService = new LoginServiceImpl(this);
        mLoginView.hideInputContainer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initialiseView();
            }
        }, AnimationDuration.STANDARD);
    }

    private void initialiseView() {
        blurBackground();
        mLoginView.showTitle();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginView.translateTitle();
                mLoginView.showInputContainer();
                mLoginView.setKeyboardShowingListener();
            }
        }, INITIALISATION_DELAY);
    }

    @Override
    public void blurBackground() {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_login);
        Bitmap blurredBitmap = BlurUtil.blurBitmap(bitmap, mContext);
        final BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), blurredBitmap);
        mLoginView.blurBackground(drawable);
    }

    @Override
    public void submitCredentials(String email, String password) {

    }

    @Override
    public void onSuccess() {

    }
}
