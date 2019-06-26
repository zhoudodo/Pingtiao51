package com.pingtiao51.armsmodule.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;
import com.pingtiao51.armsmodule.mvp.model.NewInfoModel;
import com.pingtiao51.armsmodule.mvp.model.entity.qualifier.NewsList;
import com.pingtiao51.armsmodule.mvp.model.entity.response.pojospeical.NewsListInterface;
import com.pingtiao51.armsmodule.mvp.ui.adapter.HomeNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class NewInfoModule {

    @Binds
    abstract NewInfoContract.Model bindNewInfoModel(NewInfoModel model);


    @FragmentScope
    @Provides
    static HomeNewsAdapter provideHomeNewsAdapter(@NewsList  List<NewsListInterface> list){
        return new HomeNewsAdapter(R.layout.item_news_layout,list);
    }

    @FragmentScope
    @Provides
    @NewsList
    static List<NewsListInterface> provideNewsList(){
        return new ArrayList<>();
    }


}