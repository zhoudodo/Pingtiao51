package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;

import java.util.List;

import static com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface.DIANZI_SHOUTIAO;

public class PingtiaoShouAdapter extends BaseMultiItemQuickAdapter<PingtiaoDetailResponse,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PingtiaoShouAdapter(List<PingtiaoDetailResponse> data) {
        super(data);
        addItemType(PingtiaoDetailResponse.DIANZI_SHOUTIAO, R.layout.item_shoutiao_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PingtiaoDetailResponse item) {
        switch (item.getItemType()) {
            case DIANZI_SHOUTIAO:
                ImageView imgage1 = helper.getView(R.id.jianluetu);
                GlideProxyHelper.loadImgForRes(imgage1, R.drawable.icon_shoutiao_bg);
                SpannableStringBuilder sp2 = new SpanUtils()
                        .append("收到 ").setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040)).setFontSize(18, true)
                        .append("¥" + item.getAmount()).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057)).setFontSize(18, true)
                        .create();
                helper.setText(R.id.daihuan_money, sp2);
                helper.setText(R.id.chujieren, "递交人：" + item.getLender());
                helper.setText(R.id.huankuan_shijian, "经手时间：" + item.getRepaymentDate());
                helper.setGone(R.id.jiekuan_money, false);
                break;
        }
    }
}
