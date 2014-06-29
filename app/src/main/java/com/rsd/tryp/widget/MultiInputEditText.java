package com.rsd.tryp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rsd.tryp.R;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class MultiInputEditText extends EditText implements View.OnTouchListener {

    public enum FormType {
        REGISTRATION,
        SIGN_IN;
    }

    private enum FormState {
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
                    validate();
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

    private void validate() {
        if (isRegistrationFlow()) {
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
        } else {
            switch (mFormState) {
                case EMAIL:
                    validateEmail();
                    break;
                case PASSWORD:
                    validatePassword();
                    break;
            }
        }
    }

    private void validateEmail() {
        if (isValidEmail()) {
            setPasswordState();
            mEmail = getText().toString();
            String flowIndicatorText = isRegistrationFlow() ? mResources.getString(R.string.hint_flow_2_registration) : mResources.getString(R.string.hint_flow_2_sign_in);
            mMultiInputForm.onValidInputEntered(mResources.getString(R.string.hint_password), flowIndicatorText, false);
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
                mMultiInputForm.onValidInputEntered(mResources.getString(R.string.hint_password_confirm), mResources.getString(R.string.hint_flow_3_registration), false);
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
            mMultiInputForm.submitCredentials(mEmail, mPassword);
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
        switch (mFormState) {
            case PASSWORD_CONFIRMATION:
                mMultiInputForm.onValidInputEntered(mResources.getString(R.string.hint_password), mResources.getString(R.string.hint_flow_2_registration), true);
                setPasswordState();
                break;
            case PASSWORD:
                mMultiInputForm.onValidInputEntered(mResources.getString(R.string.hint_email), getInitialFlowIndicatorText(), true);
                setEmailState();
                mPassword = null;
                break;
            case EMAIL:
                mMultiInputForm.onValidInputEntered(null, null, true);
                mEmail = null;
                break;
        }
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
        mMultiInputForm.onValidInputEntered(getResources().getString(R.string.hint_email), getInitialFlowIndicatorText(), false);
    }

    private boolean isRegistrationFlow() {
        return mFormType == FormType.REGISTRATION;
    }

    private String getInitialFlowIndicatorText() {
        return isRegistrationFlow() ? getResources().getString(R.string.hint_flow_1_registration) : getResources().getString(R.string.hint_flow_1_sign_in);
    }

    private void clearText() {
        setText("");
    }
}
