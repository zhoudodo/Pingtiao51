package com.pingtiao51.armsmodule.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import io.reactivex.Observable;
import retrofit2.http.Body;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AddBankCardContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
            void onSucAddCard();
            void onSucSendCode();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 绑卡
         * @param appVersion
         * @param bankcardNo
         * @param identityNo
         * @param name
         * @param os
         * @param phone
         * @param vcode
         * @return
         */
        Observable<BaseJson<Object>> bindBankCard(
                String appVersion,
                String bankcardNo,
                String identityNo,
                String name,
                String os,
                String phone,
                String vcode
        );
        /**
         * 发送短信验证码
         */
        Observable<BaseJson<Object>> sendPhoneCode(String phoneNum,String type);
    }
}
