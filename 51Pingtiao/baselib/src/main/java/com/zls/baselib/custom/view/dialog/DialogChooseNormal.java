package com.zls.baselib.custom.view.dialog;


import android.app.Activity;
import android.content.Context;

import com.zls.baselib.custom.view.root.BaseDialog;

/**
 * 2个选择项  分别为确认和取消
 */
public class DialogChooseNormal extends BaseDialog {


 /*   BaseDialog.getCommonDialog(this,
            "你好",
            "你是谁",
            "确定",
    DialogInterface.OnClickListener { dialog, which ->
            ToastUtils.showShort("which = "+which + "  点击确定 ")
    },
            "取消",
    DialogInterface.OnClickListener{dialog, which ->
            ToastUtils.showShort("which = "+which + "  点击取消 ")
    }*/

    private DialogChooseNormal(ChooseBuilder builder) {
        super(builder.mContext);
        setTitle(builder.mTitle);
        setMessage(builder.mContent);

        setButton1(builder.btn1Content, builder.mOnClickListener1);
        //默认显示“取消”，如有listener3监听赋值
        setButton3(builder.btn3Content, builder.mOnClickListener3);
        //推荐列表不允许cancel
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        if(builder.btn1Color > 0){
            mBtnButton1.setTextColor(mContext.getResources().getColor(builder.btn1Color));
        }

        if(builder.btn3Color > 0){
            mBtnButton3.setTextColor(mContext.getResources().getColor(builder.btn3Color));
        }

    }

    public static class ChooseBuilder {
        private Activity mContext;
        private OnClickListener mOnClickListener1;
        private OnClickListener mOnClickListener3;
        private String mTitle;
        private String mContent;
        private String btn1Content;
        private String btn3Content;

        private int btn1Color;
        private int btn3Color;

        public DialogChooseNormal.ChooseBuilder setContext(Activity c) {
            mContext = c;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setOnClickListener1(OnClickListener c) {
            mOnClickListener1 = c;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setOnClickListener3(OnClickListener c) {
            mOnClickListener3 = c;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setBtn1Content(String btn1Content) {
            this.btn1Content = btn1Content;
            return this;
        }
        public DialogChooseNormal.ChooseBuilder setBtn1Colort(int btn1Color) {
            this.btn1Color = btn1Color;
            return this;
        }

        public DialogChooseNormal.ChooseBuilder setBtn3Content(String btn3Content) {
            this.btn3Content = btn3Content;
            return this;
        }
        public DialogChooseNormal.ChooseBuilder setBtn3Color(int btn3Color) {
            this.btn3Color = btn3Color;
            return this;
        }

        public DialogChooseNormal build() {
            return new DialogChooseNormal(this);
        }

    }


    /**
     *  val chooseDialog = DialogChooseNormal.ChooseBuilder()
     *                 .setTitle("hello")
     *                 .setContext(this)
     *                 .setContent("大家好")
     *                 .setBtn1Content("取消")
     *                 .setOnClickListener1 { dialog, which ->
     *                     ToastUtils.showShort("which = "+which + "  点击取消 ")
     *                 }.setBtn3Content("确定")
     *                 .setOnClickListener3 { dialog, which ->
     *                     ToastUtils.showShort("which = "+which + "  点击确定 ")
     *                 }.build()
     *         chooseDialog.show();
     */

}
