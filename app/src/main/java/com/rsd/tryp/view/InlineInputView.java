package com.rsd.tryp.view;

import com.rsd.tryp.presenter.InlineInputPresenterImpl;
import com.rsd.tryp.widget.InlineInputEditText;

/**
 * Created by wadereweti on 30/06/14.
 */
public interface InlineInputView {
    void setFormType(InlineInputPresenterImpl.FormType formType);
    void setInitialLabel(String labelText);
    void setInitialFlowIndicator(String flowIndicatorText);
    void renderViews(final String input, String label, final String flowIndicatorText, boolean positiveFlow);
    void setEditTextPasswordState(boolean mEditTextPasswordState);
    void setErrorMessage(String errorMessage);
    void clearInput();
    void clearErrorMessage();
}
