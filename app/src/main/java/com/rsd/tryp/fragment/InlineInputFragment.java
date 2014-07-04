package com.rsd.tryp.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rsd.tryp.R;
import com.rsd.tryp.module.InlineInputModule;
import com.rsd.tryp.presenter.InlineInputPresenter;
import com.rsd.tryp.util.AnimationConstants;
import com.rsd.tryp.util.AnimationDuration;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.InlineInputEditText;
import com.rsd.tryp.widget.InlineInputForm;
import com.rsd.tryp.widget.RobotoTextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 30/06/14.
 */
public class InlineInputFragment extends AbstractFragment implements InlineInputView {

    private static final String DEFAULT_TEXT = "";

    @Inject
    InlineInputPresenter mPresenter;

    @InjectView(R.id.fragment_login_container_input)
    RelativeLayout mRootContainer;

    @InjectView(R.id.activity_login_label)
    RobotoTextView mLabel;

    @InjectView(R.id.activity_login_edit_text)
    InlineInputEditText mEditText;

    @InjectView(R.id.activity_login_label_error_message)
    RobotoTextView mErrorMessageLabel;

    @InjectView(R.id.activity_login_label_flow_indicator)
    RobotoTextView mFlowIndicatorLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We retain instance as on rotate our presenter can become stale
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inline_input, container, true);
        ButterKnife.inject(this, view);

        return view;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new InlineInputModule(this));
    }

    protected void init() {
        mEditText.setInlineInputForm((InlineInputForm) mPresenter);
    }

    @Override
    public void setInitialLabel(String labelText) {
        mLabel.setText(labelText);
    }

    @Override
    public void setInitialFlowIndicator(String flowIndicatorText) {
        mFlowIndicatorLabel.setText(flowIndicatorText);
    }

    @Override
    public void renderViews(final String input, final String label, final String flowIndicatorText, boolean positiveFlow) {
        int translationValue = positiveFlow ? mLabel.getHeight() : -mLabel.getHeight();
        final int yPosition = positiveFlow ? -mLabel.getHeight() : mLabel.getHeight();

        mLabel.animate().translationY(translationValue).alpha(AnimationConstants.GONE).withEndAction(new Runnable() {
            @Override
            public void run() {
                mLabel.setY(yPosition);
                mLabel.setText(label);
                mLabel.animate().setDuration(AnimationDuration.SHORT).alpha(AnimationConstants.DEFAULT_VALUE)
                        .translationY(AnimationConstants.DEFAULT_VALUE).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        mEditText.setText(input);
                        mFlowIndicatorLabel.setText(flowIndicatorText);
                    }
                });
            }
        });
    }

    @Override
    public void setEditTextInputMethod(boolean passwordInput) {
        if (passwordInput) {
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            mEditText.setTransformationMethod(SingleLineTransformationMethod.getInstance());
        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        mErrorMessageLabel.setText(errorMessage);
        mErrorMessageLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearInput() {
        mEditText.setText(DEFAULT_TEXT);
    }

    @Override
    public void clearErrorMessage() {
        mErrorMessageLabel.setText(DEFAULT_TEXT);
        mErrorMessageLabel.setVisibility(View.GONE);
    }

    @Override
    public void prepareForLoading() {
        mRootContainer.setPivotY(mRootContainer.getHeight() / 2);
        mRootContainer.animate().alpha(AnimationConstants.GONE);
        mRootContainer.animate().setDuration(2000).scaleY(AnimationConstants.GONE).withEndAction(new Runnable() {
            @Override
            public void run() {
                mPresenter.onPrepareForLoadingComplete();
            }
        });
    }

    @Override
    public void displayLoading() {
        mRootContainer.animate().alpha(AnimationConstants.DEFAULT_VALUE);
        mRootContainer.animate().setDuration(2000).scaleY(AnimationConstants.DEFAULT_VALUE).withEndAction(new Runnable() {
            @Override
            public void run() {
                mPresenter.onLoadingShowing();
            }
        });
    }

    public InlineInputPresenter getPresenter() {
        return mPresenter;
    }
}
