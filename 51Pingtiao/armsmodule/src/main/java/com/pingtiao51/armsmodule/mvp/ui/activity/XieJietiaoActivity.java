package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerXieJietiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.XieJietiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PayApi;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.BankDismissTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.PaySuccessTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ProductPriceRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ProductPriceResponse;
import com.pingtiao51.armsmodule.mvp.presenter.XieJietiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.BankPayDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CycleDatePickerDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.JiekuanyongtuDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.NianhualilvDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/17/2019 21:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class XieJietiaoActivity extends BaseArmsActivity<XieJietiaoPresenter> implements XieJietiaoContract.View {

    public final static String XieJietiaoActivity = "XieJietiaoActivity";

    public final static int JIEKUANREN = 0;
    public final static int CHUJIEREN = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerXieJietiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_xie_jietiao; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.xiejietiao_chujieren_shenfenzheng)
    TextView xiejietiao_chujieren_shenfenzheng;

    @BindView(R.id.xiejietiao_chujieren_xingming)
    TextView xiejietiao_chujieren_xingming;

    private int mType = JIEKUANREN;

    private void initPageConfig() {
        mType = getIntent().getIntExtra(XieJietiaoActivity, 0);
        switch (mType) {
            case JIEKUANREN://借款人
//                jiekuanrenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.jiekuanrenzhifu_select));
                chujierenzhifu.setVisibility(View.GONE);
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) jiekuanrenzhifu.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                rlp.addRule(RelativeLayout.CENTER_VERTICAL);
                jiekuanrenzhifu.setLayoutParams(rlp);
                jiekuanrenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.chujierenzhifu_cantselect));
                hasJiekuanrenZhifu = true;

                break;
            case CHUJIEREN://出借人
                xiejietiao_chujieren_xingming.setText("借款人姓名");
                xiejietiao_chujieren_shenfenzheng.setText("借款人身份证号");
                break;
        }
    }

    @BindView(R.id.xiejietiao_jiekuanjine_edit)
    EditText xiejietiao_jiekuanjine_edit;

    @BindView(R.id.xiejietiao_jiekuanyongtutianxie)
    EditText xiejietiao_jiekuanyongtutianxie;

    @BindView(R.id.xiejietiao_chujierenxingming)
    EditText xiejietiao_chujierenxingming;
    @BindView(R.id.xiejietiao_chujierenshengfengzhenghao)
    EditText xiejietiao_chujierenshengfengzhenghao;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initPageConfig();


        xiejietiao_jiekuanjine_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                jisuanBenxi();
            }
        });
        initTitle();
        getPrice();
    }

    private void initTitle() {
        setTitle("借条");
        TextView rightTv = findViewById(R.id.right_tv);
        rightTv.setText("预览");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 我的凭条 新建页面
                yulanJietiao();
            }
        });
    }

    /**
     * 预览借条
     */
    private void yulanJietiao() {
        Intent intent = new Intent(this, YulanJietiaoActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(YulanJietiaoActivity.jiekuanjine, xiejietiao_jiekuanjine_edit.getText().toString());
        mBundle.putString(YulanJietiaoActivity.jiekuanriqi, sendJiekuanriqi);
        mBundle.putString(YulanJietiaoActivity.huankuanriqi, sennHuankuanriqi);
        mBundle.putString(YulanJietiaoActivity.benxihe, String.valueOf(mBenxihe));
        mBundle.putString(YulanJietiaoActivity.nianhualilv, mLilv);
        mBundle.putString(YulanJietiaoActivity.lixizongji, String.valueOf(mLixi));
        String chujierenmingzi = TextUtils.isEmpty(xiejietiao_chujierenxingming.getText().toString()) ? "- -" : xiejietiao_chujierenxingming.getText().toString();
        mBundle.putString(YulanJietiaoActivity.chujierenmingzi, chujierenmingzi);
        mBundle.putString(YulanJietiaoActivity.chujierenshenfenzheng, xiejietiao_chujierenshengfengzhenghao.getText().toString());
        if (findViewById(R.id.jiekuanyongtu_qita).getVisibility() == View.VISIBLE) {
            String jiekuanyongtuZ = TextUtils.isEmpty(xiejietiao_jiekuanyongtutianxie.getText().toString()) ? "- -" : xiejietiao_jiekuanyongtutianxie.getText().toString();
            mBundle.putString(YulanJietiaoActivity.jiekuanyongtu, jiekuanyongtuZ);
        } else {
            String jiekuanyongtuZ = TextUtils.isEmpty(jiekuanyongtuStr) ? "- -" : jiekuanyongtuStr;
            mBundle.putString(YulanJietiaoActivity.jiekuanyongtu, jiekuanyongtuZ);
        }

        long between_days = (mCalendar2.getTimeInMillis() - mCalendar1.getTimeInMillis()) / (1000 * 3600 * 24);

        mBundle.putString(YulanJietiaoActivity.tianshu, between_days + "");
        mBundle.putInt(XieJietiaoActivity, mType);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @BindView(R.id.xiejietiao_jiekuanriqi_tv)
    TextView xiejietiao_jiekuanriqi_tv;
    @BindView(R.id.xiejietiao_huankuanriqi_edit)
    TextView xiejietiao_huankuanriqi_edit;
    @BindView(R.id.xiejietiao_nianhualilv_edit)
    TextView xiejietiao_nianhualilv_edit;
    @BindView(R.id.xiejietiao_benxi_hint)
    TextView xiejietiao_benxi_hint;
    @BindView(R.id.xiejietiao_jiekuanyongtu)
    TextView xiejietiao_jiekuanyongtu;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
//    private DatePickerDialog mDatePickerDialog1;
//    private DatePickerDialog mDatePickerDialog2;
    private CycleDatePickerDialog mDatePickerDialog1;
    private CycleDatePickerDialog mDatePickerDialog2;
    private NianhualilvDialog mNianhualilvDialog;
    private JiekuanyongtuDialog mJiekuanyongtuDialog;

    java.util.Calendar mCalendar1 = java.util.Calendar.getInstance();
    String sendJiekuanriqi = "- -";
    java.util.Calendar mCalendar2 = java.util.Calendar.getInstance();
    String sennHuankuanriqi = "- -";
    Date jiekuanriqi = new Date();
    Date huankuanriqi = new Date();
    private String mLilv = "";
    private String jiekuanyongtuStr = "";
    private String jiekuanyongtuQita = "";//借款用途其他选项

    boolean hasJiekuanriqi = false;
    boolean hasHuankuanriqi = false;

    @OnClick({R.id.choice_jiekuanriqi_layout, R.id.choice_huankuanriqi_layout, R.id.choice_nianhualilv_layout, R.id.choice_jiekuanyongtu_layout,
            R.id.jiekuanrenzhifu, R.id.chujierenzhifu, R.id.xiejietiao_btn})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.choice_jiekuanriqi_layout:
                if (mDatePickerDialog1 == null) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(new Date());//

                    Calendar end = Calendar.getInstance();
                    end.setTime(new Date());//
                    end.add(Calendar.DAY_OF_MONTH,6);
                    mDatePickerDialog1 = new CycleDatePickerDialog(this,start,end);
                }
                mDatePickerDialog1.setChoiceSureInterface(new CycleDatePickerDialog.ChoiceSureInterface() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void sure(Date date) {
                        if(hasHuankuanriqi){
                           if(date.getTime() >= huankuanriqi.getTime()){
                               ArmsUtils.snackbarText("借款日期必须小于还款日期");
                               return;
                           }
                        }
                        hasJiekuanriqi = true;
                        mCalendar1.setTime(date);
                        jiekuanriqi = date;
                        xiejietiao_jiekuanriqi_tv.setText(
                                mCalendar1.get(Calendar.YEAR) + "-"
                                        + (mCalendar1.get(Calendar.MONTH) + 1) + "-"
                                        + mCalendar1.get(Calendar.DAY_OF_MONTH));
                        sendJiekuanriqi = mCalendar1.get(Calendar.YEAR) + "年"
                                + (mCalendar1.get(Calendar.MONTH) + 1) + "月"
                                + mCalendar1.get(Calendar.DAY_OF_MONTH) + "日";
                        jisuanRiqi();
                        jisuanBenxi();
                    }
                });
                mDatePickerDialog1.setTitle("借款日期");
                mDatePickerDialog1.show();
                break;
            case R.id.choice_huankuanriqi_layout:
                if(!hasJiekuanriqi){
                    ArmsUtils.snackbarText("请先选择借款日期");
                    return;
                }
