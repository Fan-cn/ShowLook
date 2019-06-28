/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hltx.lamic.lamicpay.net;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.hltx.lamic.lamicpay.net.cookie.CookieJarImpl;
import com.hltx.lamic.lamicpay.net.https.HttpsUtils;
import com.hltx.lamic.lamicpay.net.interceptor.HttpLoggingInterceptor;
import com.hltx.lamic.lamicpay.net.model.HttpHeaders;
import com.hltx.lamic.lamicpay.net.model.HttpParams;
import com.hltx.lamic.lamicpay.net.request.GetRequest;
import com.hltx.lamic.lamicpay.net.request.PostRequest;
import com.hltx.lamic.lamicpay.net.utils.HttpUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.OkHttpClient;


public class OkNet {
    public static final long DEFAULT_MILLISECONDS = 60000;      //默认的超时时间
    public static long REFRESH_TIME = 300;                      //回调刷新时间（单位ms）

    private Application context;            //全局上下文
    private Handler mDelivery;              //用于在主线程执行的调度器
    private OkHttpClient okHttpClient;      //ok请求的客户端
    private HttpParams mCommonParams;       //全局公共请求参数
    private HttpHeaders mCommonHeaders;     //全局公共请求头
    private int mRetryCount;                //全局超时重试次数

    private OkNet() {
        mDelivery = new Handler(Looper.getMainLooper());
        mRetryCount = 1;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkNet");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.readTimeout(OkNet.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkNet.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkNet.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        okHttpClient = builder.build();
    }

    public static OkNet getInstance() {
        return OkGoHolder.holder;
    }

    private static class OkGoHolder {
        private static OkNet holder = new OkNet();
    }

    /** get请求 */
    public static <T> GetRequest<T> get(String url) {
        return new GetRequest<>(url);
    }

    /** post请求 */
    public static <T> PostRequest<T> post(String url) {
        return new PostRequest<>(url);
    }

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public OkNet init(Application app) {
        context = app;
        return this;
    }

    /** 获取全局上下文 */
    public Context getContext() {
        HttpUtils.checkNotNull(context, "please call OkNet.getInstance().init() first in application!");
        return context;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        HttpUtils.checkNotNull(okHttpClient, "please call OkNet.getInstance().setOkHttpClient() first in application!");
        return okHttpClient;
    }

    /** 必须设置 */
    public OkNet setOkHttpClient(OkHttpClient okHttpClient) {
        HttpUtils.checkNotNull(okHttpClient, "okHttpClient == null");
        this.okHttpClient = okHttpClient;
        return this;
    }

    /** 获取全局的cookie实例 */
    public CookieJarImpl getCookieJar() {
        return (CookieJarImpl) okHttpClient.cookieJar();
    }

    /** 超时重试次数 */
    public OkNet setRetryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        mRetryCount = retryCount;
        return this;
    }

    /** 超时重试次数 */
    public int getRetryCount() {
        return mRetryCount;
    }

    /** 获取全局公共请求参数 */
    public HttpParams getCommonParams() {
        return mCommonParams;
    }

    /** 添加全局公共请求参数 */
    public OkNet addCommonParams(HttpParams commonParams) {
        if (mCommonParams == null) mCommonParams = new HttpParams();
        mCommonParams.put(commonParams);
        return this;
    }

    /** 获取全局公共请求头 */
    public HttpHeaders getCommonHeaders() {
        return mCommonHeaders;
    }

    /** 添加全局公共请求参数 */
    public OkNet addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put(commonHeaders);
        return this;
    }

    /** 根据Tag取消请求 */
    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /** 根据Tag取消请求 */
    public static void cancelTag(OkHttpClient client, Object tag) {
        if (client == null || tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /** 取消所有请求请求 */
    public void cancelAll() {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    /** 取消所有请求请求 */
    public static void cancelAll(OkHttpClient client) {
        if (client == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}
