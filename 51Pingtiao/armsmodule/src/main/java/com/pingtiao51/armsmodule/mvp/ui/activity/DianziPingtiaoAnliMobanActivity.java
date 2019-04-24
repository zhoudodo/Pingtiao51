package com.pingtiao51.armsmodule.mvp.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.pingtiao51.armsmodule.R;

/**
 * 电子凭条案例模板
 */
public class DianziPingtiaoAnliMobanActivity extends FragmentActivity {

    public final static String TAG = "DianziPingtiaoAnliMobanActivity";

    public final static int DIANZI_JIETIAO = 0;
    public final static int DIANZI_SHOUTIAO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(TAG,DIANZI_JIETIAO);
        switch (type){
            case DIANZI_JIETIAO:
                setContentView(R.layout.activity_dianzi_jietiao_anlimoban);
                setTitle("电子借条案例");
                findViewById(R.id.title)
                        .setBackground(getResources().getDrawable(R.color.transparent));
                break;
            case DIANZI_SHOUTIAO:
                setContentView(R.layout.activity_dianzi_shoutiao_anlimoban);
                setTitle("电子收条案例");
                findViewById(R.id.title)
                        .setBackground(getResources().getDrawable(R.color.transparent));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
