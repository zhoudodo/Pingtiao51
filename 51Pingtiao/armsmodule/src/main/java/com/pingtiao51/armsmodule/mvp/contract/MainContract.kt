package com.pingtiao51.armsmodule.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson
import com.pingtiao51.armsmodule.mvp.model.entity.response.CheckUpdateResponse
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2019 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun  checkUpdate(data:CheckUpdateResponse?)
        fun  downApkSuc(path:String?)
        fun  downPercent(percent:Int?)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun  checkUpdate(): Observable<BaseJson<CheckUpdateResponse>?>
         fun downloadFile(url: String): Observable<ResponseBody>
    }

}
