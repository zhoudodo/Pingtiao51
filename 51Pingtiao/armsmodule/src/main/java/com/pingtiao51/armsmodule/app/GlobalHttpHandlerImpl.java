package com.pingtiao51.armsmodule.app;

import android.content.Context;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by MVPArmsTemplate on 03/02/2019 21:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {

    Gson gson = new Gson();

    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return
     */
    long comein = 0;
    long next = 0;

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        comein = System.currentTimeMillis();
        if (comein > next + 3000) {
            //相隔3秒以上处理token失效
            try {
                BaseJson bj = gson.fromJson(httpResult, BaseJson.class);
                if (bj.isTokenValid() && !(ActivityUtils.getTopActivity() instanceof LoginActivity)) {
                    //token失效
                    ArmsUtils.snackbarText(bj.getMessage());
                    Bundle bundle = new Bundle();
                    bundle.putInt(LoginActivity.LOGIN_MODE, InputLoginView.CODE_LOGIN);
                    next = System.currentTimeMillis();
                    ActivityUtils.startActivity(bundle, LoginActivity.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //如果出错了不能影响到正常请求
            }
        }

        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        return request;
    }


    private final static int BUFFER_SIZE = 4 * 1024;

    public static String readStreamAsString(InputStream in, String charset)
            throws IOException {
        if (in == null)
            return "";

        Reader reader = null;
        Writer writer = new StringWriter();
        String result;

        char[] buffer = new char[BUFFER_SIZE];
        try {
            reader = new BufferedReader(
                    new InputStreamReader(in, charset));

            int n;
            while ((n = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, n);
            }

            result = writer.toString();
        } finally {
//            safeClose(in); //不能关闭输入流后面用的着
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

        return result;
    }

    public static void safeClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 为整个body加密
     * @param request
     * @throws IOException
     */
//    public void encryptBody(Request request) throws IOException {
//        RequestBody oldRequestBody = request.body();
//        Buffer requestBuffer = new Buffer();
//        oldRequestBody.writeTo(requestBuffer);
//        String oldBodyStr = requestBuffer.readUtf8();
//        requestBuffer.close();
//        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
//        //生成随机AES密钥并用serverPublicKey进行RSA加密
//        SecretKeySpec appAESKeySpec = EncryptUtils.generateAESKey(256);
//        String appAESKeyStr = EncryptUtils.covertAESKey2String(appAESKeySpec);
//        String appEncryptedKey = RSAUtils.encryptDataString(appAESKeyStr, serverPublicKey);
//        //计算body 哈希 并使用app私钥RSA签名
//        String appSignature = RSAUtils.signature(oldBodyStr, appPrivateKey);
//        //随机AES密钥加密oldBodyStr
//        String newBodyStr = EncryptUtils.encryptAES(appAESKeySpec, oldBodyStr);
//        RequestBody newBody = RequestBody.create(mediaType, newBodyStr);
//        //构造新的request
//        request = request.newBuilder()
//                .header("Content-Type", newBody.contentType().toString())
//                .header("Content-Length", String.valueOf(newBody.contentLength()))
//                .method(request.method(), newBody)
//                .header("appEncryptedKey", appEncryptedKey)
//                .header("appSignature", appSignature)
//                .header("appPublicKey", appPublicKeyStr)
//                .build();
//    }

    /**
     * 为单个请求值加密
     *
     * @param request
     */
    public Request encryptValues(Request request) {
        FormBody formBody = (FormBody) request.body();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for(int i=0;i<formBody.size();i++){
            //把原来的参数添加到新的构造器
            formBuilder.addEncoded(formBody.encodedName(i),formBody.encodedValue(i));
        }
        FormBody formBody1 = formBuilder
                //添加新的参数并构造新的请求体
                .addEncoded("timestamp","")
                .build();

         request = request.newBuilder()
                //在header中添加新的参数
        .header("sign","")
                .post(formBody1)
                .build();
         return request;
    }

}
