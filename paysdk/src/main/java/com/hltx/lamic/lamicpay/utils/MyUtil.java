package com.hltx.lamic.lamicpay.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.TimeUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具
 */
public class MyUtil {

    public static boolean isNoEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        if (null == str)
            return true;
        if (str.length() == 0)
            return true;
        if (str.trim().length() == 0)
            return true;
        if (str.indexOf("null") == 0)
            return true;
        return false;
    }

    public static boolean isNoEmpty(List<?> datas) {
        return !isEmpty(datas);
    }

    public static boolean isEmpty(List<?> datas) {
        if (datas == null)
            return true;
        if (datas.size() == 0)
            return true;
        return false;
    }

    /**
     * 去掉多余的0
     *
     * @param str
     * @return
     */
    public static String removeNumberZero(String str) {
        if (isEmpty(str)) {
            return "0";
        }
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");// 去掉多余的0
            str = str.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return str;
    }

    /**
     * 把字体结果dimen转化成原sp值
     *
     * @return
     */
    public static float floatToSpDimension(float value, Context context) {
        return value / context.getResources().getDisplayMetrics().scaledDensity;
    }

    private static long lastTime = 0;

    /**
     * 是否是快速点击
     * @return
     */
    public static boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime < 500)
            return true;
        lastTime = curTime;
        return false;
    }
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isMyFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


}
