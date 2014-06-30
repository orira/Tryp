package com.rsd.tryp.presenter;

import com.rsd.tryp.widget.InlineInputEditText;

/**
 * Created by wadereweti on 30/06/14.
 */
public interface InlineInputPresenter {
    void setLoginPresenter(LoginPresenter mPresenter);
    void setFormType(InlineInputPresenterImpl.FormType formType);
    void onPreviousStateSelected();

}
