package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.net.URLDecoder;
import java.util.List;

public class PingtiaoZhizhiJieAdapter extends BaseMultiItemQuickAdapter<PingtiaoDetailResponse,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PingtiaoZhizhiJieAdapter(List<PingtiaoDetailResponse> data) {
        super(data);
        addItemType(PingtiaoDetailResponse.ZHIZHI_JIETIAO, R.layout.item_shoutiao_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PingtiaoDetailResponse item) {
        switch (item.getItemType()) {
            case PingtiaoDetailResponse.ZHIZHI_JIETIAO:
                helper.setGone(R.id.jiekuan_money, true);
                ImageView imgage2 = helper.getView(R.id.jianluetu);
//                GlideProxyHelper.loadImgForRes(imgage2, R.drawable.jietiao_suoluetu);
                GlideProxyHelper.loadImgForUrl(imgage2, UrlDecoderHelper.decode(item.getViewUrl()));
                String borrowAndLendState = "借到";
                String type = "出借人";
                String name = "";
                String jineType = "借款金额";
                if ("0".equals(item.getBorrowAndLendState())) {
                    borrowAndLendState = "借到";
                    name = item.getLender();
                    type = "出借人";
                    jineType = "借款金额";
                } else if ("1".equals(item.getBorrowAndLendState())) {
                    borrowAndLendState = "待收";
                    type = "借款人";
                    name = item.getBorrower();
                    jineType = "出借金额";
                }
                SpannableStringBuilder sp3 = new SpanUtils()
                        .append(borrowAndLendState+" ").setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040)).setFontSize(18, true)
                        .append("¥" + item.getAmount()).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057)).setFontSize(18, true)
                        .create();
                helper.setText(R.id.daihuan_money, sp3);
                helper.setText(R.id.chujieren, "出借人：" + item.getLender());
                helper.setText(R.id.jiekuan_money, "借款人：" + item.getBorrower());
                helper.setText(R.id.huankuan_shijian, "还款时间：" + item.getRepaymentDate());
                helper.setImageResource(R.id.dianzipingtiao_icon_status, R.drawable.dianzipingtiao_jie_icon);
                helper.setBackgroundRes(R.id.item_bg, R.drawable.dianzipingtiao_item_bg2);
                break;
        }
    }
}
