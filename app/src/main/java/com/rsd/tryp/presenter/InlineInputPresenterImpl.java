package com.rsd.tryp.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.rsd.tryp.R;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.InlineInputForm;

/**
 * Created by wadereweti on 30/06/14.
 */
public class InlineInputPresenterImpl implements InlineInputPresenter, InlineInputForm {

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

    private final Resources mResources;
    private final InlineInputView mInlineInputView;

    private LoginPresenter mLoginPresenter;
    private FormType mFormType;
    private FormState mFormState;
    private boolean mEditTextPasswordState;

    private String mEmail;
    private String mPassword;

    private String mInput = "";
    private String mLabelText;
    private String mFlowIndicatorText;
    private String mErrorMessage;

    public InlineInputPresenterImpl(Context context, InlineInputView inlineInputView) {
        mResources = context.getResources();
        mInlineInputView = inlineInputView;
        mFormState = FormState.EMAIL;
    }

    private void initialiseView() {
        String flowIndicatorText = null;

        switch (mFormType) {
            case REGISTRATION:
                flowIndicatorText = mResources.getString(R.string.hint_flow_1_registration);
                break;
            case SIGN_IN:
                flowIndicatorText = mResources.getString(R.string.hint_flow_1_sign_in);
                break;
        }

        mInlineInputView.setInitialLabel(mResources.getString(R.string.hint_email));
        mInlineInputView.setInitialFlowIndicator(flowIndicatorText);
    }

    // Inline Input Form Methods
    @Override
    public void inputProvided(String input) {
        if (isInputValid(input)) {
            mInput = "";
            boolean inputComplete = incrementFormState();

            if (inputComplete) {
                provideCredentials();
            } else {
                setTextValues(false);
                renderViews(true);
                mInlineInputView.setEditTextPasswordState(mEditTextPasswordState);
            }
        } else {
            mInlineInputView.setErrorMessage(mErrorMessage);
        }
    }

    @Override
    public void setLoginPresenter(LoginPresenter mPresenter) {
        mLoginPresenter = mPresenter;
    }

    // Presenter Methods
    @Override
    public void setFormType(FormType formType) {
        mFormType = formType;
        initialiseView();
    }

    @Override
    public void onPreviousStateSelected() {
        boolean defaultState = decrementFormState();

        if (defaultState) {
            mInlineInputView.clearErrorMessage();
            mInlineInputView.clearInput();

            mLoginPresenter.onInitialStateRequested();
        } else {
            setTextValues(true);
            renderViews(false);
            mInlineInputView.setEditTextPasswordState(mEditTextPasswordState);
        }
    }

    private boolean incrementFormState() {
        boolean inputComplete = false;

        switch (mFormState) {
            case EMAIL:
                mFormState = FormState.PASSWORD;
                mEditTextPasswordState = true;
                break;
            case PASSWORD:
                mFormState = isRegistrationFlow() ? FormState.PASSWORD_CONFIRMATION : FormState.PASSWORD;
                inputComplete = !isRegistrationFlow();
                break;
            case PASSWORD_CONFIRMATION:
                inputComplete = true;
                break;
        }

        return inputComplete;
    }

    private boolean decrementFormState() {
        boolean defaultState = false;

        switch (mFormState) {
            case PASSWORD_CONFIRMATION:
                mFormState = FormState.PASSWORD;
                mInput = mPassword;
                break;
            case PASSWORD:
                mFormState = FormState.EMAIL;
                mInput = mEmail;
                mEditTextPasswordState = false;
                break;
            case EMAIL:
                defaultState = true;
        }

        return defaultState;
    }

    private boolean isInputValid(String input) {
        boolean validInput = false;

        switch (mFormState) {
            case EMAIL:
                validInput = isValidEmail(input);
                break;
            case PASSWORD:
                validInput = isValidPassword(input);
                break;
            case PASSWORD_CONFIRMATION:
                validInput = isValidConfirmationPassword(input);
                break;
        }

        return validInput;
    }

    private boolean isValidEmail(String input) {
        boolean validEmail = false;

        if (!TextUtils.isEmpty(input) && android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            mEmail = input;
            validEmail = true;
        } else {
            mErrorMessage = mResources.getString(R.string.error_message_invalid_email);
        }

        return validEmail;
    }

    private boolean isValidPassword(String input) {
        boolean validPassword = false;

        if (!TextUtils.isEmpty(input) && input.length() > VALID_PASSWORD_LENGTH) {
            mPassword = input;
            validPassword = true;
        } else {
            mErrorMessage = mResources.getString(R.string.error_message_invalid_password_length);
        }

        return validPassword;
    }

    private boolean isValidConfirmationPassword(String input) {
        boolean validConfirmationPassword = false;

        if (input.equals(mPassword)) {
            validConfirmationPassword = true;
        } else {
            mErrorMessage = mResources.getString(R.string.error_message_invalid_password_confirm);
        }

        return validConfirmationPassword;
    }

    private void setTextValues(boolean previousFlow) {
        switch (mFormState) {
            case EMAIL:
                mLabelText = mResources.getString(R.string.hint_email);
                mFlowIndicatorText = isRegistrationFlow() ? mResources.getString(R.string.hint_flow_1_registration) : mResources.getString(R.string.hint_flow_1_sign_in);

                if (previousFlow) {
                    mInput = mEmail;
                }

                break;
            case PASSWORD:
                mLabelText = mResources.getString(R.string.hint_password);
                mFlowIndicatorText = isRegistrationFlow() ? mResources.getString(R.string.hint_flow_2_registration) : mResources.getString(R.string.hint_flow_2_sign_in);

                if (previousFlow) {
                    mInput = mPassword;
                }

                break;
            case PASSWORD_CONFIRMATION:
                mLabelText = mResources.getString(R.string.hint_password_confirm);
                mFlowIndicatorText = mResources.getString(R.string.hint_flow_3_registration);
                break;
        }
    }

    private void renderViews(boolean positiveFlow) {
        mInlineInputView.clearErrorMessage();
        mInlineInputView.clearInput();
        mInlineInputView.renderViews(mInput, mLabelText, mFlowIndicatorText, positiveFlow);
    }

    private void provideCredentials() {
        if (isRegistrationFlow()) {
            //Register
        } else {
            //Sign in
        }
    }

    private boolean isRegistrationFlow() {
        return mFormType == FormType.REGISTRATION;
    }
}
