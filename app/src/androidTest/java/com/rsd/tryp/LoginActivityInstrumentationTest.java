package com.rsd.tryp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rsd.tryp.activity.LoginActivity;
import com.rsd.tryp.widget.RobotoButton;
import com.rsd.tryp.widget.RobotoTextView;

import static android.test.ViewAsserts.assertOffScreenBelow;
import static android.test.ViewAsserts.assertOnScreen;

/**
 * Created by wadereweti on 30/06/14.
 */
public class LoginActivityInstrumentationTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mLoginActivity;

    private RobotoTextView mTitle;
    private RobotoButton mSignInButton;
    private RobotoButton mRegistrationButton;

    private LinearLayout mRegistrationContainer;
    private RelativeLayout mInputContainer;

    public LoginActivityInstrumentationTest() {
        super("com.rsd.tryp", LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        mLoginActivity = getActivity();

        mTitle = (RobotoTextView) mLoginActivity.findViewById(R.id.activity_login_title);
        mSignInButton = (RobotoButton) mLoginActivity.findViewById(R.id.btn_sign_in);
        mRegistrationButton = (RobotoButton) mLoginActivity.findViewById(R.id.btn_register);

        mRegistrationContainer = (LinearLayout) mLoginActivity.findViewById(R.id.activity_login_container_register);
        mInputContainer = (RelativeLayout) mLoginActivity.findViewById(R.id.activity_login_container_input);
    }

    public void testInitialView() {
        View view = mLoginActivity.getWindow().getDecorView();

        assertOnScreen(view, mTitle);

        assertOnScreen(view, mRegistrationContainer);
        assertOnScreen(view, mSignInButton);
        assertOnScreen(view, mRegistrationButton);

        assertOffScreenBelow(mTitle, mRegistrationContainer);
    }
}