//                if (mDatePickerDialog2 == null) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(jiekuanriqi);//
                    start.add(Calendar.DAY_OF_MONTH,1);
                    Calendar end = Calendar.getInstance();
                    end.setTime(jiekuanriqi);//
                    end.add(Calendar.YEAR,10);
                    mDatePickerDialog2 = new CycleDatePickerDialog(this,start,end);
//                }
                mDatePickerDialog2.setChoiceSureInterface(new CycleDatePickerDialog.ChoiceSureInterface() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void sure(Date date) {
                        hasHuankuanriqi = true;
                        mCalendar2.setTime(date);
                        huankuanriqi = date;
                        xiejietiao_huankuanriqi_edit.setText(
                                mCalendar2.get(Calendar.YEAR) + "-"
                                        + (mCalendar2.get(Calendar.MONTH) + 1) + "-"
                                        + mCalendar2.get(Calendar.DAY_OF_MONTH)
                        );
                        sennHuankuanriqi = mCalendar2.get(Calendar.YEAR) + "年"
                                + (mCalendar2.get(Calendar.MONTH) + 1) + "月"
                                + mCalendar2.get(Calendar.DAY_OF_MONTH) + "日";
                        jisuanRiqi();
                        jisuanBenxi();
                    }
                });
                mDatePickerDialog2.setTitle("还款日期", jiekuanriqi);
                mDatePickerDialog2.show();
                break;
            case R.id.choice_nianhualilv_layout:
                if (mNianhualilvDialog == null) {
                    mNianhualilvDialog = new NianhualilvDialog(this);
                }
                mNianhualilvDialog.setTitle(getResources().getString(R.string.nianhualilv));
                mNianhualilvDialog.setmNianhualilvListener(new NianhualilvDialog.NianhualilvListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void getNianhualilv(String lilv) {
                        mLilv = lilv;
                        xiejietiao_nianhualilv_edit.setText(
                                lilv + " " +
                                        decimalFormat.format(getLirun()) + "元"
                        );
                        jisuanBenxi();
                    }
                });
                mNianhualilvDialog.show();
                break;
            case R.id.choice_jiekuanyongtu_layout:
                if (mJiekuanyongtuDialog == null) {
                    mJiekuanyongtuDialog = new JiekuanyongtuDialog(this);
                }
                mJiekuanyongtuDialog.setTitle("借款用途");
                mJiekuanyongtuDialog.setmJiekuanyongtuLis(new JiekuanyongtuDialog.JiekuanyongtuLis() {
                    @Override
                    public void sure(String jiekuanyongtu) {
                        jiekuanyongtuStr = jiekuanyongtu;
                        xiejietiao_jiekuanyongtu.setText(jiekuanyongtuStr);
                        if (jiekuanyongtuStr.equals("其他")) {
                            findViewById(R.id.jiekuanyongtu_qita).setVisibility(View.VISIBLE);
                        } else {
                            jiekuanyongtuStr = jiekuanyongtu;
                            findViewById(R.id.jiekuanyongtu_qita).setVisibility(View.GONE);
                        }
                    }
                });
                mJiekuanyongtuDialog.show();
                break;
            case R.id.jiekuanrenzhifu:
                hasJiekuanrenZhifu = true;
                switch (mType) {
                    case JIEKUANREN://借款人
                        //不能改变背景哦
                        break;
                    default:
                        jiekuanrenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.jiekuanrenzhifu_select));
                        chujierenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.chujierenzhifu_unselect));
                        break;
                }
                break;
            case R.id.chujierenzhifu:
                hasJiekuanrenZhifu = false;
                jiekuanrenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.jiekuanrenzhifu_unselect));
                chujierenzhifu.setImageDrawable(getResources().getDrawable(R.drawable.chujierenzhifu_select));
                break;
            case R.id.xiejietiao_btn:
                if (checkCreateTiaojian()) {
                    findViewById(R.id.xiejietiao_btn).setEnabled(false);
                    /*findViewById(R.id.xiejietiao_btn).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.xiejietiao_btn).setEnabled(true);
                        }
                    }, 2000);*/
                    createJietiao();
                }

                break;
        }
    }

    private boolean checkCreateTiaojian() {
        if (TextUtils.isEmpty(xiejietiao_jiekuanjine_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写借款金额");
            return false;
        }
        if (findViewById(R.id.jiekuanyongtu_qita).getVisibility() == View.VISIBLE && TextUtils.isEmpty(xiejietiao_jiekuanyongtutianxie.getText().toString())) {
            ArmsUtils.snackbarText("请填写借款用途");
            return false;
        }
        if (TextUtils.isEmpty(xiejietiao_chujierenxingming.getText().toString())) {
            ArmsUtils.snackbarText("请填写出借人姓名");
            return false;
        }
//        if (TextUtils.isEmpty(xiejietiao_chujierenshengfengzhenghao.getText().toString())) {
//            ArmsUtils.snackbarText("请填写出借人身份证号");
//            return false;
//        }

        if (TextUtils.isEmpty(jiekuanyongtuStr)) {
            ArmsUtils.snackbarText("请选择借款用途");
            return false;
        }
        if (TextUtils.isEmpty(jiekuanyongtuStr)) {
            ArmsUtils.snackbarText("请选择借款用途");
            return false;
        }
        if (TextUtils.isEmpty(jiekuanyongtuStr)) {
            ArmsUtils.snackbarText("请选择借款用途");
            return false;
        }
        if (TextUtils.isEmpty(mLilv)) {
            ArmsUtils.snackbarText("请选择年利率");
            return false;
        }
        return true;
    }

    BankPayDialog mBankPayDialog;

    private boolean checkPayStatus(String noteid) {
        //留给支付 后再调用生成借条
            mBankPayDialog = new BankPayDialog(this, noteid);
        switch (mType) {
            case JIEKUANREN://借款人
                //我是借款人  借款人支付
                mBankPayDialog.show();

                break;
            case CHUJIEREN://出借人
                if (!hasJiekuanrenZhifu) {
                    //我是出借人  出借人支付
                    mBankPayDialog.show();
                } else {
//                    ArmsUtils.snackbarText("请将二维码分享给借款人");
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "二维码分享");
                    bundle1.putInt(WebViewActivity.USER_TYPE, mType);
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + mNoteid + "&userType=" + mType);
                    startActBundle(bundle1, WebViewShareActivity.class);
                    finish();
                }
                break;
        }

        return false;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBankDismiss(BankDismissTag tag){
        findViewById(R.id.xiejietiao_btn).setEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuc(PaySuccessTag tag) {
        switch (tag.getType()) {
            case PaySuccessTag.PAY_SUCCESS:
//                ArmsUtils.snackbarText("支付成功");
                switch (mType) {

                    case JIEKUANREN://借款人
                        //我是借款人  借款人支付
                        authSign();
                        break;
                    default:
                        h5SharePage();
                        finish();
                        break;
                }
//                h5SharePage();
//                finish();
                break;
        }
    }

    /**
     * 我是借款人，然后支付.手动签章流程
     */
    private void authSign(){
        //手动签章为了防止 过快后台文件未准备好
        Observable.timer(2000,TimeUnit.MILLISECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading("正在准备签章信息...");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mPresenter.authSign(mNoteid,SavePreference.getStr(XieJietiaoActivity.this, PingtiaoConst.USER_NAME),WebViewSignActivity.XIE_JIETIAO_RETURNURL);
                    }
                }).isDisposed();
    }

    private void h5SharePage(){
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "分享");
        bundle1.putInt(WebViewShareActivity.USER_TYPE, mType);
        bundle1.putInt(WebViewShareActivity.NOTE_ID,Integer.valueOf(mNoteid));
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + mNoteid + "&userType=" + mType);
        startActBundle(bundle1, WebViewShareActivity.class);
    }


    private void createJietiao(){
        String amount = "";//
        String borrower = "";//X
        String lender = "";//X
        String loanDate = "";//
        String repaymentDate = "";//
        String payer = "";//X
        String loanUsage = "";//
        String userType = "";
        String yearRate = "";//
        String identityNo = "";//


        amount = xiejietiao_jiekuanjine_edit.getText().toString();
        loanDate = mCalendar1.get(Calendar.YEAR) + "-" + (mCalendar1.get(Calendar.MONTH) + 1) + "-" + mCalendar1.get(Calendar.DAY_OF_MONTH);
        repaymentDate = mCalendar2.get(Calendar.YEAR) + "-" + (mCalendar2.get(Calendar.MONTH) + 1) + "-" + mCalendar2.get(Calendar.DAY_OF_MONTH);
        yearRate = mLilv;

        if (findViewById(R.id.jiekuanyongtu_qita).getVisibility() == View.GONE) {
            loanUsage = jiekuanyongtuStr;
        } else {
            loanUsage = xiejietiao_jiekuanyongtutianxie.getText().toString();
        }
        identityNo = xiejietiao_chujierenshengfengzhenghao.getText().toString();
        switch (mType) {
            case JIEKUANREN://我是借款人
                borrower = SavePreference.getStr(this, PingtiaoConst.USER_NAME);
                lender = xiejietiao_chujierenxingming.getText().toString();
                payer = "0";
                userType = "0";
                break;
            case CHUJIEREN://我是出借人
                lender = SavePreference.getStr(this, PingtiaoConst.USER_NAME);
                borrower = xiejietiao_chujierenxingming.getText().toString();
                payer = hasJiekuanrenZhifu ? "0" : "1";
                userType = "1";
                break;
        }
        mPresenter.addJietiao(
                amount,
                borrower,
                lender,
                loanDate,
                repaymentDate,
                payer,
                loanUsage,
                userType,
                yearRate,
                identityNo
        );
    }

    private boolean hasJiekuanrenZhifu = false;


    @BindView(R.id.jiekuanrenzhifu)
    ImageView jiekuanrenzhifu;
    @BindView(R.id.chujierenzhifu)
    ImageView chujierenzhifu;

    /**
     * 获取利润
     *
     * @return 利息=本金*年化利率*（借款天数/365），保留2位小数，四舍五入
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private float getLirun() {
        if (TextUtils.isEmpty(xiejietiao_jiekuanjine_edit.getText())) {
            return 0;
        }
        String st1 = xiejietiao_jiekuanjine_edit.getText().toString();
        long benjin = Long.valueOf(st1);
        mBenjin = benjin;

        if (TextUtils.isEmpty(mLilv)) {
            return 0;
        }

        float nianhualilv = Float.valueOf(mLilv.substring(0, mLilv.length() - 1)) / 100f;
        Calendar calendarjiekuan = Calendar.getInstance();
        Calendar calendarhuankuan = Calendar.getInstance();
        calendarjiekuan.setTime(jiekuanriqi);
        calendarhuankuan.setTime(huankuanriqi);
        if (calendarhuankuan.getTimeInMillis() <= calendarjiekuan.getTimeInMillis()) {
            return 0;
        }
        long day = (calendarhuankuan.getTimeInMillis() - calendarjiekuan.getTimeInMillis()) / (1000 * 3600 * 24);
        float lirun = benjin * nianhualilv * (day / 365f);
        mLixi = lirun;
        return (float) (lirun);
    }

    private float mBenjin;
    private float mLixi;
    private float mBenxihe;

    private void jisuanRiqi() {
        if (!TextUtils.isEmpty(xiejietiao_jiekuanriqi_tv.getText().toString()) &&
                !TextUtils.isEmpty(xiejietiao_huankuanriqi_edit.getText().toString())) {
            xiejietiao_huankuanriqi_edit.setText(
                    mCalendar2.get(Calendar.YEAR) + "-"
                            + (mCalendar2.get(Calendar.MONTH) + 1) + "-"
                            + mCalendar2.get(Calendar.DAY_OF_MONTH)
                            + "  " + ((huankuanriqi.getTime() - jiekuanriqi.getTime()) / (24 * 60 * 60 * 1000)) + "天"
            );
        }
    }

    /**
     * 本息合计提醒兰
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void jisuanBenxi() {

        if (TextUtils.isEmpty(xiejietiao_jiekuanjine_edit.getText())) {
            return;
        }
        String st1 = xiejietiao_jiekuanjine_edit.getText().toString();
        long benjin = Long.valueOf(st1);


        if (TextUtils.isEmpty(mLilv)) {
            return;
        }

        float nianhualilv = Float.valueOf(mLilv.substring(0, mLilv.length() - 1)) / 100f;
        Calendar calendarjiekuan = Calendar.getInstance();
        Calendar calendarhuankuan = Calendar.getInstance();
        calendarjiekuan.setTime(jiekuanriqi);
        calendarhuankuan.setTime(huankuanriqi);
        if (calendarhuankuan.getTimeInMillis() <= calendarjiekuan.getTimeInMillis()) {
            return;
        }
        long day = (calendarhuankuan.getTimeInMillis() - calendarjiekuan.getTimeInMillis()) / (1000 * 3600 * 24);
        float lirun = benjin * nianhualilv * (day / 365f);
        float benxi = benjin + lirun;
        mBenjin = benjin;
        mBenxihe = benxi;
        xiejietiao_benxi_hint.setText(
                "*本金" + benjin + "+利息" + decimalFormat.format(lirun) + "=到期本息" + decimalFormat.format(benxi) + "元"
        );
        xiejietiao_nianhualilv_edit.setText(
                mLilv + " " +
                        decimalFormat.format(lirun) + "元"
        );
    }


    @Override
    public void onBackPressed() {
        if (mDialogChooseNormal == null || !isShow) {
            backDialog();
        } else {
            super.onBackPressed();
        }
    }

    DialogChooseNormal mDialogChooseNormal;
    private boolean isShow = false;

    private void backDialog() {
        isShow = true;
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(this)
                    .setContent("退回将清空当前页面所有内容")
                    .setBtn1Content("清空").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("取消")
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                            isShow = false;
                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    private String mNoteid;

    @Override
    public void sucAddJietiao(String noteId) {
//        ArmsUtils.snackbarText("成功生成借条");
        mNoteid = noteId;
        Log.d("dodoJie","noteId = "+noteId);
        checkPayStatus(noteId);
//        finish();
    }

    @Override
    public void sucAuthSign(String url) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "手动签章");
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, url);
        bundle1.putString(WebViewSignActivity.NOTE_ID,mNoteid);
        bundle1.putInt(WebViewSignActivity.USER_TYPE,mType);
        startActBundle(bundle1, WebViewSignActivity.class);
        finish();
    }

    @BindView(R.id.xianshimianfei)
    TextView xianshimianfei;

    private void getPrice() {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PayApi.class).productPrice(new ProductPriceRequest())
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
                            if (money <= 0) {
                                xianshimianfei.setVisibility(View.VISIBLE);
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
}
