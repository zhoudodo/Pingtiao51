package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.root.BasePopu;

public class CloseBankPopu extends BasePopu {

    public interface CloseBankPopuInterface {
        public void closeBankRelative();
    }

    private CloseBankPopuInterface mCloseBankPopuInterface;

    public CloseBankPopu(FragmentActivity act, CloseBankPopuInterface closeBankPopuInterface) {
        super(act, R.layout.popu_addbank_layout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mCloseBankPopuInterface = closeBankPopuInterface;
    }

    @Override
    protected void init() {
        findViewsId(R.id.card_cancel_relative, true);
        findViewsId(R.id.card_cancel, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_cancel:
                dismiss();
                break;

            case R.id.card_cancel_relative:
                if (mCloseBankPopuInterface != null) {
                    mCloseBankPopuInterface.closeBankRelative();
                }
                dismiss();
                break;
        }

    }

    //自定义位置
    public void showViewBottom(View mView) {
        if (mActivity.isFinishing())
            return;
        setAnimationStyle(com.zls.baselib.R.style.Popup_Animation_UpDown);
        beforeshow();

        int[] location = new int[2];
        mView.getLocationOnScreen(location);
        showAtLocation(mView,
                Gravity.NO_GRAVITY,
                location[0] - mView.getMeasuredWidth(),
                location[1] + mView.getMeasuredHeight());


        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f; //屏幕变暗 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
        update();
    }
}
