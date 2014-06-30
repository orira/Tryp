package com.rsd.tryp.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.rsd.tryp.R;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.MultiInputEditText;

/**
 * Created by wadereweti on 30/06/14.
 */
public class InlineInputPresenterImpl implements InlineInputPresenter {

    private static final int VALID_PASSWORD_LENGTH = 6;

    private final Resources mResources;
    private final InlineInputView mInlineInputView;
    private MultiInputEditText.FormType mFormType;
    private MultiInputEditText.FormState mFormState;

    private String mEmail;
    private String mPassword;

    public InlineInputPresenterImpl(Context context, InlineInputView inlineInputView) {
        mResources = context.getResources();
        mInlineInputView = inlineInputView;
        mFormState = MultiInputEditText.FormState.EMAIL;

        initialiseView();
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

        mInlineInputView.setLabel(mResources.getString(R.string.hint_email));
        mInlineInputView.setFlowIndicator(flowIndicatorText);
    }

    @Override
    public void onInputProvided(String input) {
        if (isInputValid(input)) {
            mInlineInputView.clearErrorMessage();
            mInlineInputView.clearInput();
            mInlineInputView.setLabel(calculateLabelText());
            mInlineInputView.setFlowIndicator(calculateFlowIndicatorText());
        } else {
            mInlineInputView.setErrorMessage(calculateErrorMessage(input));
        }
    }

    private boolean isInputValid(String input) {

        boolean validInput = false;

        switch (mFormState) {
            case EMAIL:
                validInput = isValidEmail(input);
                break;
            case PASSWORD:
                validInput = isValidatePassword(input);
                break;
            case PASSWORD_CONFIRMATION:
                validInput = isValidConfirmationPassword(input);
                break;
        }

        return false;
    }

    private boolean isValidEmail(String input) {
        return !TextUtils.isEmpty(input) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();

    }

    private boolean isValidatePassword(String input) {
        return !TextUtils.isEmpty(input) && input.length() > VALID_PASSWORD_LENGTH;
    }

    private String calculateLabelText() {
        return null;
    }

    private String calculateFlowIndicatorText() {
        return null;
    }

    private String calculateErrorMessage(String input) {
        return null;
    }
}
