package com.rsd.tryp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.rsd.tryp.R;
import com.rsd.tryp.view.InlineInputView;
import com.rsd.tryp.widget.MultiInputEditText;
import com.rsd.tryp.widget.RobotoTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 30/06/14.
 */
public class InlineInputFragment extends Fragment implements InlineInputView {

    @InjectView(R.id.activity_login_container_input)
    RelativeLayout mInputContainer;

    @InjectView(R.id.activity_login_label)
    RobotoTextView mLabel;

    @InjectView(R.id.activity_login_edit_text)
    EditText mInlineInputEditText;

    @InjectView(R.id.activity_login_label_error_message)
    RobotoTextView mLabelErrorMessage;

    @InjectView(R.id.activity_login_label_flow_indicator)
    RobotoTextView mLabelFlowIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inline_input, container, true);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void setFormType(MultiInputEditText.FormType formType) {

    }

    @Override
    public void setLabel(String label) {

    }

    @Override
    public void setFlowIndicator(String flowIndicator) {

    }

    @Override
    public void setInput(String input) {

    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }
}
