package com.pingtiao51.armsmodule.mvp.ui.helper;



import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;

import java.util.ArrayList;
import java.util.List;

public class BannerHelper {

    private final static int turnTime = 6000;

    //初始化 banner
    public static void initBanner(final ConvenientBanner bannerPoster, List<BannerParentInterface> bannerList) {

        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        final List<BannerParentInterface> banners = bannerList;
//        bannerPoster.setCanLoop(true);//能否循环
        bannerPoster.setPages(
                //接口对象  加载图片
                new CBViewHolderCreator<BannerImageHolderViewHelper>() {
                    @Override
                    public BannerImageHolderViewHelper createHolder() {
                        return new BannerImageHolderViewHelper();
                    }
                }, banners)
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        // .setOnPageChangeListener(this)//监听翻页事件
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。

        if (banners.size() > 1) {
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//            bannerPoster.setPageIndicator(new int[]{R.drawable.icon_page_indicator_normal, R.drawable.icon_page_indicator_press});
            bannerPoster.setPageIndicator(new int[]{R.drawable.icon_page_indicator_normal1, R.drawable.icon_page_indicator_press1});
            bannerPoster.startTurning(turnTime);
            bannerPoster.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
            bannerPoster.setManualPageable(true);//设置不能手动影响
        } else {
            bannerPoster.setManualPageable(false);//设置不能手动影响
        }
    }




    public static void controlBannerScroll(boolean visible, ConvenientBanner bannerView) {
        if (bannerView == null || bannerView.getViewPager().getAdapter() == null)
            return;
        if (bannerView.getViewPager().getAdapter().getCount() <= 1)
            return;
        if (visible) {
            bannerView.startTurning(turnTime);
        } else {
            bannerView.stopTurning();
        }
    }

}