package com.pingtiao51.armsmodule.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 12:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface XieShoutiaoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
            public void onSucAddDianziShoutiao(String noteid);
            public void onSucAuthSign(String url);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<String>> addDianziShoutiaotiao(String amount,
                                                           String borrower,
                                                           String comment,
                                                           String lender,
                                                           String repaymentDate,
                                                           List<String> urls
                                                           );
        Observable<BaseJson<Object>> extSign(String noteid,String name,String returnUrl);
    }
}
