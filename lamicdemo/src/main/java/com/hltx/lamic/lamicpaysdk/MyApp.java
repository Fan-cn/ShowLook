package com.hltx.lamic.lamicpaysdk;

import android.app.Application;

import com.hltx.lamic.lamicpay.LamicPay;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-25 14:46
 *     desc  :
 * </pre>
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LamicPay.getInstance().setIsDebuggle(false).init(this, "22222222222");
    }
}
