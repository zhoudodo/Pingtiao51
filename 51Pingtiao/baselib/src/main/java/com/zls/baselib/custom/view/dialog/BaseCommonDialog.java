package com.zls.baselib.custom.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * 通用dialog by joeYu on 17/5/10.
 */

public class BaseCommonDialog extends Dialog {
    public BaseCommonDialog(Context context) {
        super(context);
    }

    public BaseCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }

}
