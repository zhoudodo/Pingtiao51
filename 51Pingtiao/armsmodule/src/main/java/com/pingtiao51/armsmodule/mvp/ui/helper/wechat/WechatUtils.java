package com.pingtiao51.armsmodule.mvp.ui.helper.wechat;

import android.content.Context;
import android.graphics.Bitmap;

import com.authreal.util.ToastUtil;
import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.img.ImageUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class WechatUtils {

    /**
     * 分享网页类型至微信
     *
     * @param context 上下文
     * @param appId   微信的appId
     * @param webUrl  网页的url
     * @param title   网页标题
     * @param content 网页描述
     * @param bitmap  位图
     */
    public static void shareWeb(
            Context context,
            String appId,
            String webUrl,
            String title,
            String content,
            Bitmap bitmap
    ) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, appId);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ArmsUtils.snackbarText("您还没有安装微信");
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = webUrl;

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = title;
        msg.description = content;
        // 如果没有位图，可以传null，会显示默认的图片
        //图片必须小于32KB
        msg.setThumbImage(cropShareImage(bitmap));

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）
        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
        req.message = msg;
        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
        req.scene = SendMessageToWX.Req.WXSceneSession;

        // 向微信发送请求
        wxapi.sendReq(req);
    }


    public static Bitmap cropShareImage(Bitmap bp) {
        if(bp == null){
            return null;
        }
        return ImageUtils.getCompressImage(bp, 32,
                AutoSizeUtils.dp2px(ActivityUtils.getTopActivity(), 40),
                AutoSizeUtils.dp2px(ActivityUtils.getTopActivity(), 40));
    }


    /**
     * 微信支付
     */
    public static void payWeChat(
            Context context,
            String appId,
            String partnerId,
            String prepayId,
            String nonceStr,
            String timeStamp,
            String sign

    ){
        IWXAPI api= WXAPIFactory.createWXAPI(context, appId,false);//填写自己的APPID
        api.registerApp(appId);//填写自己的APPID，注册本身APP
        PayReq req = new PayReq();//PayReq就是订单信息对象
//给req对象赋值
        req.appId = appId;//APPID
        req.partnerId = partnerId;//    商户号
        req.prepayId = prepayId;//  预付款ID
        req.nonceStr = nonceStr;//随机数
        req.timeStamp = timeStamp;//时间戳
        req.packageValue = "Sign=WXPay";//固定值Sign=WXPay
        req.sign = sign;//签名

        api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
    }

    /**
     * 微信登录
     * @param context
     * @param appId
     */
    public static void loginWx(
            Context context,
            String appId
    ) {
        IWXAPI  api = WXAPIFactory.createWXAPI(context, appId, false);
        if (api.isWXAppInstalled()) {
            // send auth request
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_pingxin";
            api.sendReq(req);
        } else {
            ArmsUtils.snackbarText("请先安装微信客户端");
        }
    }
}
