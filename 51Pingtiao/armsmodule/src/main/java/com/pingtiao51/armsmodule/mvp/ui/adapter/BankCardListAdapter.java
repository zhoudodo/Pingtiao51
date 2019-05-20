package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.App;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService;
import com.pingtiao51.armsmodule.mvp.model.entity.request.UnBindCardRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserBankListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CloseBankPopu;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class BankCardListAdapter extends BaseQuickAdapter<UserBankListResponse, BaseViewHolder> {

    public BankCardListAdapter(int layoutResId, @Nullable List<UserBankListResponse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBankListResponse item) {
        //图标
        ImageView imageView = helper.getView(R.id.icon_bank);
        GlideProxyHelper.loadImgForUrl(imageView, UrlDecoderHelper.decode(item.getIcon()));

        helper.setText(R.id.card_name, item.getBankName());
        helper.setText(R.id.card_nums, "**** **** **** "+
                item.getBankNo().substring(item.getBankNo().length() - 4, item.getBankNo().length()));

        helper.getView(R.id.click_card_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseBankPopu closeBankPopu = new CloseBankPopu((FragmentActivity) ActivityUtils.getTopActivity(), new CloseBankPopu.CloseBankPopuInterface() {
                    @Override
                    public void closeBankRelative() {
                        closeBankCardRelative(item);
                    }
                });
                closeBankPopu.showViewBottom(helper.getView(R.id.click_card_more));
            }
        });
        switch (helper.getAdapterPosition()%3){
            case 0:
                helper.getView(R.id.relative_layout).setBackground(mContext.getResources().getDrawable(R.drawable.bg_card_num1));
                break;
            case 1:
                helper.getView(R.id.relative_layout).setBackground(mContext.getResources().getDrawable(R.drawable.bg_card_num2));
                break;
            case 2:
                helper.getView(R.id.relative_layout).setBackground(mContext.getResources().getDrawable(R.drawable.bg_card_num3));
                break;
        }
    }


    public void closeBankCardRelative(UserBankListResponse item){
            item.getId();
        ArmsUtils.obtainAppComponentFromContext(mContext).repositoryManager().obtainRetrofitService(UserService.class)
                .unbindBankCard(new UnBindCardRequest(
                        AppUtils.getAppVersionName(),
                        item.getId(),
                        "ANDRIOD"
                ))
                 .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(mContext).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> objectBaseJson) {
                        if(objectBaseJson.isSuccess()) {
                            mData.remove(item);
                            BankCardListAdapter.this.notifyDataSetChanged();
                        }else{
                            ArmsUtils.snackbarText(objectBaseJson.getMessage());
                        }
                    }
                });
    }
}
