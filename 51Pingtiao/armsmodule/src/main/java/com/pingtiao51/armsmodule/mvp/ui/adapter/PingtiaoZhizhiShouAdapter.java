package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.util.List;

import static com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface.DIANZI_SHOUTIAO;
import static com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface.ZHIZHI_SHOUTIAO;

public class PingtiaoZhizhiShouAdapter extends BaseMultiItemQuickAdapter<PingtiaoDetailResponse,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PingtiaoZhizhiShouAdapter(List<PingtiaoDetailResponse> data) {
        super(data);

        addItemType(PingtiaoDetailResponse.ZHIZHI_SHOUTIAO, R.layout.item_shoutiao_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PingtiaoDetailResponse item) {
        switch (item.getItemType()) {
            case PingtiaoDetailResponse.ZHIZHI_SHOUTIAO:
                ImageView imgage3 = helper.getView(R.id.jianluetu);
//                GlideProxyHelper.loadImgForRes(imgage3, R.drawable.shoutiao_suoluetu);
                GlideProxyHelper.loadImgForUrl(imgage3, UrlDecoderHelper.decode(item.getViewUrl()));

                SpannableStringBuilder sp4 = new SpanUtils()
                        .append("收到 ").setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040)).setFontSize(18, true)
                        .append("¥" + item.getAmount()).setForegroundColor(mContext.getResources().getColor(R.color.green_2BB77E)).setFontSize(18, true)
                        .create();
                helper.setText(R.id.daihuan_money, sp4);
                helper.setText(R.id.chujieren, "经手人：" + item.getBorrower());
                helper.setText(R.id.huankuan_shijian, "经手时间：" + item.getRepaymentDate());
                helper.setBackgroundRes(R.id.item_bg, R.drawable.dianzipingtiao_item_bg2);
                helper.setGone(R.id.jiekuan_money, true);
                helper.setText(R.id.jiekuan_money,"递交人："+item.getLender());

                helper.setImageResource(R.id.dianzipingtiao_icon_status, R.drawable.shoutiao_icon);
                break;
        }
    }
}
