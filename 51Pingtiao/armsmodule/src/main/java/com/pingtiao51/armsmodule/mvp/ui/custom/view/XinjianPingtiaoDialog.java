package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateDianziJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateDianziShoutiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.SecureCopyActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoMainActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoMainActivity;
import com.zls.baselib.custom.view.dialog.FrameDialog;

public class XinjianPingtiaoDialog extends FrameDialog {

    public XinjianPingtiaoDialog(Activity context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setCanceledOnTouchOutside(true);
        findViewsId(R.id.dianzijietiao_tv, true);
        findViewsId(R.id.dianzishoutiao_tv, true);
        findViewsId(R.id.zhizhijietiao_tv, true);
        findViewsId(R.id.zhizhishoutiao_tv, true);
        findViewsId(R.id.cancel_layout, true);
//        initLocation();
    }

    @Override
    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_xinjian_pingtiao_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dianzijietiao_tv:
                ActivityUtils.startActivity(CreateDianziJietiaoActivity.class);
                break;
            case R.id.dianzishoutiao_tv:
                ActivityUtils.startActivity(CreateDianziShoutiaoActivity.class);
                break;
            case R.id.zhizhijietiao_tv:
                ActivityUtils.startActivity(ZhizhiJietiaoMainActivity.class);
                break;
            case R.id.zhizhishoutiao_tv:
                ActivityUtils.startActivity(ZhizhiShoutiaoMainActivity.class);
                dismiss();
                break;
            case R.id.cancel_layout:
                dismiss();
                break;

        }
        dismiss();
    }
}
