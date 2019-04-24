package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewPagerActivity extends FragmentActivity {

    public final static String TAG = PhotoViewPagerActivity.class.getSimpleName();
    public final static String POSITION = "position";
    public final static String TITLE = "title";
    private ViewPager mPager;

//    private int[] imgsId = new int[]{R.mipmap.aaa, R.mipmap.bbb, R.mipmap.ccc, R.mipmap.ddd, R.mipmap.ic_launcher, R.mipmap.image003};

    private List<String> mDatas = new ArrayList<>();
    private int pos = 0;
    TextView current;
    private String title = "图片";

    public final static String URL_TYPE = "URL_TYPE";
    public final static int NETWORK_URL = 0;//网络图
    public final static int LOCAL_RES = 1;//本地res
    private int urlType = NETWORK_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_pager);
        mDatas = getIntent().getStringArrayListExtra(TAG);
        pos = getIntent().getIntExtra(POSITION, 0);
        title = getIntent().getStringExtra(TITLE);
        urlType = getIntent().getIntExtra(URL_TYPE, NETWORK_URL);
        if (TextUtils.isEmpty(title)) {
            title = "图片";
        }
        setTitle(title);
        current = findViewById(R.id.current);
        current.setText((pos + 1) + "/" + mDatas.size());
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(PhotoViewPagerActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                view.setImageResource(imgsId[position]);
                String item = mDatas.get(position);
                if (UrlEncoderUtils.hasUrlEncoded(item)) {
                    try {
                        item = URLDecoder.decode(item, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                switch (urlType) {
                    case NETWORK_URL:
                        GlideProxyHelper.loadImgForUrl(view, item);
                        break;
                    case LOCAL_RES:
                        GlideProxyHelper.loadImgForRes(view, Integer.valueOf(item));
                        break;
                    default:
                        GlideProxyHelper.loadImgForUrl(view, item);
                        break;
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current.setText((i + 1) + "/" + mDatas.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mPager.setCurrentItem(pos);
    }
}
