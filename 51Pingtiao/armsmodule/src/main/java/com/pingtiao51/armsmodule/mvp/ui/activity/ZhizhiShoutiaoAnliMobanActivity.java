package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.pingtiao51.armsmodule.R;

import java.util.ArrayList;

import butterknife.OnClick;

public class ZhizhiShoutiaoAnliMobanActivity  extends FragmentActivity {

    public final static String TAG = "ZhizhiShoutiaoAnliMobanActivity";

    public final static int ZHIZHI_JIETIAO = 0;
    public final static int ZHIZHI_SHOUTIAO = 1;

    private int mType = ZHIZHI_JIETIAO;
    ArrayList<String> shows = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mType = getIntent().getIntExtra(TAG,ZHIZHI_JIETIAO);
         shows.clear();
        switch (mType){
            case ZHIZHI_JIETIAO:
                setContentView(R.layout.activity_zhizhi_moban);
                setTitle("纸质借条案例");
                shows.add(R.drawable.zhizhijietiao_yulan_tu+"");
                break;
            case ZHIZHI_SHOUTIAO:
                setContentView(R.layout.activity_zhizhi_moban_shoutiao);
                setTitle("纸质收条案例");
                shows.add(R.drawable.zhizhishoutiao_yulan_tu+"");
                break;
        }
        findViewById(R.id.moban_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewPagerActivity.TAG, (ArrayList<String>) shows);
                bundle.putString(PhotoViewPagerActivity.TITLE, "图片");
                bundle.putInt(PhotoViewPagerActivity.POSITION, 0);
                bundle.putInt(PhotoViewPagerActivity.URL_TYPE, PhotoViewPagerActivity.LOCAL_RES);
                ActivityUtils.startActivity(bundle, PhotoViewPagerActivity.class);
            }
        });
    }

}
