package com.rsd.tryp.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.rsd.tryp.R;
import com.rsd.tryp.animation.AnimationDuration;
import com.rsd.tryp.presenter.LoginPresenter;
import com.rsd.tryp.presenter.LoginPresenterImpl;
import com.rsd.tryp.view.LoginView;
import com.rsd.tryp.widget.LinearForm;
import com.rsd.tryp.widget.MultiInputEditText;
import com.rsd.tryp.widget.RobotoTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends Activity implements LoginView, LinearForm  {

    private static final int DEFAULT_VALUE = 1;
    private static final int GONE = 0;
    private static final long LAYOUT_TRANSLATION_DURATION = 250;
    private static final int LAYOUT_TRANSLATION = 150;
    private static final float MINIMUM_SCALE_VALUE = .96f;

    private LoginPresenter mPresenter;
    private int mLayoutTranslation;
    private int mInputContainerHeight;

    @InjectView(R.id.activity_login_container_root)
    RelativeLayout mRootContainer;

    @InjectView(R.id.activity_login_title)
    RobotoTextView mTitle;

    @InjectView(R.id.activity_login_container_input)
    RelativeLayout mInputContainer;

    @InjectView(R.id.activity_login_label)
    RobotoTextView mLabel;

    @InjectView(R.id.activity_login_edit_text)
    MultiInputEditText mLinearEditText;

    @InjectView(R.id.activity_login_label_error_message)
    RobotoTextView mLabelErrorMessage;

    @InjectView(R.id.activity_login_label_flow_indicator)
    RobotoTextView mLabelFlowIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mLinearEditText.setLinearForm(this);

        // Test for orientation as we only want to translate our views in portrait
        // otherwise the views clip in landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutTranslation = GONE;
        } else {
            mLayoutTranslation = -LAYOUT_TRANSLATION;
        }

        // Delegate to presenter for view initialisation only once the view has been rendered
        mRootContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPresenter = new LoginPresenterImpl(getApplicationContext(), LoginActivity.this);
                mRootContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // We cache this value as the height can change depending on error messages shown
                // so we keep the original value so the title doesn't translate unnecessarily
                mInputContainerHeight = mInputContainer.getHeight();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mLabelErrorMessage.getText().toString().length() > 0) {
            clearErrorMessage();
        } else {
            mLinearEditText.setPreviousState();
        }
    }

    @Override
    public void hideInputContainer() {
        mInputContainer.setY(mRootContainer.getHeight());
    }

    @Override
    public void blurBackground(BitmapDrawable drawable) {
        mRootContainer.setBackground(drawable);
    }

    @Override
    public void showTitle() {
        mTitle.animate().scaleX(DEFAULT_VALUE).scaleY(DEFAULT_VALUE);
    }

    @Override
    public void translateTitle() {
        mTitle.animate().setDuration(AnimationDuration.LONG).translationY(-mInputContainer.getHeight());
    }

    @Override
    public void showInputContainer() {
        mInputContainer.animate().setDuration(AnimationDuration.LONG).translationY(DEFAULT_VALUE);
    }

    /**
     * We need to animate the title and input container above the keyboard when showing, and return
     * the elements to their default co-ordinates when the keyboard is dismissed.  Of special import
     * is that they are only translated in portrait.
     */
    @Override
    public void setKeyboardShowingListener() {
        mRootContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isKeyboardShowing()) {
                    mTitle.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(-mInputContainerHeight + mLayoutTranslation);
                    mInputContainer.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(mLayoutTranslation);
                } else {
                    mTitle.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(-mInputContainerHeight);
                    mInputContainer.animate().setDuration(LAYOUT_TRANSLATION_DURATION).translationY(DEFAULT_VALUE);
                }
            }

            private boolean isKeyboardShowing() {
                Rect r = new Rect();
                mRootContainer.getWindowVisibleDisplayFrame(r);
                int heightDiff = mRootContainer.getRootView().getHeight() - (r.bottom - r.top);

                return heightDiff > 100;
            }
        });
    }

    public void onValidInputEntered(final String inputLabel, final String flowLabelIndicator) {
        mLabelErrorMessage.animate().alpha(GONE).withEndAction(new Runnable() {
            @Override
            public void run() {
                clearErrorMessage();
            }
        });

        mLabel.animate().translationY(-mLabel.getHeight()).alpha(GONE).withEndAction(new Runnable() {
            @Override
            public void run() {
                mLabel.setY(mLabel.getHeight());
                mLabel.setText(inputLabel);
                mLabel.animate().setDuration(AnimationDuration.SHORT).alpha(DEFAULT_VALUE);
                mLabel.animate().translationY(DEFAULT_VALUE);
                mLabelFlowIndicator.setText(flowLabelIndicator);
            }
        });
    }

    @Override
    public void onInvalidInputEntered(String errorMessage) {
        if (mLabelErrorMessage.getText().toString().equals(errorMessage)) {
            mLabelErrorMessage.animate().setDuration(AnimationDuration.SHORT).scaleX(MINIMUM_SCALE_VALUE).scaleY(MINIMUM_SCALE_VALUE).setInterpolator(new OvershootInterpolator(6f)).withEndAction(new Runnable() {
                @Override
                public void run() {
                    mLabelErrorMessage.animate().setDuration(AnimationDuration.SHORT).scaleX(DEFAULT_VALUE).scaleY(DEFAULT_VALUE);
                }
            });
        } else {
            mLabelErrorMessage.setVisibility(View.VISIBLE);
            mLabelErrorMessage.setScaleX(DEFAULT_VALUE);
            mLabelErrorMessage.setText(errorMessage);
            mLabelErrorMessage.animate().setDuration(AnimationDuration.LONG).scaleX(DEFAULT_VALUE);
        }
    }

    @Override
    public void submitCredentials(String email, String password) {
        mPresenter.submitCredentials(email, password);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void clearErrorMessage() {
        mLabelErrorMessage.setVisibility(View.GONE);
        mLabelErrorMessage.setText("");
        mLabelErrorMessage.setAlpha(DEFAULT_VALUE);
    }
}