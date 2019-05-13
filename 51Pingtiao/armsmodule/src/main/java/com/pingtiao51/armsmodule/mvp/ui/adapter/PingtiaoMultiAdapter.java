package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CloseElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.HuankuanFangshiActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.HuankuanStatusActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewShareActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.XiaoZhangActivity;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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
                if ("借款人完结".equals(yuqi_flag) || "出借人完结".equals(yuqi_flag)) {
                    helper.setTextColor(R.id.yuqi_flag, mContext.getResources().getColor(R.color.blue_color_467BB6));
                } else {
                    helper.setTextColor(R.id.yuqi_flag, mContext.getResources().getColor(R.color.red_color_ED4641));
                }
                helper.setText(R.id.yuqi_flag, yuqi_flag);

                setBtnType(helper, item);

                String finalQueryRequestType = queryRequestType;

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
                //删除借条
                helper.getView(R.id.shanchujietiao_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog(item);
                    }
                });
                helper.getView(R.id.xiaozhang).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 销账
                        hintDialog(item);
                    }
                });
//                if ((helper.getView(R.id.zaicifasong).getVisibility() == View.VISIBLE) && (helper.getView(R.id.yihuankuan_tv).getVisibility() == View.GONE)){
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,67),AutoSizeUtils.dp2px(mContext,25));
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                    lp.rightMargin = AutoSizeUtils.dp2px(mContext,5);
//                    lp.bottomMargin = AutoSizeUtils.dp2px(mContext,13);
//                    helper.getView(R.id.zaicifasong).setLayoutParams(lp);
//                } else{
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,67),AutoSizeUtils.dp2px(mContext,25));
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                    lp.rightMargin = AutoSizeUtils.dp2px(mContext,90);
//                    lp.bottomMargin = AutoSizeUtils.dp2px(mContext,13);
//                    helper.getView(R.id.zaicifasong).setLayoutParams(lp);
//                }
//
//                if(helper.getView(R.id.zaicifasong).getVisibility() == View.GONE && helper.getView(R.id.yihuankuan_tv).getVisibility() == View.GONE){
//                    RelativeLayout.LayoutParams vlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,27),AutoSizeUtils.dp2px(mContext,29));
//                    vlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    vlp.rightMargin = AutoSizeUtils.dp2px(mContext,9);
//                    vlp.topMargin = AutoSizeUtils.dp2px(mContext,6);
//                    helper.getView(R.id.dianzipingtiao_icon_status).setLayoutParams(vlp);
//
//                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,355),AutoSizeUtils.dp2px(mContext,147));
//                    rlp.leftMargin = AutoSizeUtils.dp2px(mContext,10);
//                    rlp.rightMargin = AutoSizeUtils.dp2px(mContext,10);
//                    helper.getView(R.id.main_rl_layout).setLayoutParams(rlp);
//                    helper.getView(R.id.main_rl_layout).setBackground(mContext.getResources().getDrawable(R.drawable.pingtiao_liebiao2));
//                    helper.getView(R.id.main_rl_layout).setPadding(
//                            AutoSizeUtils.dp2px(mContext,5),
//                            0,
//                            AutoSizeUtils.dp2px(mContext,5),
//                            0
//                            );
//                }else{
//
//                    RelativeLayout.LayoutParams vlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,27),AutoSizeUtils.dp2px(mContext,29));
//                    vlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    vlp.rightMargin = AutoSizeUtils.dp2px(mContext,9);
//                    vlp.topMargin = AutoSizeUtils.dp2px(mContext,0);
//                    helper.getView(R.id.dianzipingtiao_icon_status).setLayoutParams(vlp);
//
//                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(AutoSizeUtils.dp2px(mContext,355),AutoSizeUtils.dp2px(mContext,180));
//                    rlp.leftMargin = AutoSizeUtils.dp2px(mContext,10);
//                    rlp.rightMargin = AutoSizeUtils.dp2px(mContext,10);
//                    helper.getView(R.id.main_rl_layout).setLayoutParams(rlp);
//                    helper.getView(R.id.main_rl_layout).setBackground(mContext.getResources().getDrawable(R.drawable.dianzipingtiao_item_bg));
//                    helper.getView(R.id.main_rl_layout).setPadding(
//                            AutoSizeUtils.dp2px(mContext,5),
//                            0,
//                            AutoSizeUtils.dp2px(mContext,5),
//                            0
//                    );
//                }
                break;
        }
    }


    private void shanchujietiao(PingtiaoDetailResponse item) {
        ArmsUtils.obtainAppComponentFromContext(mContext).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .closeElectronicNote(new CloseElectronicNoteRequest(
                        AppUtils.getAppVersionName(),
                        item.getId(),
                        "ANDRIOD",
                        0L
                )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(mContext).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if (rep.isSuccess()) {
                            PingtiaoMultiAdapter.this.getData().remove(item);
                            PingtiaoMultiAdapter.this.notifyDataSetChanged();
                            ArmsUtils.snackbarText("删除成功");
                        }
                    }
                });


    }

    DialogChooseNormal mDialogChooseNormal;
    DialogChooseNormal mDeleteDialog;//删除借条弹框

    private void deleteDialog(final PingtiaoDetailResponse item) {
        mDeleteDialog = new DialogChooseNormal.ChooseBuilder()
                .setTitle("提示")
                .setContext(ActivityUtils.getTopActivity())
                .setContent("确定删除该借条？")
                .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDeleteDialog.dismiss();
                    }
                })
                .setBtn1Colort(R.color.gray_color_7D7D7D)
                .setBtn3Content("确定删除")
                .setOnClickListener3(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDeleteDialog.dismiss();
                        shanchujietiao(item);
                    }
                }).build();
        mDeleteDialog.show();
    }

    private void hintDialog(final PingtiaoDetailResponse item) {
        String title = "提示：销账等同于借款人还款，一经发起，不能撤销！";
        String btnHint = "继续发起";
        if ("1".equals(item.getHasApplyRepayRecord())) {
            //TODO 还款确认
            title = "该借条有1笔还款审批需要您处理，请处理后再操作完结";
            btnHint = "去处理";
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(ActivityUtils.getTopActivity())
                    .setContent(title)
                    .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content(btnHint)
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                            if ("1".equals(item.getHasApplyRepayRecord())) {
                                //TODO 还款审批
                                huankuanshenpi(item);
                            } else {
                                //TODO 销账
                                xiaozhang(item);
                            }
                        }
                    }).build();
            mDialogChooseNormal.show();
        } else {
            //TODO 销账
            title = "提示：销账等同于借款人还款，一经发起，不能撤销！";
            btnHint = "继续发起";
            xiaozhang(item);
        }

    }

    /**
     * 销账
     * @param item
     */
    private void xiaozhang(final PingtiaoDetailResponse item) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(XiaoZhangActivity.BORROW, item.getBorrower());
        bundle1.putString(XiaoZhangActivity.LENDER, item.getLender());
        bundle1.putDouble(XiaoZhangActivity.AMOUNT, Double.valueOf(item.getTotalAmount()));
        bundle1.putInt(XiaoZhangActivity.NOTEID, (int) item.getId());
        ActivityUtils.startActivity(bundle1, XiaoZhangActivity.class);
    }

    /**
     * 还款审批
     * @param item
     */
    private void huankuanshenpi(final PingtiaoDetailResponse item){
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "还款审批");
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "repaymentApproval?id=" + item.getId());
        ActivityUtils.startActivity(bundle1, WebViewActivity.class);
    }


    private void setBtnType(BaseViewHolder helper, final PingtiaoDetailResponse item) {
        String status = item.getStatus();
        //删除借条按钮显示
        if ("UNHANDLED".equals(status) || "LENDER_FINISHED".equals(status) || "REJECTED".equals(status) || "UNSIGNED".equals(item.getSignStatus())) {
            helper.setGone(R.id.shanchujietiao_tv, true);
        } else {
            helper.setGone(R.id.shanchujietiao_tv, false);
        }

        if ("1".equals(item.getBorrowAndLendState())) {//出借人 才有还款 审批 和 销账
            //0:待还  借款人  1:代收 出借人
            //还款审批 按钮显示
            if ("1".equals(item.getHasApplyRepayRecord()) && "SIGNED".equals(item.getSignStatus())) {
                helper.setGone(R.id.huankuanshenpi, true);
                helper.getView(R.id.huankuanshenpi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "还款审批");
                        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "repaymentApproval?id=" + item.getId());
                        ActivityUtils.startActivity(bundle1, WebViewActivity.class);
                    }
                });
            } else {
                helper.setGone(R.id.huankuanshenpi, false);
            }

            //销账  按钮显示
            if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "SIGNED".equals(item.getSignStatus())) {
                helper.setGone(R.id.xiaozhang, true);
            } else {
                helper.setGone(R.id.xiaozhang, false);
            }
        } else {
            helper.setGone(R.id.huankuanshenpi, false);
            helper.setGone(R.id.xiaozhang, false);
        }


        //再次发送 按钮显示
        if (("UNHANDLED".equals(status) || "UNSIGNED".equals(item.getSignStatus()))&& !"REJECTED".equals(status)) {
            helper.setGone(R.id.zaicifasong, true);
        } else {
            helper.setGone(R.id.zaicifasong, false);
        }


        if ("0".equals(item.getBorrowAndLendState())) {
            //还款状态 已还款 按钮显示
            if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "0".equals(item.getHasApplyRepayRecord()) && "SIGNED".equals(item.getSignStatus())) {
                helper.setGone(R.id.yihuankuan_tv, true);
                helper.getView(R.id.yihuankuan_tv).setBackground(mContext.getResources().getDrawable(R.drawable.yihaikuan_anniu));
                helper.getView(R.id.yihuankuan_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 已还款
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(HuankuanFangshiActivity.BORROW, item.getBorrower());
                        bundle1.putString(HuankuanFangshiActivity.LENDER, item.getLender());
                        bundle1.putDouble(HuankuanFangshiActivity.AMOUNT, Double.valueOf(item.getTotalAmount()));
                        bundle1.putInt(HuankuanFangshiActivity.NOTEID, (int) item.getId());
                        ActivityUtils.startActivity(bundle1, HuankuanFangshiActivity.class);
                    }
                });
            } else if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "1".equals(item.getHasApplyRepayRecord()) && "SIGNED".equals(item.getSignStatus())) {
                helper.setGone(R.id.yihuankuan_tv, true);
                helper.getView(R.id.yihuankuan_tv).setBackground(mContext.getResources().getDrawable(R.drawable.icon_huankuanzhuangtai));
//                GlideProxyHelper.loadImgForRes(helper.getView(R.id.yihuankuan_tv), R.drawable.icon_huankuanzhuangtai);
                helper.getView(R.id.yihuankuan_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 还款状态
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(HuankuanStatusActivity.BORROW, item.getBorrower());
                        bundle1.putString(HuankuanStatusActivity.LENDER, item.getLender());
                        bundle1.putDouble(HuankuanStatusActivity.AMOUNT, Double.valueOf(item.getTotalAmount()));
                        bundle1.putInt(HuankuanStatusActivity.NOTE_ID, (int) item.getId());
                        bundle1.putInt(HuankuanStatusActivity.USER_TYPE, Integer.parseInt(userType));
                        ActivityUtils.startActivity(bundle1, HuankuanStatusActivity.class);
                    }
                });
            } else {
                helper.setGone(R.id.yihuankuan_tv, false);
            }
        } else {
            helper.setGone(R.id.yihuankuan_tv, false);
        }


        //删除借条 待操作  出借人完结 （显示）
        //还款审批  出借人这边  新字段 hasApplyRepayRecord
        //销账 出借人这边  确认的 逾期即可
        //再次发送  待操作
        //
        // 借款人这边   已生效(包含确认和逾期) 未提醒 按钮显示  “已还款？”  已生效已提醒 按钮显示 还款状态
    }

    // 状态草稿 DRAFT
    // 待操作 UNHANDLED
    // 逾期 OVERDUE
    // 出借人完结 LENDER_FINISHED
    // 确认的 CONFIRMED
    // 被驳回的 REJECTED
    // 借款人删除 BORROWER_CLOSED
    // 出借人删除 LENDER_CLOSED
    //

    public static String getStatus(BaseViewHolder helper, PingtiaoDetailResponse item) {
        String value = item.getStatus();
        String overDueDays = item.getOverDueDays();
        switch (value) {
            case "DRAFT":
                return "";
            case "UNHANDLED":
                return "待确认";
            case "OVERDUE":
                return "已逾期" + overDueDays + "天";
            case "LENDER_FINISHED":
                return "已完结";
            case "BORROWER_FINISHED":
                return "借款人完结";
            case "CONFIRMED"://确认的
                if("UNSIGNED".equals(item.getSignStatus())){
                    return "待确认";
                }
                return "";
            case "REJECTED"://REJECTED
                return "被驳回";
            case "BORROWER_CLOSED"://借款人删除
                return "";
            case "LENDER_CLOSED"://出借人删除
                return "";
            case "CLOSED"://CLOSED
                return "";
        }
        return "";
    }

}
//-------------页面逻辑-----------------------------
//删除借条 待操作 出借人完结 （显示）
//还款审批 出借人这边 已签章 && 新字段 hasApplyRepayRecord
//销账 出借人这边 (确认的 ||逾期) && 已签章即可
//再次发送 待操作 || 未签章
//
// 借款人这边 已签章 && 已生效(包含确认和逾期) && 未提醒 按钮显示 “已还款？”
//已签章 && 已生效 && 已提醒 按钮显示 还款状态


// 状态草稿 DRAFT
// 待操作 UNHANDLED
// 逾期 OVERDUE
// 出借人完结 LENDER_FINISHED
// 确认的 CONFIRMED
// 被驳回的 REJECTED
// 借款人删除 BORROWER_CLOSED
// 出借人删除 LENDER_CLOSED
//

// 签章状态 signed unsigned
//