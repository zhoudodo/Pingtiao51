package com.zls.baselib.custom.view.root;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zls.baselib.R;
import com.zls.baselib.custom.view.helputils.SelectorUtil;


public class BaseDialog extends Dialog implements View.OnClickListener  {

    protected Context mContext;         // 上下文
    protected LinearLayout mLayoutRoot;      // 总体根布局
    @SuppressWarnings("unused")
    protected LinearLayout mLayoutTop;       // 头部根布局
    protected RelativeLayout mLayoutTitle;     // 标题根布局
    protected TextView mHtvTitle;        // 标题
    protected View mViewTitleLine;   // 标题分割线
    protected LinearLayout mLayoutContent;   // 内容根布局
    protected TextView mHtvMessage;      // 内容
    protected LinearLayout mLayoutBottom;    // 底部根布局
    protected Button mBtnButton1;      // 底部按钮1
    protected Button mBtnButton2;      // 底部按钮2
    protected Button mBtnButton3;      // 底部按钮3
    protected View line1;              //button1的竖线
    protected View line2;             //button2的竖线
    protected View bottomLine;       //button2的竖线

    protected static BaseDialog mBaseDialog;      // 当前的对话框
    protected OnClickListener mOnClickListener1; // 按钮1的单击监听事件
    protected OnClickListener mOnClickListener2; // 按钮2的单击监听事件
    protected OnClickListener mOnClickListener3; // 按钮3的单击监听事件

