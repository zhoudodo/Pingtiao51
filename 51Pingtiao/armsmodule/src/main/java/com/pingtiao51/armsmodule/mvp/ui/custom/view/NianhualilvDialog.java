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

public class NianhualilvDialog extends FrameDialog {

    public interface NianhualilvListener {
        void getNianhualilv(String lilv);
    }

    private NianhualilvListener mNianhualilvListener;

    public void setmNianhualilvListener(NianhualilvListener l) {
        mNianhualilvListener = l;
    }

    public NianhualilvDialog(Activity context) {
        super(context);
    }

    public NianhualilvDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_nianhualilv_layout;
    }

    ImageView cancel, sure;
    TextView title;
    private String titleStr;
    private String mStr = "0%";//年化利率
    WheelPicker mWheelPicker;
    private List<String> lilvList;
    private List<String> lilvList2;

    @Override
    protected void initView() {
        super.initView();
        mWheelPicker = findViews(R.id.main_wheel);
        setCanceledOnTouchOutside(false);
        cancel = findViewsId(R.id.cancel, true);
        sure = findViewsId(R.id.sure, true);
        title = findViews(R.id.title);
        if (lilvList == null) {
            lilvList = new ArrayList<>();
        }
        if (lilvList2 == null) {
            lilvList2 = new ArrayList<>();
        }
        lilvList.clear();
        lilvList2.clear();
        for (int i = 0; i < 37; i++) {
            lilvList.add(i + "%");

        }
        lilvList2.addAll(lilvList);
        mWheelPicker.setCyclic(false);
       mWheelPicker.setData(lilvList2);
        mWheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                if (mNianhualilvListener != null) {
                    mStr = (String) data;
                }
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
                if (mNianhualilvListener != null &&  !TextUtils.isEmpty(mStr)) {
                    mNianhualilvListener.getNianhualilv(mStr);
                    dismiss();
                }

                break;

        }
    }

}
