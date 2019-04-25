package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.YiHuanKuanTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.FinishElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewShareActivity;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface.DIANZI_JIETIAO;

public class PingtiaoMultiAdapter extends BaseMultiItemQuickAdapter<PingtiaoDetailResponse, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PingtiaoMultiAdapter(List<PingtiaoDetailResponse> data) {
        super(data);
        addItemType(PingtiaoDetailResponse.DIANZI_JIETIAO, R.layout.item_jietiao_layout);
    }

    String userType = "0";//二维码分享专用

    @Override
    protected void convert(BaseViewHolder helper, PingtiaoDetailResponse item) {
        switch (item.getItemType()) {
            case DIANZI_JIETIAO:
                ImageView imgage = helper.getView(R.id.jianluetu);
                GlideProxyHelper.loadImgForRes(imgage, R.drawable.jietiao_shuoluetu2);
                String borrowAndLendState = "待还";
                String type = "出借人";
                String name = "";
                String jineType = "借款金额";
                String queryRequestType = "1";

                if (item.getBorrowAndLendState().equals("0")) {
                    borrowAndLendState = "待还";
                    userType = "0";
                    name = item.getLender();
                    type = "出借人";
                    jineType = "借款金额";
                    queryRequestType = "1";//借入
                } else if (item.getBorrowAndLendState().equals("1")) {
                    borrowAndLendState = "待收";
                    type = "借款人";
                    name = item.getBorrower();
                    jineType = "出借金额";
                    queryRequestType = "2";//借出
                    userType = "1";
                }
                SpannableStringBuilder sp1 = new SpanUtils()
                        .append(borrowAndLendState + " ").setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040)).setFontSize(18, true)
                        .append("¥" + item.getTotalAmount()).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057)).setFontSize(18, true)
                        .create();
                helper.setText(R.id.daihuan_money, sp1);

                helper.setText(R.id.chujieren, type + "：" + name);
                helper.setText(R.id.jiekuan_money, jineType + "：¥" + item.getAmount());
                helper.setText(R.id.huankuan_shijian, "还款时间：" + item.getRepaymentDate());

                String yuqi_flag = getStatus(helper, item);
                if("借款人完结".equals(yuqi_flag) || "LENDER_FINISHED".equals(yuqi_flag)){
                    helper.setTextColor(R.id.yuqi_flag, mContext.getResources().getColor(R.color.blue_color_467BB6));
                }else{
                    helper.setTextColor(R.id.yuqi_flag, mContext.getResources().getColor(R.color.red_color_ED4641));
                }
                helper.setText(R.id.yuqi_flag, yuqi_flag);

                setBtnType(helper, item);

                String finalQueryRequestType = queryRequestType;
                helper.getView(R.id.yihuankuan_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backDialog(helper, item, finalQueryRequestType);
//                        finishElectronicNote(helper.getView(R.id.yihuankuan_tv), item.getId(), finalQueryRequestType);
                    }
                });
                helper.getView(R.id.zaicifasong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "二维码分享");
                        bundle1.putInt(WebViewShareActivity.NOTE_ID, (int) item.getId());
                        bundle1.putInt(WebViewShareActivity.USER_TYPE, Integer.parseInt(userType));
                        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + item.getId() + "&userType=" + userType);

                        ActivityUtils.startActivity(bundle1, WebViewShareActivity.class);
                    }
                });
                if ((helper.getView(R.id.zaicifasong).getVisibility() == View.VISIBLE) && (helper.getView(R.id.yihuankuan_tv).getVisibility() == View.GONE)){
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,67),AutoSizeUtils.dp2px(mContext,25));
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    lp.rightMargin = AutoSizeUtils.dp2px(mContext,5);
                    lp.bottomMargin = AutoSizeUtils.dp2px(mContext,13);
                    helper.getView(R.id.zaicifasong).setLayoutParams(lp);
                } else{
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,67),AutoSizeUtils.dp2px(mContext,25));
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    lp.rightMargin = AutoSizeUtils.dp2px(mContext,90);
                    lp.bottomMargin = AutoSizeUtils.dp2px(mContext,13);
                    helper.getView(R.id.zaicifasong).setLayoutParams(lp);
                }

                if(helper.getView(R.id.zaicifasong).getVisibility() == View.GONE && helper.getView(R.id.yihuankuan_tv).getVisibility() == View.GONE){
                    RelativeLayout.LayoutParams vlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,27),AutoSizeUtils.dp2px(mContext,29));
                    vlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    vlp.rightMargin = AutoSizeUtils.dp2px(mContext,9);
                    vlp.topMargin = AutoSizeUtils.dp2px(mContext,6);
                    helper.getView(R.id.dianzipingtiao_icon_status).setLayoutParams(vlp);

                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,355),AutoSizeUtils.dp2px(mContext,147));
                    rlp.leftMargin = AutoSizeUtils.dp2px(mContext,10);
                    rlp.rightMargin = AutoSizeUtils.dp2px(mContext,10);
                    helper.getView(R.id.main_rl_layout).setLayoutParams(rlp);
                    helper.getView(R.id.main_rl_layout).setBackground(mContext.getResources().getDrawable(R.drawable.pingtiao_liebiao2));
                    helper.getView(R.id.main_rl_layout).setPadding(
                            AutoSizeUtils.dp2px(mContext,5),
                            0,
                            AutoSizeUtils.dp2px(mContext,5),
                            0
                            );
                }else{
//                    android:id="@+id/dianzipingtiao_icon_status"
//                    android:layout_width="27dp"
//                    android:layout_height="29dp"
//                    android:layout_alignParentRight="true"
//                    android:layout_marginTop="0dp"
//                    android:layout_marginRight="9dp"
                    RelativeLayout.LayoutParams vlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,27),AutoSizeUtils.dp2px(mContext,29));
                    vlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    vlp.rightMargin = AutoSizeUtils.dp2px(mContext,9);
                    vlp.topMargin = AutoSizeUtils.dp2px(mContext,0);
                    helper.getView(R.id.dianzipingtiao_icon_status).setLayoutParams(vlp);

                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,355),AutoSizeUtils.dp2px(mContext,180));
                    rlp.leftMargin = AutoSizeUtils.dp2px(mContext,10);
                    rlp.rightMargin = AutoSizeUtils.dp2px(mContext,10);
                    helper.getView(R.id.main_rl_layout).setLayoutParams(rlp);
                    helper.getView(R.id.main_rl_layout).setBackground(mContext.getResources().getDrawable(R.drawable.dianzipingtiao_item_bg));
                    helper.getView(R.id.main_rl_layout).setPadding(
                            AutoSizeUtils.dp2px(mContext,5),
                            0,
                            AutoSizeUtils.dp2px(mContext,5),
                            0
                    );
                }
            break;
        }
    }

    DialogChooseNormal mDialogChooseNormal;

    private void backDialog(
            BaseViewHolder helper, PingtiaoDetailResponse item,
            String queryRequestType) {
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(ActivityUtils.getTopActivity())
                    .setContent("该凭条确定已经结清？")
                    .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("确定")
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                            finishElectronicNote(helper.getView(R.id.yihuankuan_tv), item.getId(), queryRequestType);
                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    /**
     * 已还款按钮
     */
    private void finishElectronicNote(
            View view,
            long id,
            String queryRequestType// 1:借入 2：出借
    ) {
        ArmsUtils.obtainAppComponentFromContext(mContext).repositoryManager().obtainRetrofitService(PingtiaoApi.class).finishElectronicNote(
                new FinishElectronicNoteRequest(
                        AppUtils.getAppVersionName(),
                        id,
                        "ANDRIOD",
                        queryRequestType
                )
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseJson<Object>>() {
                    @Override
                    public void accept(BaseJson<Object> objectBaseJson) throws Exception {
                        if (objectBaseJson.isSuccess()) {
                            //TODO 已还款成功
                            EventBus.getDefault().post(new YiHuanKuanTag());
                        }
                    }
                }).isDisposed();
    }


    private void setBtnType(BaseViewHolder helper, PingtiaoDetailResponse item) {
        String status = item.getStatus();


        if (!"UNPAY_REJECT".equals(status) &&
                !"PAY_REJECT".equals(status) &&
                !"LENDER_FINISHED".equals(status) &&
                !"BORROWER_FINISHED".equals(status)
                ) {
            if ("UNCLOUD_STORE".equals(item.getHasCloudStoreOrConfirm()) ||
                    "UNCONFIRMED".equals(item.getHasCloudStoreOrConfirm())||
                    "UNSIGNED".equals(item.getStatus())) {
                helper.setGone(R.id.zaicifasong, true);
            } else {
                helper.setGone(R.id.zaicifasong, false);
            }
            if("UNSIGNED".equals(item.getStatus())) {
                helper.setGone(R.id.yihuankuan_tv, false);
            }else{
                helper.setGone(R.id.yihuankuan_tv, true);
            }
        } else {
            helper.setGone(R.id.zaicifasong, false);
            helper.setGone(R.id.yihuankuan_tv, false);
        }

        if ("DRAFT".equals(status) || "UNPAY_UNHANDLE".equals(status) || "PAY_UNHANDLE".equals(status)) {
            helper.setGone(R.id.zaicifasong, true);
            helper.setGone(R.id.yihuankuan_tv, false);
        }
    }


    public static String getStatus(BaseViewHolder helper, PingtiaoDetailResponse item) {
        String value = item.getStatus();
        String overDueDays = item.getOverDueDays();
        switch (value) {
            case "DRAFT":
                return "未生效";
            case "UNPAY_REJECT":
                return "被驳回";
            case "PAY_REJECT":
                return "被驳回";
            case "UNPAY_UNHANDLE":
                return "未生效";
            case "PAY_UNHANDLE":
                return "未生效";
            case "UNSIGNED":
                return "未生效";
            case "SIGNED":
                return "";
            case "OVERDUE":
                return "已逾期" + overDueDays + "天";
            case "BORROWER_FINISHED":
                return "借款人完结";
            case "LENDER_FINISHED":
                return "出借人完结";
            case "CLOSED":
                return "作废";
        }
        return "";
    }
}
