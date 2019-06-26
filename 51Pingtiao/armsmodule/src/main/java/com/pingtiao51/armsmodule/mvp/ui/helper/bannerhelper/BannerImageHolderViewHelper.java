package com.pingtiao51.armsmodule.mvp.ui.helper.bannerhelper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

/**
 * 自定义Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。例子
 * Created by joeYu .
 */

public class BannerImageHolderViewHelper implements Holder<BannerParentInterface> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerParentInterface data) {

        if (data.getLoadUrl() != null) {
            GlideProxyHelper.loadImgByPlaceholder(imageView, R.drawable.banner_home, UrlDecoderHelper.decode(data.getLoadUrl()));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Bundle mBundle = new Bundle();
                    mBundle.putString("Base_url", data.getClickIntentUrl());
//                ContextHelper.getRequiredActivity(context).startAct(BannerWebActivity.class, mBundle);
                }
            });
        } else {
            GlideProxyHelper.loadImgForRes(imageView, data.getResId());
        }
    }

}
