package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.jess.arms.di.component.AppComponent;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerYulanShoutiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.YulanShoutiaoContract;
import com.pingtiao51.armsmodule.mvp.presenter.YulanShoutiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.ConvertMoneyToUppercase;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;

import butterknife.BindView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 预览收条
 */
public class YulanShoutiaoActivity extends BaseArmsActivity<YulanShoutiaoPresenter> implements YulanShoutiaoContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerYulanShoutiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public final static String jingshourenxingming = "jingshourenxingming";//经手人姓名
    public final static String dijiaorenxingming = "dijiaorenxingming";//递交人姓名
    public final static String hejijine = "hejijine";//合计金额
    public final static String jingshourenshenfenzheng = "jingshourenshenfenzheng";//经手人身份证
    public final static String shoutiaochujushijian = "shoutiaochujushijian";//收条出具时间

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_yulan_shoutiao; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTitle();

        Bundle recBundle = getIntent().getExtras();
        String shoutiaochujushijianVal =  recBundle.getString(shoutiaochujushijian,"--");
        String dijiaorenxingmingVal = recBundle.getString(dijiaorenxingming,"--");
        String hejijineVal = recBundle.getString(hejijine,"0");
        String userName = SavePreference.getStr(this,PingtiaoConst.USER_NAME);
        String userIdCard = SavePreference.getStr(this,PingtiaoConst.USER_ID_CARD);
        SpannableStringBuilder chujiereninfos = new SpanUtils()
                .append("经手人").setFontSize(12,true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" "+userName+"("+userIdCard+")").setFontSize(15,true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .setUnderline()
                .create();

        jingshouren_xingming_shenfenzheng.setText(chujiereninfos);


        SpannableStringBuilder chujieshijian = new SpanUtils()
                .append("经手时间").setFontSize(12,true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" "+shoutiaochujushijianVal+ " ").setFontSize(15,true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .setUnderline()
                .create();

        chushoujushijian.setText(chujieshijian);
        String bigMoneyStr = ConvertMoneyToUppercase.convertMoney(Double.valueOf(hejijineVal));
        SpannableStringBuilder ssbContent = new SpanUtils()
                .append(" "+userName + " ").setFontSize(15,true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("今收到").setFontSize(13,true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append("  "+dijiaorenxingmingVal+"  ").setFontSize(15,true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("归还的借款，合计").setFontSize(13,true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .append(" "+"¥"+hejijineVal +"元("+bigMoneyStr +")"+" ").setFontSize(15,true).setForegroundColor(getResources().getColor(R.color.black_color_333333)).setUnderline()
                .append("以下无正文。")
                .setFontSize(13,true).setForegroundColor(getResources().getColor(R.color.gray_color_969696))
                .create();
        content.setText(ssbContent);

    }

    @BindView(R.id.jingshouren_xingming_shenfenzheng)
    TextView jingshouren_xingming_shenfenzheng;

    @BindView(R.id.chushoujushijian)
    TextView chushoujushijian;

    @BindView(R.id.content)
    TextView content;

    private void initTitle(){
        setTitle("收条预览");
        findViewById(R.id.toolbar).setBackground(getResources().getDrawable(R.color.transparent));
    }

}
