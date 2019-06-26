package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerBankPayDialogComponent;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PayApi;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.AddBankSucTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.BankDismissTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.PaySuccessTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CommonRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CreateDingdanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PayDingdanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ProductPriceRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CreateDingdanResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ProductPriceResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.SupportPaymentsResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserBankListResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.AddBankCardActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseArmsActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.BankListAdapter;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.wechat.WechatUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.AutoSizeUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * 支付规则 需要保存用户上次支付的银行卡信息
 */
@ActivityScope
public class BankPayDialog extends FrameDialog {

    @Inject
    IRepositoryManager mRepositoryManager;//用于管理网络请求层, 以及数据缓存层
    @Inject
    RxErrorHandler mErrorHandler;

    private String noteid;

    public BankPayDialog(Activity context) {
        super(context);
    }

    private Activity context;
    public BankPayDialog(Activity context, String noteid) {
        super(context);
        this.context = context;
        this.noteid = noteid;
    }

    @Override
    protected int getViewIds() {
        DaggerBankPayDialogComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(mActivity))
                .build()
                .inject(this);
        return R.layout.dialog_bankpay_layout;
    }


    BankListAdapter mBankListAdapter;

    TextView bankpay_money;
    TextView bankpay_jiaoyizhanghu_haoma;//交易账号
    TextView bankpay_zhifu_bankname;//支付银行卡名字 后4位
    RecyclerView bankpay_list_rv;
    VerticalScrollLinearLayout mMainLayout;//主页面
    LinearLayout bankpay_layout;//支付页面
    LinearLayout bankpay_list_layout;//选择页面

    UserBankListResponse mUserBankListResponse;//选择支付的类型和卡号等信息 很重要

    protected void initView() {
//        EventBus.getDefault().register(this);
        setCanceledOnTouchOutside(false);
        findViewsId(R.id.bankpay_close, true);
        bankpay_money = findViews(R.id.bankpay_money);
        bankpay_layout = findViews(R.id.bankpay_layout);
        mMainLayout = findViews(R.id.main_layout);
        bankpay_list_layout = findViews(R.id.bankpay_list_layout);
        bankpay_jiaoyizhanghu_haoma = findViews(R.id.bankpay_jiaoyizhanghu_haoma);
        bankpay_jiaoyizhanghu_haoma.setText(SavePreference.getStr(mActivity, PingtiaoConst.USER_PHONE));
        bankpay_zhifu_bankname = findViews(R.id.bankpay_zhifu_bankname);
        findViewsId(R.id.bankpay_type, true);
        findViewsId(R.id.bankpay_list_close, true);
        findViewsId(R.id.zhifu_btn2, true);
        findViewsId(R.id.zhifu_btn1, true);
        bankpay_list_rv = findViews(R.id.bankpay_list_rv);
        mBankListAdapter = new BankListAdapter(R.layout.item_banklist_layout, mUserBankList);
        bankpay_list_rv.setAdapter(mBankListAdapter);
        bankpay_list_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBankListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<UserBankListResponse> datas = adapter.getData();
                if (position == datas.size() - 1) {
                    //TODO 去添加银行卡
                    ActivityUtils.startActivity(AddBankCardActivity.class);
                } else {
                    //TODO 选择支付方式
                    UserBankListResponse response = datas.get(position);
                    SavePreference.save(SavePreference.getStr(getContext(), PingtiaoConst.USER_PHONE), response.getId());
                    mUserBankListResponse = response;
                    String hou4wei = "";
                    if (response.getBankNo() != null && response.getBankNo().length() > 4) {
                        hou4wei = response.getBankNo().substring(response.getBankNo().length() - 4, response.getBankNo().length());
                    }
                    bankpay_zhifu_bankname.setText(datas.get(position).getBankName()
                            + (TextUtils.isEmpty(hou4wei) ? "" : "(" + hou4wei + ")")
                    );
                    backChoicePayAnimator();
                }
            }
        });

    }

    @Override
    public void show() {
        super.show();
        getSupportPayments();
        getPrice();
//        getBankList();
    }


    /**
     * 滑动至选择银行卡界面
     */
    private void startChoicePayAnimator(){
        mMainLayout.smoothScrollTo(AutoSizeUtils.dp2px(getContext(),375),0);
   }

    /**
     * 滑动至支付界面
     */
   private void backChoicePayAnimator(){
       mMainLayout.smoothScrollTo(0,0);
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bankpay_close:
                EventBus.getDefault().post(new BankDismissTag());
                dismiss();
                break;
            case R.id.bankpay_type:
                //选择支付界面
                startChoicePayAnimator();
//                bankpay_layout.setVisibility(View.GONE);
//                bankpay_list_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.zhifu_btn2:
            case R.id.bankpay_list_close:
                backChoicePayAnimator();
//                bankpay_layout.setVisibility(View.VISIBLE);
//                bankpay_list_layout.setVisibility(View.GONE);
                break;
            case R.id.zhifu_btn1:
                //TODO 去支付
                toPay();
                break;
        }
    }

    public final static int PAY_BANK = 0;
    public final static int PAY_WECHAT = 1;
    private int mPayType = PAY_BANK;

    private void toPay() {
        if(mUserBankListResponse == null){
//            ArmsUtils.snackbarText("请选择支付方式");
//            return;
            //默认微信支付
            mPayType = PAY_WECHAT;
            //TODO 微信支付
            createDingdan(
                    Integer.valueOf(noteid),
                    null,
                    mPayAmount,
                    WECHAT,
                    "APP",
                    null
            );
        }else {
            switch (mUserBankListResponse.getBankName()) {
                case "微信支付":
                    mPayType = PAY_WECHAT;
                    //TODO 微信支付
                    createDingdan(
                            Integer.valueOf(noteid),
                            null,
                            mPayAmount,
                            WECHAT,
                            "APP",
                            null
                    );
                    break;
                default:
                    mPayType = PAY_BANK;
                    //TODO 银行卡支付
                    createDingdan(
                            Integer.valueOf(noteid),
                            null,
                            mPayAmount,
                            BANKCARD,
                            null,
                            (int) mUserBankListResponse.getId()
                    );
                    break;
            }
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private String mPayAmount;

    private void getPrice() {
        mRepositoryManager.obtainRetrofitService(PayApi.class).productPrice(new ProductPriceRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<ProductPriceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<ProductPriceResponse> rep) {
                        if (rep.isSuccess()) {
                            ProductPriceResponse pr = rep.getData();
                            double money = pr.getDiscountPrice();
                            mPayAmount = decimalFormat.format(money);
                            SpannableStringBuilder ssb = new SpanUtils()
                                    .append("￥").setFontSize(14,true)
                                    .append(decimalFormat.format(money)).setFontSize(22,true)
                                    .create();
//                            String moneyText = "￥" + decimalFormat.format(money);
                            bankpay_money.setText(ssb);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    SupportPaymentsResponse mSupportPaymentsResponse;

    private void getSupportPayments() {
        mRepositoryManager.obtainRetrofitService(PayApi.class).supportPayments(new CommonRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<SupportPaymentsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<SupportPaymentsResponse> rep) {
                        if (rep.isSuccess()) {
                            mSupportPaymentsResponse = rep.getData();
                            getBankList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    List<UserBankListResponse> mUserBankList = new ArrayList<>();

    private void getBankList() {
        mRepositoryManager.obtainRetrofitService(PayApi.class).getUserBankList(new CommonRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<List<UserBankListResponse>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<List<UserBankListResponse>> rep) {
                        if (rep.isSuccess()) {
                            List<UserBankListResponse> tempList = rep.getData();
                            long i = SavePreference.getLong(getContext(), SavePreference.getStr(getContext(), PingtiaoConst.USER_PHONE));
                            for (UserBankListResponse reptemp : tempList) {
                                if (reptemp.getId() == i) {
                                    mUserBankListResponse = reptemp;
                                    String hou4wei = "";
                                    if (reptemp.getBankNo() != null && reptemp.getBankNo().length() > 4) {
                                        hou4wei = reptemp.getBankNo().substring(reptemp.getBankNo().length() - 4, reptemp.getBankNo().length());
                                    }
                                    bankpay_zhifu_bankname.setText(reptemp.getBankName()
                                            + (TextUtils.isEmpty(hou4wei) ? "" : "(" + hou4wei + ")")
                                    );
                                }
                            }
                            mUserBankList.clear();
                            if (mSupportPaymentsResponse.getWechat() == 1) {
                                UserBankListResponse response1 = new UserBankListResponse();
                                response1.setBankName("微信支付");
                                  response1.setId(-1);
                                mUserBankList.add(response1);
                            }
                            if (tempList != null && tempList.size() > 0) {
                                mUserBankList.addAll(tempList);
                            }
                            UserBankListResponse response2 = new UserBankListResponse();
                            response2.setBankName("添加银行卡付款");
                            mUserBankList.add(response2);
                            mBankListAdapter.setNewData(mUserBankList);
                            mBankListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public final static String BANKCARD = "BANKCARD";
    public final static String WECHAT = "WECHAT";
    public final static String ALIPAY = "ALIPAY";
    /**
     * 生成订单
     */
    CreateDingdanResponse mCreateDingdanResponse;

    private void createDingdan(int noteId, String openId, String payAmount, String payMethod, String tradeType, Integer userBankId) {
        mRepositoryManager.obtainRetrofitService(PayApi.class).createDingdan(new CreateDingdanRequest(
                AppUtils.getAppVersionName(),
                noteId,
                openId,
                "ANDRIOD",
                payAmount,
                payMethod,//BANKCARD,WECHAT,ALIPAY
                tradeType,
                userBankId
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                            if(context instanceof BaseArmsActivity){
                                ((BaseArmsActivity)context).showLoading();
                            }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(context instanceof BaseArmsActivity){
                            ((BaseArmsActivity)context).hideLoading();
                        }
                    }
                })
                .subscribe(new Observer<BaseJson<CreateDingdanResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<CreateDingdanResponse> rep) {
                        if (rep.isSuccess()) {
                            mCreateDingdanResponse = rep.getData();
                            if(mCreateDingdanResponse == null){
                                EventBus.getDefault().post(new PaySuccessTag(PaySuccessTag.PAY_SUCCESS));
                                BankPayDialog.this.dismiss();
                            }else {
                                switch (mPayType){
                                    case PAY_WECHAT:
                                        WechatUtils.payWeChat(
                                                getContext(),
                                                Api.WECHAT_APPKEY,
                                                mCreateDingdanResponse.getPartnerId(),
                                                mCreateDingdanResponse.getPrepayId(),
                                                mCreateDingdanResponse.getNonceStr(),
                                                mCreateDingdanResponse.getTimeStamp(),
                                                mCreateDingdanResponse.getPaySign()
                                                );
                                        break;
                                    case PAY_BANK:
                                        payYzm();
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    ZhifuYzmDialog mZhifuYzmDialog;
    private String mCode = "";

    /**
     * 银行卡支付流程
     */
    private void payYzm() {
            mZhifuYzmDialog = new ZhifuYzmDialog(mActivity, new ZhifuYzmDialog.ZhifuComComplete() {
                @Override
                public void onSuccess(String s) {
                    mCode = s;
                    mZhifuYzmDialog.dismiss();
                    payBank();
                }
            });

        mZhifuYzmDialog.show();
    }

    //验证码确认支付订单
    private void payBank() {
        if(mCreateDingdanResponse == null){
            ArmsUtils.snackbarText("订单号生成失败，请重试");
            return;
        }
        mRepositoryManager.obtainRetrofitService(PayApi.class).confirmOrder(new PayDingdanRequest(
                AppUtils.getAppVersionName(),
                mCode,
                mCreateDingdanResponse.getOrderNo(),
                "ANDRIOD"
        )).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if(context instanceof BaseArmsActivity){
                            ((BaseArmsActivity)context).showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(context instanceof BaseArmsActivity){
                            ((BaseArmsActivity)context).hideLoading();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseJson<Object>>() {
                    @Override
                    public void accept(BaseJson<Object> objectBaseJson) throws Exception {
                        if (objectBaseJson.isSuccess()) {
                            //支付成功
                            EventBus.getDefault().post(new PaySuccessTag(PaySuccessTag.PAY_SUCCESS));
                            BankPayDialog.this.dismiss();
                        } else {
                            ArmsUtils.snackbarText(objectBaseJson.getMessage());
                        }
                    }
                }).isDisposed();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBankList(AddBankSucTag tag) {
        getBankList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new BankDismissTag());
    }
}
