package com.zls.baselib.custom.view.dialog;

import android.content.Context;
import android.view.View;

import com.zls.baselib.R;
import com.zls.baselib.custom.view.root.BaseDialog;

/**
 * 只有一个选择项  点击后dialog消失执行业务逻辑
 */
public class DialogHintNormal extends BaseDialog {


    private DialogHintNormal(HintBuilder hintBuilder) {
        super(hintBuilder.mContext);
        initHintNormalStyle();
        setMessage(hintBuilder.mContent);
        setButton2(hintBuilder.btn2Content,hintBuilder.mOnClickListener2);
        setTitle(hintBuilder.mTitle);
        setCancelable(hintBuilder.isCanceable);
    }


    private void initHintNormalStyle() {
        mBtnButton1.setVisibility(View.GONE);
        mBtnButton2.setVisibility(View.VISIBLE);
        mBtnButton3.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        bottomLine.setVisibility(View.GONE);
        mBtnButton2.setBackground(mContext.getResources().getDrawable(R.drawable.btn_dialog_middle));
    }



    public static class HintBuilder {
        private Context mContext;
        private OnClickListener mOnClickListener2;
        private String mTitle;
        private String mContent;
        private String btn2Content;
        private boolean isCanceable = true;

        public HintBuilder setContext(Context c) {
            mContext = c;
            return this;
        }

        public HintBuilder setOnClickListener(OnClickListener c) {
            mOnClickListener2 = c;
            return this;
        }

        public HintBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public HintBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public HintBuilder setBtn2Content(String btn2Content) {
            this.btn2Content = btn2Content;
            return this;
        }
        public HintBuilder setCancelable(boolean cancelable) {
            this.isCanceable = cancelable;
            return this;
        }

        public DialogHintNormal build() {
            return new DialogHintNormal(this);
        }

    }


    /**
     *  val normalDialog = DialogHintNormal.HintBuilder()
     *                 .setTitle("你好")
     *                 .setContent("大家好")
     *                 .setBtn2Content("好的")
     *                 .setContext(this)
     *                 .setOnClickListener(DialogInterface.OnClickListener { dialog, which ->
     *                     ToastUtils.showShort("which is"+which+"按钮被点击")
     *                 })
     *                 .build();
     *         normalDialog.show();
     */

}
