package com.pingtiao51.armsmodule.mvp.ui.helper.bannerhelper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerNewsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

public class BannerNewsViewHelper implements Holder<BannerNewsInterface> {
    ImageView img;
    TextView title1,title2;
    @Override
    public View createView(Context context) {
        View rootView = View.inflate(context,R.layout.item_banner_news_layout,null);
        img = rootView.findViewById(R.id.img);
        title1 = rootView.findViewById(R.id.title1);
        title2 = rootView.findViewById(R.id.title2);
        return rootView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerNewsInterface data) {
        if (data.getLoadUrl() != null) {
            GlideProxyHelper.loadImgByPlaceholder(img, R.drawable.banner_home, UrlDecoderHelper.decode(data.getLoadUrl()));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Bundle mBundle = new Bundle();
                    mBundle.putString("Base_url", data.getClickIntentUrl());
//                ContextHelper.getRequiredActivity(context).startAct(BannerWebActivity.class, mBundle);
                }
            });
        } else {
            GlideProxyHelper.loadImgForRes(img, data.getResId());
        }
        title1.setText(data.getTitle1());
        title2.setText(data.getTitle2());
    }
}