    public BaseDialog(Context context) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        mContext = context;
        setContentView(R.layout.dialog_common_generic);
        initViews();
        initEvents();
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.dialog_generic_layout_root);
        mLayoutRoot.setBackground(SelectorUtil.getShape(Color.WHITE, 10, 0, Color.WHITE));
        mLayoutTop = (LinearLayout) findViewById(R.id.dialog_generic_layout_top);
        mLayoutTitle = (RelativeLayout) findViewById(R.id.dialog_generic_layout_title);
        mHtvTitle = (TextView) findViewById(R.id.dialog_generic_htv_title);
        mViewTitleLine = findViewById(R.id.dialog_generic_view_titleline);
        mLayoutContent = (LinearLayout) findViewById(R.id.dialog_generic_layout_content);
        mHtvMessage = (TextView) findViewById(R.id.dialog_generic_htv_message);
        mLayoutBottom = (LinearLayout) findViewById(R.id.dialog_generic_layout_bottom);
        mBtnButton1 = (Button) findViewById(R.id.dialog_generic_btn_button1);
        mBtnButton2 = (Button) findViewById(R.id.dialog_generic_btn_button2);
        mBtnButton3 = (Button) findViewById(R.id.dialog_generic_btn_button3);
        line1 = findViewById(R.id.view_dialog_one);
        line2 = findViewById(R.id.view_dialog_two);
        bottomLine = findViewById(R.id.bottom_line);
        mLayoutRoot.setVisibility(View.VISIBLE);
        setTitleLineVisibility(View.GONE);
    }

    public void setTitleLineVisibility(int visibility) {
        mViewTitleLine.setVisibility(visibility);
    }

    private void initEvents() {
        mBtnButton1.setOnClickListener(this);
        mBtnButton2.setOnClickListener(this);
        mBtnButton3.setOnClickListener(this);
    }

    /**
     * 填充新布局到内容布局
     *
     * @param resource
     */
    public void setDialogContentView(int resource) {
        View v = LayoutInflater.from(mContext).inflate(resource, null);
        if (mLayoutContent.getChildCount() > 0) {
            mLayoutContent.removeAllViews();
        }
        mLayoutContent.addView(v);
    }

    /**
     * 填充新布局到内容布局
     *
     * @param resource
     * @param params
     */
    public void setDialogContentView(int resource, LinearLayout.LayoutParams params) {
        View v = LayoutInflater.from(mContext).inflate(resource, null);
        if (mLayoutContent.getChildCount() > 0) {
            mLayoutContent.removeAllViews();
        }
        mLayoutContent.addView(v, params);
    }

    //只有title和确定取、消按钮
    @SuppressWarnings("deprecation")
    public static BaseDialog getCommonDialog(Activity mActivity, CharSequence title, CharSequence msgContent,
                                             CharSequence button1,
                                             OnClickListener listener1,
                                             CharSequence button3,
                                             OnClickListener listener3) {
        mBaseDialog = new BaseDialog(mActivity);
        mBaseDialog.setTitle(title);
        mBaseDialog.setMessage(msgContent);

        mBaseDialog.setButton1(button1, listener1);
        //默认显示“取消”，如有listener3监听赋值
        if (listener3 != null && button3 != null) {
            mBaseDialog.setButton3(button3, listener3);
        }
        //推荐列表不允许cancel
        mBaseDialog.setCancelable(false);
        mBaseDialog.setCanceledOnTouchOutside(false);

        android.view.WindowManager.LayoutParams lp = mBaseDialog.getWindow().getAttributes();
        lp.width = mActivity.getWindowManager().getDefaultDisplay().getWidth() - 50;
        mBaseDialog.getWindow().setAttributes(lp);

        return mBaseDialog;
    }

    @SuppressWarnings("deprecation")
    public static BaseDialog getTwoButtonsDialog(Activity mActivity, CharSequence title,
                                                 CharSequence button1,
                                                 OnClickListener listener1,
                                                 CharSequence button3,
                                                 OnClickListener listener3) {
        mBaseDialog = new BaseDialog(mActivity);
        mBaseDialog.setTitle(null);
        mBaseDialog.setMessage(title);//Message栏不显示

        mBaseDialog.setButton1(button1, listener1);
        //默认显示“取消”，如有listener3监听赋值
        if (listener3 != null && button3 != null) {
            mBaseDialog.setButton3(button3, listener3);
        }
        //推荐列表不允许cancel
        mBaseDialog.setCancelable(true);
        mBaseDialog.setCanceledOnTouchOutside(true);

        android.view.WindowManager.LayoutParams lp = mBaseDialog.getWindow().getAttributes();
        lp.width = mActivity.getWindowManager().getDefaultDisplay().getWidth() - 50;
        mBaseDialog.getWindow().setAttributes(lp);

        return mBaseDialog;
    }

    public void setTitle(CharSequence text) {
        mHtvTitle.setText(text);
        mLayoutTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        setTitleLineVisibility(View.GONE);
    }

    public void setMessage(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mLayoutContent.setVisibility(View.VISIBLE);
            mHtvMessage.setText(text);
        } else {
            mLayoutContent.setVisibility(View.GONE);
        }
    }

    public void setButton1(CharSequence text, OnClickListener listener) {
        if (text != null && listener != null) {
            mLayoutBottom.setVisibility(View.VISIBLE);
            mBtnButton1.setVisibility(View.VISIBLE);
            mBtnButton1.setText(text);
            mOnClickListener1 = listener;
        } else {
            mBtnButton1.setVisibility(View.GONE);
        }
    }

    public void setButton2(CharSequence text, OnClickListener listener) {
        if (text != null && listener != null) {
            mLayoutBottom.setVisibility(View.VISIBLE);
            mBtnButton2.setVisibility(View.VISIBLE);
            mBtnButton2.setText(text);
            line2.setVisibility(View.VISIBLE);
            mOnClickListener2 = listener;
        } else {
            mBtnButton2.setVisibility(View.GONE);
        }
    }

    public void setButton3(CharSequence text, OnClickListener listener) {
        if (text != null && listener != null) {
            mLayoutBottom.setVisibility(View.VISIBLE);
            mBtnButton3.setVisibility(View.VISIBLE);
            mBtnButton3.setText(text);
            mOnClickListener3 = listener;
        } else {
            mBtnButton3.setVisibility(View.GONE);
        }
    }

    public void setBottomButtonGone() {
        bottomLine.setVisibility(View.GONE);
        mLayoutBottom.setVisibility(View.GONE);
    }

    public void setDialogVisible(int value) {
        mLayoutRoot.setVisibility(value);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_generic_btn_button1) {
            dismiss();
            if (mOnClickListener1 != null) {
                mOnClickListener1.onClick(this, 0);
            }

        } else if (i == R.id.dialog_generic_btn_button2) {
            if (mOnClickListener2 != null) {
                mOnClickListener2.onClick(this, 1);
            }

        } else if (i == R.id.dialog_generic_btn_button3) {
            dismiss();
            if (mOnClickListener3 != null) {
                mOnClickListener3.onClick(this, 2);
            }
        }
    }

}
