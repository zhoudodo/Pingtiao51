package com.pingtiao51.armsmodule.mvp.ui.helper.biometric

import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException

class BiometricPromptHelper {
    companion object {
//        @RequiresApi(Build.VERSION_CODES.M)
//        fun showBiometric(activity: androidx.fragment.app.FragmentActivity, title: String, description: String, cancelText: String) {
//            var disposable =
//                    RxPreconditions
//                            .hasBiometricSupport(activity)
//                            .flatMapCompletable {
//                                if (!it) Completable.error(BiometricNotSupported())
//                                else
//                                    RxBiometric
//                                            .title(title)
//                                            .description(description)
//                                            .negativeButtonText(cancelText)
//                                            .negativeButtonListener(DialogInterface.OnClickListener { _, _ ->
//                                                //点击取消
//                                            })
//                                            .executor(MainHandlerExecutor(Handler(activity.mainLooper)))
//                                            .build()
//                                            .authenticate(activity)
//                            }
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeBy(
//                                    onComplete = {
//                                        //认证成功
//                                    },
//                                    onError = {
//                                        //错误情况
//                                        when (it) {
//                                            is AuthenticationError ->
//                                                ArmsUtils.snackbarText("error: ${it.errorCode} ${it.errorMessage}")
//                                            is AuthenticationFail -> ArmsUtils.snackbarText("fail")
//                                            else -> {
//                                                ArmsUtils.snackbarText("other error")
//                                            }
//                                        }
//                                    }
//                            )
//        }
    }

    private class MainHandlerExecutor internal constructor(private val mHandler: Handler) : Executor {

        override fun execute(command: Runnable) {
            if (!mHandler.post(command)) {
                throw RejectedExecutionException("$mHandler is shutting down")
            }
        }
    }
}