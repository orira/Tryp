package com.rsd.tryp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Raukawa on 6/28/2014.
 */
public class InlineInputEditText extends EditText implements View.OnTouchListener {

    private Resources mResources;
    private Drawable mActionDrawable;
    private InlineInputForm mInlineInputForm;

    public InlineInputEditText(Context context) {
        super(context);
        init();
    }

    public InlineInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InlineInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mResources = getResources();
        mActionDrawable = mResources.getDrawable(android.R.drawable.ic_input_add);
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mActionDrawable, null);
        this.setOnTouchListener(this);
    }

    public void setInlineInputForm(InlineInputForm inlineInputForm) {
        mInlineInputForm = inlineInputForm;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (withinDrawableBounds(motionEvent.getX())) {
                    mInlineInputForm.inputProvided(getText().toString());

                    return true;
                }
        }

        return false;
    }

    private boolean withinDrawableBounds(float x) {
        float parentWidth = this.getWidth();
        float drawableWidth = mActionDrawable.getIntrinsicWidth();
        float horizontalThreshold = parentWidth - drawableWidth;

        return x >= horizontalThreshold;
    }
}
