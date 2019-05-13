package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.ui.helper.img.WechatUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.share.ShareHelper;
import com.zls.baselib.custom.view.dialog.FrameDialog;

/**
 * 分享diaolog
 */
public class ShareDialog extends FrameDialog {
    /**
     *  这个接口主要是为了方便外部处理业务 这样整个Dialog就是一个独立完整的分享View不参杂业务
     *   除了微信 其他全是拼音
     */
    public interface ShareClickListener{

        public void shareWechat();

        public void shareErweima();
        public void shareLianjie();
        public void shareDuanxin();


    }

    private ShareClickListener mShareClickListener;

    public void setShareListener(ShareClickListener scl){
        mShareClickListener = scl;
    }


    public ShareDialog(Activity context) {
        super(context);
        setCanceledOnTouchOutside(true);
    }

    public ShareDialog(Activity context,String title,int[] ints) {
        super(context);
        setCanceledOnTouchOutside(true);
        findViews(R.id.title).post(new Runnable() {
            @Override
            public void run() {
                TextView titleTv = findViews(R.id.title);
                titleTv.setText(title);
                if(ints.length == 4){
                    findViews(R.id.share_weixin).setVisibility(ints[0] == 1?View.VISIBLE:View.GONE);
                    findViews(R.id.share_erweima).setVisibility(ints[1] == 1?View.VISIBLE:View.GONE);
                    findViews(R.id.share_duanxin).setVisibility(ints[2] == 1?View.VISIBLE:View.GONE);
                    findViews(R.id.share_lianjie).setVisibility(ints[3] == 1?View.VISIBLE:View.GONE);
                }
            }
        });
    }




    public ShareDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
    }

    @Override
    protected int getViewIds() {
        return  R.layout.dialog_share_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewsId(R.id.share_duanxin,true);
        findViewsId(R.id.share_erweima,true);
        findViewsId(R.id.share_lianjie,true);
        findViewsId(R.id.share_weixin,true);
        findViewsId(R.id.cancel_tv,true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_weixin:
                //TODO 微信
                if(mShareClickListener != null){
                    mShareClickListener.shareWechat();
                }
                break;
            case R.id.share_erweima:
                //TODO 二维码
                if(mShareClickListener != null){
                    mShareClickListener.shareErweima();
                }
                break;
            case R.id.share_lianjie:
                //TODO 复制链接
                if(mShareClickListener != null){
                    mShareClickListener.shareLianjie();
                }
                break;
            case R.id.share_duanxin:
                //TODO 短信
                if(mShareClickListener != null){
                    mShareClickListener.shareDuanxin();
                }
                break;
            case R.id.cancel_tv:
                dismiss();
                break;

        }
    }




}
