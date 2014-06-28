package com.rsd.tryp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rsd.tryp.R;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class MultiInputEditText extends EditText implements View.OnTouchListener {

    private enum FormState {
        EMAIL,
        PASSWORD,
        PASSWORD_CONFIRMATION;
    }

    private static final int VALID_PASSWORD_LENGTH = 6;

    private Resources mResources;
    private Drawable mActionDrawable;
    private LinearForm mLinearForm;
    private String mEmail;
    private String mPassword;
    private FormState mFormState;

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

    public void setLinearForm(LinearForm linearForm) {
        mLinearForm = linearForm;
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
            mFormState = FormState.PASSWORD;
            mEmail = getText().toString();
            mLinearForm.onValidInputEntered(mResources.getString(R.string.label_password), mResources.getString(R.string.hint_flow_2));
        } else {
            mEmail = null;
            mLinearForm.onInvalidInputEntered(mResources.getString(R.string.error_message_invalid_email));
        }

        clearText();
    }

    private boolean isValidEmail() {
        return !TextUtils.isEmpty(getText().toString()) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(getText().toString()).matches();
    }

    private void validatePassword() {
        if (isValidPassword()) {
            mFormState = FormState.PASSWORD_CONFIRMATION;
            mPassword = getText().toString();
            mLinearForm.onValidInputEntered(mResources.getString(R.string.label_password_confirm), mResources.getString(R.string.hint_flow_3));
        } else {
            mPassword = null;
            mLinearForm.onInvalidInputEntered(getInvalidPasswordMessage());
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
            mLinearForm.submitCredentials(mEmail, mPassword);
        } else {
            mLinearForm.onInvalidInputEntered(mResources.getString(R.string.error_message_invalid_password_confirm));
        }

        clearText();
    }

    private boolean isValidConfirmPassword() {
        return getText().toString().equals(mPassword);
    }

    private void clearText() {
        setText("");
    }
}
