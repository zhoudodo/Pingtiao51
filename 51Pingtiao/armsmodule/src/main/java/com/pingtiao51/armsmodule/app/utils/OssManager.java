package com.pingtiao51.armsmodule.app.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.pingtiao51.armsmodule.mvp.ui.helper.FileUtil;

import java.io.File;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * OSS图片上传的管理类
 */

public class OssManager {
    /**
     * 图片上传的地址
     * 问后台要的
     */
    public static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    /**
     * 图片的访问地址的前缀
     * 其实就是： bucketName + endpoint
     */
    public static String prefix = "https://pingtiao-dev.oss-cn-hangzhou.aliyuncs.com/";
    /**
     * Bucket是OSS上的命名空间
     * 问后台要的
     */
    public static String bucketName = "pingtiao-prod";

    /**
     * 图片保存到OSS服务器的目录，问后台要
     */
    public static String dir = "android"+File.separator;

    private OSS mOSS;

    private static OssManager mInstance;

    public static OssManager getInstance() {
        if (mInstance == null) {
            synchronized (OssManager.class) {
                mInstance = new OssManager();
            }
        }
        return mInstance;
    }

    /**
     * 创建OSS对象
     */
    private OSS getOSS(Context context) {
        if (mOSS == null) {
            OSSCredentialProvider provider = OSSConfig.newCustomSignerCredentialProvider();
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            mOSS = new OSSClient(context, endpoint, provider, conf);
        }
        return mOSS;
    }

    /**
     * 图片上传
     *
     * @param context
     * @param uploadFilePath   图片的本地路径
     * @param onUploadListener 回调监听
     */
    public void upload(final Context context, final int position, final String uploadFilePath,
                       final OnUploadListener onUploadListener) {
//        Log.d("dodoOSS","uploadFilePath = "+ uploadFilePath);
        Observable.just(context)
                .map(new Function<Context, OSS>() {
                    @Override
                    public OSS apply(Context context) throws Exception {
                        return getOSS(context);
                    }
                })
                .map(new Function<OSS, String>() {
                    @Override
                    public String apply(OSS oss) throws Exception {
                        // 创建压缩图片的路径
//                        File imageFilePath = FileUtil.createImageFilePath();
                        // 压缩图片
//                        ImageCompressUtil.compress(context, uploadFilePath, imageFilePath.getAbsolutePath());
                        // 创建上传的对象
                        String uuidImg = getUUIDByRules32Image();
//                        Log.d("dodoOSS","uuidImg = "+ uuidImg);
                        PutObjectRequest put = new PutObjectRequest(bucketName,
                                dir +uuidImg
                                , uploadFilePath);
                        // 上传的进度回调
                        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                            @Override
                            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                if (onUploadListener == null) {
                                    return;
                                }
                                onUploadListener.onProgress(position, currentSize, totalSize);
                            }
                        });
                        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                if (onUploadListener == null) {
                                    return;
                                }
                                String imageUrl = request.getObjectKey();

//                                Log.d("dodoOSS","imageUrl = "+ imageUrl);

//                                onUploadListener.onSuccess(position, uploadFilePath,
//                                        prefix + imageUrl);
                                onUploadListener.onSuccess(position, uploadFilePath,
                                          imageUrl);
                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                                serviceException.printStackTrace();
                                clientException.printStackTrace();
                                if (onUploadListener == null) {
                                    return;
                                }
                                onUploadListener.onFailure(position);
                            }
                        });
                        return uploadFilePath;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }

    public interface OnUploadListener {
        /**
         * 上传的进度
         */
        void onProgress(int position, long currentSize, long totalSize);

        /**
         * 成功上传
         */
        void onSuccess(int position, String uploadPath, String imageUrl);

        /**
         * 上传失败
         */
        void onFailure(int position);
    }

    /**
     * 上传到后台的图片的名称
     */
    public static String getUUIDByRules32Image() {
        StringBuffer generateRandStr = null;
        try {
            String rules = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int rpoint = 0;
            generateRandStr = new StringBuffer();
            Random rand = new Random();
            int length = 32;
            for (int i = 0; i < length; i++) {
                if (rules != null) {
                    rpoint = rules.length();
                    int randNum = rand.nextInt(rpoint);
                    generateRandStr.append(rules.substring(randNum, randNum + 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (generateRandStr == null) {
            return "getUUIDByRules32Image.png";
        }
        return generateRandStr + ".png";
    }

/*  使用范例  转载自 https://www.jianshu.com/p/745a328b7a26
    private void uploadImage(final List<ImageBean> images) {
        for (int i = 0; i < images.size(); i++) {
            ImageBean bean = images.get(i);
            OssManager.getInstance().upload(getAppActivity(), i, bean.path,
                    new OssManager.OnUploadListener() {

                        @Override
                        public void onProgress(int position, long currentSize, long totalSize) {
                            LogUtils.e("position = " + position + " onProgress = " + currentSize);
                        }

                        @Override
                        public void onSuccess(int position, String uploadPath, String imageUrl) {
                            LogUtils.e("position = " + position + " imageUrl = " + imageUrl
                                    +"\n uploadPath = "+uploadPath);
                        }

                        @Override
                        public void onFailure(int position) {
                            LogUtils.e("position = " + position);
                        }
                    }
            );
        }

    }*/
}