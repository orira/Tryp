package com.rsd.tryp.presenter;

/**
 * Created by wadereweti on 30/06/14.
 */
public interface InlineInputPresenter {
    void setLoginPresenter(LoginPresenter mPresenter);
    void setFormType(InlineInputPresenterImpl.FormType formType);
    void onPreviousStateSelected();

}
