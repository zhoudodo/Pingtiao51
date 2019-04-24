package com.zls.baselib.custom.view.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.zls.baselib.R;
import com.zls.baselib.custom.view.helputils.ViewBindHelper;


/**
 * Created by joeYu on 17/3/22.
 */

public abstract class FrameDialog extends BaseCommonDialog implements View.OnClickListener {

    protected Activity mActivity;

    public FrameDialog(Activity context) {
        super(context, R.style.custom_dialog);
        mActivity = context;
        setContentView(getViewIds());
        initLocation();
        initView();
    }

    public FrameDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
        setContentView(getViewIds());
        initLocation();
        mActivity = context;
        initView();
    }

// 生命周期靠后
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initView();
//    }

    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.BOTTOM);
    }

    //获取资源ids
    protected abstract int getViewIds();


    protected void initView() {
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViews(int id) {
        return findViewsId(id, false);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewsId(int id, boolean clickAble) {
        T views = ViewBindHelper.findViews(getWindow().getDecorView(), id);
        if (clickAble)
            views.setOnClickListener(this);
        return views;
    }

}
