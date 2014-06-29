package com.rsd.tryp.widget;

/**
 * Created by Raukawa on 6/28/2014.
 */
public interface MultiInputForm {
    void onValidInputEntered(String inputLabel, String flowPositionLabel, boolean previousState);
    void onInvalidInputEntered(String errorMessage);
    void submitCredentials(String email, String password);
}
