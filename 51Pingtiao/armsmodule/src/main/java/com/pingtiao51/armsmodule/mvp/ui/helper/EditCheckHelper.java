package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;


public class EditCheckHelper {
    public static final int USERNAME_LENGTH_MAX = 11;
    public static final int PASSWORD_LENGTH_MIN = 6;
    public static final int PASSWORD_LENGTH_MAX = 16;

    public static boolean checkInputPhoneToast(EditText mEditView) {
        String mUserName = mEditView.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)) {
            ArmsUtils.snackbarText(Utils.getApp().getResources().getString(R.string.string_check_phone));
            return false;
        }

        if (mUserName.length() != USERNAME_LENGTH_MAX || !mUserName.matches("[1][3578]\\d{9}|14[57]\\d{8}|166\\d{8}|19[98]\\d{8}")) {
            ArmsUtils.snackbarText(Utils.getApp().getResources().getString(R.string.string_check_phone_right));
            return false;
        }
        return true;
    }
    public static boolean checkInputPasswordToast(EditText mEditView) {
        String mPassword = mEditView.getText().toString().trim();
        if (TextUtils.isEmpty(mPassword)) {
            ArmsUtils.snackbarText(Utils.getApp().getResources().getString(R.string.string_check_password));
            return false;
        }

        if (mPassword.length() < PASSWORD_LENGTH_MIN
                || mPassword.length() > PASSWORD_LENGTH_MAX) {
            ArmsUtils.snackbarText(Utils.getApp().getResources().getString(R.string.string_check_password_lendth));
            return false;
        }
        return true;
    }

    public static boolean checkInputAuthCodeToast(EditText mEditView) {
        String authCode = mEditView.getText().toString().trim();
        if (TextUtils.isEmpty(authCode)) {
            ArmsUtils.snackbarText(Utils.getApp().getResources().getString(R.string.string_check_code));
            return false;
        }
        return true;
    }


}
