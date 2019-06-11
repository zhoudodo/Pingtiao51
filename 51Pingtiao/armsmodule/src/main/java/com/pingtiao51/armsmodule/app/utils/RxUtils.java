package com.pingtiao51.armsmodule.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================
 * 放置便于使用 RxJava 的一些工具方法
 * <p>
 * Created by MVPArmsTemplate on 03/02/2019 21:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RxUtils {

    private RxUtils() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoading();//显示进度条
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() {
                                view.hideLoading();//隐藏进度条
                            }
                        }).compose(RxLifecycleUtils.bindToLifecycle(view));
            }
        };
    }
}
//InSplashActivity, 启动页进入启动页, 1
//woyaobeifenpingtiao, 备份点击我要备份凭条, 1
//InMainActivity, 首页进入首页, 1
//woyaoxiepingtiao, 首页点击我要写凭条, 1
//InCreateJietiaoActivity, 新建页进入新建页, 1
//OutCreateJietiaoActivity, 新建页点击返回, 1
//InCreateDianziJietiaoActivity, 新建页点击我要写借条, 1
//InCreateDianziShoutiaoActivity, 新建页点击我要写收条, 1
//OutCreateDianziJietiaoActivity, 新建电子借条页点击返回, 1
//woshijiekuanren, 新建电子借条页点击我是借款人, 1
//woshichujieren, 新建电子借条页点击我是出借人, 1
//OutCreateDianziShoutiaoActivity, 新建电子收条点击返回, 1
//xieshoutiao, 新建电子收条点击写收条, 1
//OutXieJietiaoActivity, 写电子借条点击返回, 1
//dianzijietiaoyulan, 写电子借条点击预览, 1
//shengchengjietiao, 写电子借条点击生成借条, 1
//fasongduifang, 借条分享页点击发送或发送对方, 1
//fenxiangweixin, 借条分享页选择分享至微信好友, 1
//fenxiangerweima, 借条分享页选择二维码分享, 1
//fenxianglianjie, 借条分享页选择复制链接, 1
//fenxiangduanxin, 借条分享页选择发送短信, 1
//webviewfanhuishouye, 借条分享页点击返回首页, 1
//webviewchakanjietiao, 借条分享页点击查看借条, 1
//OutWebViewShareActivity, 借条分享页点击左上角返回, 1
//InXieShoutiaoActivity, 写电子收条进入写电子收条页, 1
//dianzishoutiaoluyan, 写电子收条点击预览, 1
//shengchengshoutiao, 写电子收条点击生成收条, 1
//InShimingrenzhengActivity, 实名认证页进入实名认证页, 1
//shimingrenzhengtijiao, 实名认证页点击提交, 1
//shouyeleft, 首页点击左上角, 1
//shouyexiaoxi, 首页点击消息, 1
//shouyexinshoubangzhu, 首页点击新手帮助, 1
//InLoginActivity, 登录页进入登录页, 1
//codehuoquyanzhenma, 登录页点击验证码登录的获取验证码, 1
//mimadenglu, 登录页点击密码登录的登录, 1
//yanzhengmadenglu, 登录页点击验证码登录的登录, 1
//codewangjimima, 登录页点击忘记密码, 1
//psdhuoquyanzhenma, 登录页点击忘记密码的获取验证码, 1
//wangjimimadenglu, 登录页点击忘记密码的登录, 1
//yanzhengmafanhui, 登录页点击验证码登录的返回, 1
//chanxinyong, 首页点击-查信用, 1
//qiujiekuan, 首页点击求借款, 1
//jietiaomoban, 首页点击借条模板, 1
//shoutiaomoban, 首页点击收条模板, 1
//pingtiaoleixing, 首页点击选择凭条类型小三角那块, 1
//InZhizhiJietiaoMainActivity, 备份凭条页点击纸质借条, 1
//InZhizhiShoutiaoMainActivity, 备份凭条页点击纸质收条, 1
//OutSecureCopyActivity, 备份凭条页点击返回, 1
//InJietiaoMobanVpActivity, 备份借条页点击模板, 1
//OutZhizhiJietiaoMainActivity, 备份借条页点击返回, 1
//beifenjietiaosave, 备份借条页点击保存, 1
//InShoutiaoMobanVpActivityActivity, 备份收条页点击模板, 1
//OutZhizhiShoutiaoMainActivity, 备份收条页点击返回, 1
//beifenshoutiaosave, 备份收条页点击保存, 1
