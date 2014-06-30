package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsd.tryp.R;
import com.rsd.tryp.animation.AnimationDuration;
import com.rsd.tryp.presenter.InlineInputPresenter;
import com.rsd.tryp.presenter.InlineInputPresenterImpl;
import com.rsd.tryp.util.AnimationConstants;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.InlineInputEditText;
import com.rsd.tryp.widget.InlineInputForm;
import com.rsd.tryp.widget.RobotoTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 30/06/14.
 */
public class InlineInputFragment extends Fragment implements InlineInputView {

    private static final String DEFAULT_TEXT = "";

    private InlineInputPresenter mPresenter;

    @InjectView(R.id.activity_login_label)
    RobotoTextView mLabel;

    @InjectView(R.id.activity_login_edit_text)
    InlineInputEditText mInlineInputEditText;

    @InjectView(R.id.activity_login_label_error_message)
    RobotoTextView mLabelErrorMessage;

    @InjectView(R.id.activity_login_label_flow_indicator)
    RobotoTextView mLabelFlowIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inline_input, container, true);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter = new InlineInputPresenterImpl(getActivity(), this);
        mInlineInputEditText.setInlineInputForm((InlineInputForm) mPresenter);
    }

    @Override
    public void setFormType(InlineInputPresenterImpl.FormType formType) {
        mPresenter.setFormType(formType);
    }

    @Override
    public void setInitialLabel(String labelText) {
        mLabel.setText(labelText);
    }

    @Override
    public void setInitialFlowIndicator(String flowIndicatorText) {
        mLabelFlowIndicator.setText(flowIndicatorText);
    }

    @Override
    public void renderViews(final String input, final String label, final String flowIndicatorText, boolean positiveFlow) {
        int translationValue = positiveFlow ? mLabel.getHeight() : -mLabel.getHeight();
        final int yPosition = positiveFlow ? -mLabel.getHeight() : mLabel.getHeight();

        mLabel.animate().translationY(translationValue).alpha(AnimationConstants.GONE).withEndAction(new Runnable() {
            @Override
            public void run() {
                mLabel.setY(yPosition);
                mLabel.animate().setDuration(AnimationDuration.SHORT).alpha(AnimationConstants.DEFAULT_VALUE);
                mLabel.animate().translationY(AnimationConstants.DEFAULT_VALUE);
                mLabel.setText(label);
                mInlineInputEditText.setText(input);
                mLabelFlowIndicator.setText(flowIndicatorText);
            }
        });
    }

    @Override
    public void setEditTextPasswordState(boolean mEditTextPasswordState) {
        if (mEditTextPasswordState) {
            mInlineInputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mInlineInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mInlineInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            mInlineInputEditText.setTransformationMethod(SingleLineTransformationMethod.getInstance());
        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        mLabelErrorMessage.setText(errorMessage);
        mLabelErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearInput() {
        mInlineInputEditText.setText(DEFAULT_TEXT);
    }

    @Override
    public void clearErrorMessage() {
        mLabelErrorMessage.setText(DEFAULT_TEXT);
        mLabelErrorMessage.setVisibility(View.GONE);
    }

    public InlineInputPresenter getPresenter() {
        return mPresenter;
    }
}
