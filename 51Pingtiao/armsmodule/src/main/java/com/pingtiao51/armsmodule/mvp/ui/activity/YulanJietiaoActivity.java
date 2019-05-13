package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerYulanJietiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.YulanJietiaoContract;
import com.pingtiao51.armsmodule.mvp.presenter.YulanJietiaoPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.HorSpaceTextView;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.ConvertMoneyToUppercase;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 16:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 预览借条
 */
public class YulanJietiaoActivity extends BaseArmsActivity<YulanJietiaoPresenter> implements YulanJietiaoContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerYulanJietiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    private int mType;//出借人预览 还是借款人预览

    public final static String jiekuanjine = "jiekuanjine";
    public final static String jiekuanriqi = "jiekuanriqi";
    public final static String jiekuanyongtu = "jiekuanyongtu";
    public final static String huankuanriqi = "huankuanriqi";
    public final static String benxihe = "benxihe";
    public final static String nianhualilv = "nianhualilv";
    public final static String lixizongji = "lixizongji";
    public final static String chujierenmingzi = "chujieren";
    public final static String chujierenshenfenzheng = "chujierenshenfenzheng";
    public final static String jietiaochujushijian = "jietiaochujushijian";
    public final static String tianshu = "tianshu";


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_yulan_jietiao; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("借条预览");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        findViewById(R.id.toolbar).setBackground(getResources().getDrawable(R.color.transparent));
        Bundle bundle = getIntent().getExtras();
        String jiekuanyongtuVal = bundle.getString(YulanJietiaoActivity.jiekuanyongtu, "--");
        String tianshuVal = bundle.getString(YulanJietiaoActivity.tianshu, "--");
        String jiekuanjineVal = bundle.getString(YulanJietiaoActivity.jiekuanjine, "0");
        jiekuanjineVal = setNull2Zero(jiekuanjineVal);
        jiekuanjineVal = decimalFormat.format(Double.valueOf(jiekuanjineVal));

        String jiekuanriqiVal = bundle.getString(YulanJietiaoActivity.jiekuanriqi, "--");
        String huankuanriqiVal = bundle.getString(YulanJietiaoActivity.huankuanriqi, "--");
        String benxiheVal = bundle.getString(YulanJietiaoActivity.benxihe, "0");
        benxiheVal = setNull2Zero(benxiheVal);
        benxiheVal = decimalFormat.format(Double.valueOf(benxiheVal));
        String nianhualilvVal = bundle.getString(YulanJietiaoActivity.nianhualilv, "0");
        nianhualilvVal = setNull2Zero(nianhualilvVal);
        String lixizongjiVal = bundle.getString(YulanJietiaoActivity.lixizongji, "0");
        lixizongjiVal = setNull2Zero(lixizongjiVal);
        lixizongjiVal = decimalFormat.format(Double.valueOf(lixizongjiVal));
        String chujierenmingziVal = bundle.getString(YulanJietiaoActivity.chujierenmingzi, "--");
        String chujierenshenfenzhengVal = bundle.getString(YulanJietiaoActivity.chujierenshenfenzheng, "--");
        String userNameVal = SavePreference.getStr(this, PingtiaoConst.USER_NAME);
        String userIdCardVal = SavePreference.getStr(this, PingtiaoConst.USER_ID_CARD);
        mType = bundle.getInt(XieJietiaoActivity.XieJietiaoActivity, XieJietiaoActivity.JIEKUANREN);//默认借款人

        switch (mType) {
            case XieJietiaoActivity.CHUJIEREN:
                //换名字
                String temp = userNameVal;
                userNameVal = chujierenmingziVal;
                chujierenmingziVal = temp;
                //换身份证号
                temp = userIdCardVal;
                userIdCardVal = chujierenshenfenzhengVal;
                chujierenshenfenzhengVal = temp;

                break;
            case XieJietiaoActivity.JIEKUANREN:
                break;
        }

        SpannableStringBuilder jiekuanreninfos = new SpanUtils()
                .append("借款人").setFontSize(12, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + userNameVal
//                        + "(" + userIdCardVal + ")"
                ).setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .setUnderline()
                .create();

        jiekuanren_xingming_shenfenzheng.setText(jiekuanreninfos);

        SpannableStringBuilder chujiereninfos = new SpanUtils()
                .append("出借人").setFontSize(12, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + chujierenmingziVal
//                        + "(" + chujierenshenfenzhengVal + ")"
                ).setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .setUnderline()
                .create();

        chujieren_xingming_shenfenzheng.setText(chujiereninfos);


        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DecimalFormat decimalFormat1 = new DecimalFormat("00");
        String day = decimalFormat1.format(calendar.get(Calendar.DAY_OF_MONTH));
        String month = decimalFormat1.format((calendar.get(Calendar.MONTH) + 1));
        SpannableStringBuilder chujieshijian = new SpanUtils()
                .append("借条出具时间").setFontSize(12, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + calendar.get(Calendar.YEAR) + "/" + month + "/" + day).setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .setUnderline()
                .create();

        chujiejushijian.setText(chujieshijian);


        SpannableStringBuilder ssbContent = new SpanUtils()
                .append(" " + userNameVal + " ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("因").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append("  " + jiekuanyongtuVal + "  ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("向").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))

                .append(" " + chujierenmingziVal + " ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("今借到（人民币）").setFontSize(13, true).setForegroundColor(getResources()
//                        .getColor(R.color.red_color_ED4641)
                        .getColor(R.color.black_color_333333)
                )
                .append(" " + "¥" + jiekuanjineVal + "(" + ConvertMoneyToUppercase.convertMoney(Double.valueOf(jiekuanjineVal)) + ")" + " ")
                .setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("，借款期限自").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append("  " + jiekuanriqiVal + "  ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("起至").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append("  " + huankuanriqiVal + "  ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("止，共").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + tianshuVal + " ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("天，年利率为").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + nianhualilvVal + " ").setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append(",利息共计（人民币）").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + "¥" + lixizongjiVal + "(" + ConvertMoneyToUppercase.convertMoney(Double.valueOf(lixizongjiVal)) + ")" + " ")
                .setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append(",到期后连本带息").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" " + "¥" + benxiheVal + "(" + ConvertMoneyToUppercase.convertMoney(Double.valueOf(benxiheVal)) + ")" + " ")
                .setFontSize(15, true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("偿还。如到期未还，本人愿承担对方所支付的律师费、交通费等其他费用。以下无正文。")
                .setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .create();
        content.setText(ssbContent);


    }

    private String setNull2Zero(String nullStr){
        return TextUtils.isEmpty(nullStr) ? "0":nullStr;
    }

    @BindView(R.id.jiekuanren_xingming_shenfenzheng)
    TextView jiekuanren_xingming_shenfenzheng;
    @BindView(R.id.chujieren_xingming_shenfenzheng)
    TextView chujieren_xingming_shenfenzheng;
    @BindView(R.id.chujiejushijian)
    TextView chujiejushijian;
    @BindView(R.id.content)
    TextView content;
}
