package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.jess.arms.di.component.AppComponent;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerSecureCopyComponent;
import com.pingtiao51.armsmodule.mvp.contract.SecureCopyContract;
import com.pingtiao51.armsmodule.mvp.presenter.SecureCopyPresenter;
import butterknife.OnClick;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SecureCopyActivity extends BaseArmsActivity<SecureCopyPresenter> implements SecureCopyContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSecureCopyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_secure_copy; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
    @OnClick({R.id.zhizhijietiao,R.id.zhizhishoutiao})
    public void onPageClick(View view){
        switch (view.getId()){
            case R.id.zhizhijietiao:
                //TODO 纸质 借条
                startAct(ZhizhiJietiaoMainActivity.class);
                break;
            case R.id.zhizhishoutiao:
                //TODO 纸质收条
                startAct(ZhizhiShoutiaoMainActivity.class);
                break;
        }
    }
}
