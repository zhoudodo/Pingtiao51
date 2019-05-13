package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.wheelpicker.WheelPicker;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.ArrayList;
import java.util.List;

public class ComSingleWheelDialog extends FrameDialog {

    public interface ComSingleWheelInterface {
        void getChoiceStr(String str);
    }

    private ComSingleWheelInterface mComSingleWheelInterface;

    public void setComSingleWheelInterface(ComSingleWheelInterface l) {
        mComSingleWheelInterface = l;
    }

    public ComSingleWheelDialog(Activity context) {
        super(context);
    }

    public ComSingleWheelDialog(Activity context, List<String> datas) {
        super(context);
        mDatas = datas;
        mChoiceStr = datas.get(0);
        mWheelPicker.post(new Runnable() {
            @Override
            public void run() {
                mWheelPicker.setData(mDatas);
            }
        });
    }

    public ComSingleWheelDialog(Activity context, List<String> datas, String titleStr) {
        super(context);
        mDatas = datas;
        mChoiceStr = datas.get(0);
        mWheelPicker.post(new Runnable() {
            @Override
            public void run() {
                mWheelPicker.setData(mDatas);
                title.setText(titleStr);
            }
        });
    }

    public ComSingleWheelDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_com_single_wheel;
    }

    ImageView cancel, sure;
    TextView title;
    WheelPicker mWheelPicker;
    private List<String> mDatas;

    private String mChoiceStr;

    @Override
    protected void initView() {
        super.initView();
        mWheelPicker = findViews(R.id.main_wheel);
        setCanceledOnTouchOutside(false);
        cancel = findViewsId(R.id.cancel, true);
        sure = findViewsId(R.id.sure, true);
        title = findViews(R.id.title);

        mWheelPicker.setCyclic(false);
//        mWheelPicker.setData(mDatas);
        mWheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                mChoiceStr = (String) data;
            }
        });
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                if (mComSingleWheelInterface != null && !TextUtils.isEmpty(mChoiceStr)) {
                    mComSingleWheelInterface.getChoiceStr(mChoiceStr);
                    dismiss();
                }

                break;

        }
    }

}
