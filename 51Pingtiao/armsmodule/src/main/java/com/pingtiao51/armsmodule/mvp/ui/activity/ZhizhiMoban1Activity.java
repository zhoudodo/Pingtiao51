package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.pingtiao51.armsmodule.R;

@Deprecated
public class ZhizhiMoban1Activity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhizhi_moban1_layout);
        setTitle("模板");
        findViewById(R.id.jietiao_moban1_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(JietiaoMobanVpActivity.class);
            }
        });
        findViewById(R.id.shoutiao_moban1_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(ShoutiaoMobanVpActivityActivity.class);
            }
        });
    }
}
