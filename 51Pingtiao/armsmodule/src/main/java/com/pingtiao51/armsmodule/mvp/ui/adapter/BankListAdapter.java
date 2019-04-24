package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserBankListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.BankListParentInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.util.List;

public class BankListAdapter extends BaseQuickAdapter<UserBankListResponse, BaseViewHolder> {
    public BankListAdapter(int layoutResId, @Nullable List<UserBankListResponse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBankListResponse item) {
        ImageView imageView = helper.getView(R.id.bank_icon);

        if (helper.getAdapterPosition() == mData.size() - 1) {
            helper.setGone(R.id.bank_next, true);
            GlideProxyHelper.loadImgForRes(imageView, R.drawable.banklist_add);
            helper.getView(R.id.bank_list_rl).setBackground(null);
        } else if (helper.getAdapterPosition() == 0) {
            if ("微信支付".equals(item.getBankName())) {
                helper.setGone(R.id.bank_next, false);
                helper.setText(R.id.bank_name, item.getBankName());
                GlideProxyHelper.loadImgForRes(imageView, R.drawable.weixin_icon);
            } else {
                helper.setText(R.id.bank_name, item.getBankName() + "(" +
                        item.getBankNo().substring(item.getBankNo().length() - 4, item.getBankNo().length()) + ")");
                helper.setGone(R.id.bank_next, false);
                GlideProxyHelper.loadImgForUrl(imageView, UrlDecoderHelper.decode(item.getIcon()));
            }
        } else {
            helper.setText(R.id.bank_name, item.getBankName() + "(" +
                    item.getBankNo().substring(item.getBankNo().length() - 4, item.getBankNo().length()) + ")");
            helper.setGone(R.id.bank_next, false);
            GlideProxyHelper.loadImgForUrl(imageView, UrlDecoderHelper.decode(item.getIcon()));
        }
    }
}
