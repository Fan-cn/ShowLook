package com.hltx.lamic.lamicpay.utils;

import android.util.Log;

import com.hltx.lamic.lamicpay.LamicPay;


public class Debug {

    public static void d(Object msg) {
        println(Log.DEBUG, msg);
    }

    public static void i(Object msg) {
        println(Log.INFO, msg);
    }

    public static void w(Object msg) {
        println(Log.WARN, msg);
    }

    public static void e(Object msg) {
        println(Log.ERROR, msg);
    }

    private static void println(int priority, Object msg) {
        if (LamicPay.isLoggable)
            Log.println(priority, LamicPay.TAG, String.valueOf(msg));
    }
}
