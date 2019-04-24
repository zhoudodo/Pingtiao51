package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JiekuanyongtuDialog extends FrameDialog {

    public interface JiekuanyongtuLis {
        void sure(String jiekuanyongtu);
    }

    JiekuanyongtuLis mJiekuanyongtuLis;

    public void setmJiekuanyongtuLis(JiekuanyongtuLis l) {
        this.mJiekuanyongtuLis = l;
    }

    public JiekuanyongtuDialog(Activity context) {
        super(context);
    }

    public JiekuanyongtuDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_jiekuanyongtu_layout;
    }

    ImageView cancel, sure;
    TextView title;
    private String titleStr;
    private String mStr;//借款用途
    WheelPicker mWheelPicker;
    private List<String> lilvList;

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
        lilvList = Arrays.asList(mActivity.getResources().getStringArray(R.array.jiekuanyongtu));
        mStr = lilvList.get(0);
        mWheelPicker.setCyclic(false);
        mWheelPicker.setData(lilvList);
        mWheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                if (mJiekuanyongtuLis != null) {
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
                if (mJiekuanyongtuLis != null && !TextUtils.isEmpty(mStr)) {
                    mJiekuanyongtuLis.sure(mStr);
                    dismiss();
                }


                break;

        }
    }

}
