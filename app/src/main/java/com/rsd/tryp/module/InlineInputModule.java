package com.rsd.tryp.module;

import com.rsd.tryp.activity.LoginActivity;
import com.rsd.tryp.fragment.AbstractFragment;
import com.rsd.tryp.fragment.InlineInputFragment;
import com.rsd.tryp.presenter.InlineInputPresenter;
import com.rsd.tryp.presenter.InlineInputPresenterImpl;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.InlineInputEditText;
import com.rsd.tryp.widget.InlineInputForm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 3/07/14.
 */

@Module(
    injects = {
        InlineInputFragment.class,
    }
)
public class InlineInputModule {
    private InlineInputView mView;

    public InlineInputModule(InlineInputView view) {
        mView = view;
    }

    @Provides
    InlineInputView provideInputView() {
        return mView;
    }

    @Provides @Singleton
    InlineInputPresenter provideInlineInputPresenter(InlineInputView view) {
        return new InlineInputPresenterImpl(view);
    }
}
