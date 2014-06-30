package com.rsd.tryp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rsd.tryp.R;
import com.rsd.tryp.animation.AnimationDuration;
import com.rsd.tryp.util.OrientationUtil;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class MultiInputEditText extends EditText implements View.OnTouchListener {

    public enum FormType {
        REGISTRATION,
        SIGN_IN;
    }

    public enum FormState {
        EMAIL,
        PASSWORD,
        PASSWORD_CONFIRMATION;
    }

    private static final int VALID_PASSWORD_LENGTH = 6;

    private Resources mResources;
    private Drawable mActionDrawable;
    private MultiInputForm mMultiInputForm;
    private String mEmail;
    private String mPassword;
    private FormState mFormState;
    private FormType mFormType;

    public MultiInputEditText(Context context) {
        super(context);
        init(context);
    }

    public MultiInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mResources = getResources();
        mFormState = FormState.EMAIL;
        mActionDrawable = mResources.getDrawable(android.R.drawable.ic_input_add);
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mActionDrawable, null);
        this.setOnTouchListener(this);
    }

    public void setMultiInputForm(MultiInputForm multiInputForm) {
        mMultiInputForm = multiInputForm;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (withinDrawableBounds(motionEvent.getX())) {
                    validateInput();

                    return true;
                }
        }

        return false;
    }

    private boolean withinDrawableBounds(float x) {
        float parentWidth = this.getWidth();
        float drawableWidth = mActionDrawable.getIntrinsicWidth();
        float horizontalThreshold = parentWidth - drawableWidth;

        return x >= horizontalThreshold;
    }

    private void validateInput() {
        switch (mFormState) {
            case EMAIL:
                validateEmail();
                break;
            case PASSWORD:
                validatePassword();
                break;
            case PASSWORD_CONFIRMATION:
                validateConfirmPassword();
                break;
        }
    }

    private void validateEmail() {
        if (isValidEmail()) {
            setPasswordState();
            mEmail = getText().toString();
            mMultiInputForm.onValidInputEntered(getLabelText(), getFlowIndicatorText());
        } else {
            mEmail = null;
            mMultiInputForm.onInvalidInputEntered(mResources.getString(R.string.error_message_invalid_email));
        }

        clearText();
    }

    private boolean isValidEmail() {
        return !TextUtils.isEmpty(getText().toString()) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(getText().toString()).matches();
    }

    private void validatePassword() {
        if (isValidPassword()) {
            if (isRegistrationFlow()) {
                mFormState = FormState.PASSWORD_CONFIRMATION;
                mPassword = getText().toString();
                mMultiInputForm.onValidInputEntered(getLabelText(), getFlowIndicatorText());
            } else {
                // Sign in flow submit credentials
                mMultiInputForm.submitCredentials(mEmail, mPassword);
            }
        } else {
            mPassword = null;
            mMultiInputForm.onInvalidInputEntered(getInvalidPasswordMessage());
        }

        clearText();
    }

    private boolean isValidPassword() {
        String input = getText().toString();

        return !TextUtils.isEmpty(input) && input.length() > VALID_PASSWORD_LENGTH;
    }

    private String getInvalidPasswordMessage() {
        String input = getText().toString();
        String errorMessage = "";

        if (TextUtils.isEmpty(input)) {
            errorMessage = mResources.getString(R.string.error_message_invalid_password_empty);
        } else if (input.length() < VALID_PASSWORD_LENGTH) {
            errorMessage = mResources.getString(R.string.error_message_invalid_password_length);
        }

        return errorMessage;
    }

    private void validateConfirmPassword() {
        if (isValidConfirmPassword()) {
            mMultiInputForm.registerCredentials(mEmail, mPassword);
        } else {
            mMultiInputForm.onInvalidInputEntered(mResources.getString(R.string.error_message_invalid_password_confirm));
        }

        clearText();
    }

    private boolean isValidConfirmPassword() {
        return getText().toString().equals(mPassword);
    }

    public void setPreviousState() {
        clearText();

        String input = null;

        switch (mFormState) {
            case PASSWORD_CONFIRMATION:
                setPasswordState();
                input = mPassword;
                break;
            case PASSWORD:
                setEmailState();
                input = mEmail;
                break;
            case EMAIL:
                break;
        }

        mMultiInputForm.setPreviousFormState(getLabelText(), getFlowIndicatorText(), input);

        // Show previous input once labels have animated to correct state
        final String finalInput = input;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setText(finalInput);
            }
        }, AnimationDuration.STANDARD);
    }

    private void setEmailState() {
        mFormState = FormState.EMAIL;
        setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        setTransformationMethod(SingleLineTransformationMethod.getInstance());
    }

    private void setPasswordState() {
        mFormState = FormState.PASSWORD;
        setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void setFormType(FormType formType) {
        mFormType = formType;
        setInitialState();
    }

    private void setInitialState() {
        mMultiInputForm.setInitialFormState(getLabelText(), getFlowIndicatorText());
    }

    private boolean isRegistrationFlow() {
        return mFormType == FormType.REGISTRATION;
    }

    private String getLabelText() {
        String labelText = null;
        switch (mFormState) {
            case PASSWORD_CONFIRMATION:
                labelText = mResources.getString(R.string.hint_password_confirm);
                break;
            case PASSWORD:
                labelText = mResources.getString(R.string.hint_password);
                break;
            case EMAIL:
                labelText = mResources.getString(R.string.hint_email);
                break;
        }

        return labelText;
    }

    private String getFlowIndicatorText() {
        String flowIndicatorText = null;
        switch (mFormState) {
            case PASSWORD_CONFIRMATION:
                flowIndicatorText = mResources.getString(R.string.hint_flow_3_registration);
                break;
            case PASSWORD:
                flowIndicatorText = isRegistrationFlow() ? mResources.getString(R.string.hint_flow_2_registration) : getResources().getString(R.string.hint_flow_2_sign_in);
                break;
            case EMAIL:
                flowIndicatorText = isRegistrationFlow() ? mResources.getString(R.string.hint_flow_1_registration) : getResources().getString(R.string.hint_flow_1_sign_in);
                break;
        }

        return flowIndicatorText;
    }

    private void clearText() {
        setText("");
    }
}
