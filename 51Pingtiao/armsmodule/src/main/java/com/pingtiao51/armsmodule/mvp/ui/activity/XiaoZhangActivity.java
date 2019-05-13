package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.di.component.DaggerXiaoZhangComponent;
import com.pingtiao51.armsmodule.mvp.contract.XiaoZhangContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.EditRepaymentRecordRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;
import com.pingtiao51.armsmodule.mvp.presenter.XiaoZhangPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ComSingleWheelDialog;


import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 15:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class XiaoZhangActivity extends BaseArmsActivity<XiaoZhangPresenter> implements XiaoZhangContract.View {

    public final static String BORROW = "borrow";
    public final static String LENDER = "lender";
    public final static String AMOUNT = "amount";
    public final static String NOTEID = "noteid";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerXiaoZhangComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_xiao_zhang; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.xiaozhangjine)
    TextView xiaozhangjine;
    @BindView(R.id.qingxuanze)
    TextView qingxuanze;
    @BindView(R.id.xiaozhang_sure1_btn)
    TextView xiaozhang_sure1_btn;
    @BindView(R.id.xiaozhangyuanyin_xuanze)
    RelativeLayout xiaozhangyuanyin_xuanze;

    private String sendReason = "";
    private String borrow = "";
    private String lender = "";
    private int noteid;
    private double amount;
    private String operationType = "CANCLE";//确认 CONFIRM 驳回REJECT 销账CANCLE

    ComSingleWheelDialog mComSingleWheelDialog;

    @OnClick({R.id.xiaozhang_sure1_btn, R.id.xiaozhangyuanyin_xuanze})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.xiaozhang_sure1_btn:
                //TODO 销账
                sendXiaozhang();
                break;
            case R.id.xiaozhangyuanyin_xuanze:
                //TODO 销账原因
                if (mComSingleWheelDialog == null) {
                    List<String> datas = Arrays.asList(getResources().getStringArray(R.array.xiaozhang_yuanyin));
                    List<String> dataSend = Arrays.asList(getResources().getStringArray(R.array.xiaozhang_yuanyin_send));
                    mComSingleWheelDialog = new ComSingleWheelDialog(this, datas, "销账原因");
                    mComSingleWheelDialog.setComSingleWheelInterface(new ComSingleWheelDialog.ComSingleWheelInterface() {
                        @Override
                        public void getChoiceStr(String str) {
                            qingxuanze.setText(str);
                            for (int i = 0; i < datas.size(); i++) {
                                String temp = datas.get(i);
                                if (temp.equals(str)) {
                                    sendReason = dataSend.get(i);
                                }
                            }
                        }
                    });
                }
                mComSingleWheelDialog.show();
                break;
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        borrow = getIntent().getStringExtra(BORROW);
        lender = getIntent().getStringExtra(LENDER);
        amount = getIntent().getDoubleExtra(AMOUNT, 0);
        noteid = getIntent().getIntExtra(NOTEID, 0);
        setTitle("销账");
        String money = decimalFormat.format(amount);
        xiaozhangjine.setText(money);
    }

    /**
     * 销账
     */
    private void sendXiaozhang() {
        if (TextUtils.isEmpty(sendReason)) {
            ArmsUtils.snackbarText("请选择销账原因");
            return;
        }
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class).editRepaymentRecord(new EditRepaymentRecordRequest(
                amount,
                AppUtils.getAppVersionName(),
                sendReason,
                noteid,
                operationType,
                "ANDRIOD"
//                lender,
//                borrow
        )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle((Lifecycleable) this))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if (rep.isSuccess()) {
                            //成功销账
                            ArmsUtils.snackbarText("销账成功");
                            startToXiangqing();
                            finish();
                        } else {
                            ArmsUtils.snackbarText(rep.getMessage());
                        }
                    }
                });
    }

    //跳转至详情界面
    private void startToXiangqing() {
        ActivityUtils.finishActivity(DianziJietiaoXiangqingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(PING_TIAO_XIANG_QING, noteid);
        startActBundle(bundle, DianziJietiaoXiangqingActivity.class);
    }
}
