package com.rsd.tryp.view;

import com.rsd.tryp.widget.MultiInputEditText;

/**
 * Created by wadereweti on 30/06/14.
 */
public interface InlineInputView {
    void setFormType(MultiInputEditText.FormType formType);
    void setLabel(String label);
    void setFlowIndicator(String flowIndicator);
    void setInput(String input);
    void setErrorMessage(String errorMessage);

    void clearInput();
    void clearErrorMessage();
}
