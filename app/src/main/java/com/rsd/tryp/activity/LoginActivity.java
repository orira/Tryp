package com.rsd.tryp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.SignInButton;
import com.google.common.eventbus.Subscribe;
import com.rsd.tryp.R;
import com.rsd.tryp.TrypApplication;
import com.rsd.tryp.fragment.InlineInputFragment;
import com.rsd.tryp.module.annotation.LoginModule;
import com.rsd.tryp.presenter.InlineInputPresenterImpl;
import com.rsd.tryp.presenter.LoginPresenter;
import com.rsd.tryp.presenter.LoginPresenterImpl;
import com.rsd.tryp.util.AnimationConstants;
import com.rsd.tryp.util.AnimationDuration;
import com.rsd.tryp.util.KeyboardUtil;
import com.rsd.tryp.util.OrientationUtil;
import com.rsd.tryp.view.LoginView;
import com.rsd.tryp.widget.RobotoTextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends AbstractActivity implements LoginView {

    private static final String TAG = "LoginActivity";
    private static final long LAYOUT_TRANSLATION_DURATION = 250;
    private static final int LAYOUT_TRANSLATION = 150;

    private int mLayoutTranslation;
    private int mInlineInputFragmentHeight;

    @Inject
    LoginPresenter mPresenter;

    @InjectView(R.id.activity_login_container_root)
    RelativeLayout mRootContainer;

    @InjectView(R.id.activity_login_title)
    RobotoTextView mTitle;

    @InjectView(R.id.activity_login_container_register)
    LinearLayout mRegisterContainer;

    @InjectView(R.id.sign_in_button)
    SignInButton mSignInButton;

    InlineInputFragment mInlineInputFragment;

    /**
     * We need to animate the title and input container above the keyboard when showing, and return
     * the elements to their default co-ordinates, when the keyboard is dismissed.  Of special import
     * is that they are only translated in portrait.
     */
    ViewTreeObserver.OnGlobalLayoutListener mKeyboardShowingListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (KeyboardUtil.isKeyboardShowing(mRootContainer)) {
                mTitle.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(-mInlineInputFragmentHeight + mLayoutTranslation);
                mInlineInputFragment.getView().animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(mLayoutTranslation);
            } else {
                mTitle.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(-mInlineInputFragmentHeight);
                mInlineInputFragment.getView().animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(AnimationConstants.DEFAULT_VALUE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        mInlineInputFragment = (InlineInputFragment) getFragmentManager().findFragmentById(R.id.activity_login_input_fragment);

        // Test for orientation as we only want to translate our views in portrait
        // otherwise the views clip in landscape
        if (OrientationUtil.isLandscape(this)) {
            mLayoutTranslation = AnimationConstants.GONE;
        } else {
            mLayoutTranslation = -LAYOUT_TRANSLATION;
        }

        // Delegate to presenter for view initialisation only once the view has been rendered
        mRootContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //mPresenter = new LoginPresenterImpl(LoginActivity.this);
                mPresenter.init();
                mInlineInputFragment.getPresenter().setLoginPresenter(mPresenter);
                mRootContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // We cache this value as the height can change depending on error messages shown
                // so we keep the original value so the title doesn't translate unnecessarily
                mInlineInputFragmentHeight = mInlineInputFragment.getView().getHeight();
            }
        });
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new LoginModule(this));
    }

    @Override
    public void onBackPressed() {
        if (mInlineInputFragment.getView().getY() == mRootContainer.getHeight()) {
            super.onBackPressed();
        }

        mInlineInputFragment.getPresenter().onPreviousStateSelected();
    }

    // LoginView Methods

    @Override
    public void blurBackground(BitmapDrawable drawable) {
        mRootContainer.setBackground(drawable);
    }

    @Override
    public void showTitle() {
        mTitle.animate().scaleX(AnimationConstants.DEFAULT_VALUE).scaleY(AnimationConstants.DEFAULT_VALUE);
    }

    @Override
    public void translateTitle() {
        mTitle.animate().setDuration(AnimationDuration.STANDARD).translationY(-mInlineInputFragment.getView().getHeight());
    }

    @Override
    public void showGoogleSignIn() {
        mSignInButton.animate().alpha(AnimationConstants.DEFAULT_VALUE);
    }

    @Override
    public void hideGoogleSignIn() {
        mSignInButton.animate().alpha(AnimationConstants.GONE);
    }

    @Override
    public void setRegisterContainerOffscreen() {
        mRegisterContainer.setY(mRootContainer.getHeight());
    }

    @Override
    public void showRegisterContainer() {
        mRegisterContainer.animate().setDuration(AnimationDuration.STANDARD).translationY(AnimationConstants.DEFAULT_VALUE);
    }

    @Override
    public void translateRegisterContainerOut(boolean translateLeft) {
        int translation = translateLeft ? -mRootContainer.getWidth() : mRootContainer.getWidth();
        mRegisterContainer.animate().setDuration(AnimationDuration.SHORT).translationX(translation);
    }

    @Override
    public void translateRegisterContainerIn() {
        long startDelay = OrientationUtil.isLandscape(this) ? AnimationDuration.STANDARD : 0;
        mRegisterContainer.animate().setDuration(AnimationDuration.STANDARD).setStartDelay(startDelay).translationX(AnimationConstants.DEFAULT_VALUE);
    }

    @Override
    public void setInputContainerOffscreen() {
        mInlineInputFragment.getView().setY(mRootContainer.getHeight());
    }

    @Override
    public void translateInputContainerIn() {
        long startDelay = OrientationUtil.isLandscape(this) ? AnimationDuration.STANDARD : 0;
        mInlineInputFragment.getView().animate().setDuration(AnimationDuration.STANDARD).setStartDelay(startDelay).translationY(AnimationConstants.DEFAULT_VALUE);
    }

    @Override
    public void translateInputContainerOut() {
        mInlineInputFragment.getView().animate().setDuration(AnimationDuration.STANDARD).translationY(mRootContainer.getHeight()).withEndAction(new Runnable() {
            @Override
            public void run() {
            // We set the y position to the bottom of the screen so when back is selected we can close the app
            setInputContainerOffscreen();
            }
        });
    }

    @Override
    public void setKeyboardShowingListener() {
        mRootContainer.getViewTreeObserver().addOnGlobalLayoutListener(mKeyboardShowingListener);
    }

    @Override
    public void removeKeyboardShowingListener() {
        mRootContainer.getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardShowingListener);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @OnClick({R.id.btn_register, R.id.btn_sign_in})
    public void onRegisterOrSignInButtonClick(Button button) {
        InlineInputPresenterImpl.FormType formType = button.getText().toString().equals(getString(R.string.button_text_register)) ?
                InlineInputPresenterImpl.FormType.REGISTRATION : InlineInputPresenterImpl.FormType.SIGN_IN;
        mInlineInputFragment.getPresenter().setFormType(formType);
        mPresenter.onInitialiseButtonSelected(button);
    }

    @Subscribe
    public void onAuthenticateEvent(boolean success) {

    }

    @Subscribe void onRegistrationEvent(boolean success) {

    }
}