package com.hltx.lamic.lamicpay;

import android.app.Application;

import com.google.gson.Gson;
import com.hltx.lamic.lamicpay.bean.MethodConfig;
import com.hltx.lamic.lamicpay.bean.BaseModel;
import com.hltx.lamic.lamicpay.bean.CrtModel;
import com.hltx.lamic.lamicpay.bean.HttpModel;
import com.hltx.lamic.lamicpay.bean.HttpResponseModel;
import com.hltx.lamic.lamicpay.http.ApiCallback;
import com.hltx.lamic.lamicpay.http.ApiHttp;
import com.hltx.lamic.lamicpay.http.LamicApiCallBack;
import com.hltx.lamic.lamicpay.utils.Debug;
import com.hltx.lamic.lamicpay.utils.DesUtil;
import com.hltx.lamic.lamicpay.utils.DesUtils4CSharp;
import com.hltx.lamic.lamicpay.utils.SignUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.HttpUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-24 14:13
 *     desc  : 莱米支付SDK
 * </pre>
 */
public class LamicPay {

    public static final String      TAG                 = "LamicSDK";       //日志标识
    public static final boolean     isLoggable          = true;             //是否打印日志
    public static final int         DEF_MILLISECONDS    = 15000;            //默认超时时间 毫秒
    public static String            UID;
    public static String            PRIVATE_KEY;
//    public static String            UID = "22222222222";
//    public static String            PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM3NjLXb5w5RBRXau1Ud/ORcGnyOk3vGoL6FBQOfdKlD1JFftabexrJg7E/jXkR3KfGDnAtzoKct/ckpvxiVboK7he2Gxn+DavBlREJinSVUCda60sph2w889b5ns8hudqIsrb2/fxdkPxEAboE7YSBok/QpwtndOpkippdMLccnAgMBAAECgYAQ4cupH2DUy9Ce+lJjqRIVqXiCvy9Z9/E3r7G5mlr3h5joU/GSvrON7mV0KDKTazMFnMYwKkwDasJmvgXu/lMSQKQucSyzm7bMAlgEAX4D8eAeNJIt9/v1sZ5Bg3/0jZNuk/PHiFxjOD+0yBrXtCwLmUzpJixagQmvrPSPN4J4AQJBAOt6mHCuhIZK+4mhQnKuZv8UgQErIFQGpP2hzjI6ewZreL1uSUVmJVp2c0g2MQrXFPCc92wTkr4x0laPVYUIJk0CQQDfvOZa5XeKV/g7ce98Bt2TPbZDipdIyRjWwkPvTwOehT/wqNAW3PcmmMHrSRWR4m/JFVjI2emH+kQx/AK6HUVDAkBl43z0PL8A8I7YJVuADbBpLLEJFWT+loVrbUiv+RfkVjo/FOpFSgZdlyUYmMIto5Te67wvGmUDQMF3TLu/PSB9AkBLxnpuBpF59VlJKMlnRBv/JkN4lJOwPwt+kMTZY/Vh1tdU9pejZqr+E3Z57YK0qfAaNnSfcc46E3TNSQDTb95pAkEAsReDNCCcsDW5VbHCmUpyta86+LDilbuDYmblx/gSqfsuGEDmqo7eWk+idrPt980wvhs88XGSBV1/hJ0aPRo38g==";

    private static LamicPay         instance;
    private Application             mApp;                                   //全局Application


    public LamicPay(){

    }


    public static LamicPay getInstance() {
        if (instance == null) {
            synchronized (LamicPay.class) {
                if (instance == null) {
                    instance = new LamicPay();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化接口（在application中调用）
     * @param application   全局Application
     * @param uid           莱米UID
     */
    public void init(Application application, String uid){
        HttpUtils.checkNotNull(application, "请调用初始化接口 LamicPay.getInstance().init()，在自定义的Application中!");
        HttpUtils.checkNotNull(uid,"请在init或changeAccount接口中传入uid!");

        this.mApp = application;
        changeAccount(uid);

        initHttp();
        getCrt();
    }

    /**
     * 账号信息 发生变更
     * @param uid   莱米UID
     */
    public void changeAccount(String uid){
        UID = uid;
    }

    public void invoke(String method, Map<String, Object> params, final LamicApiCallBack callBack){

        checkInit();
        HttpUtils.checkNotNull(method, "请传入method参数(如ApiConfig.TRADE_CREATE)");
        HttpUtils.checkNotNull(params, "请传入参数");

        ApiHttp apiHttp = new ApiHttp();
        apiHttp.putParams(SignUtils.makeParamMap(params));
        String url = MethodConfig.BASE_URL + method;
        apiHttp.post(url, new ApiCallback() {
            @Override
            public void onSuccess(Object resultData) {
                String json = resultData.toString();
                BaseModel model = new Gson().fromJson(json, BaseModel.class);
                HttpModel httpModel = new HttpModel();
                if (model.getErrorCode().equals(MethodConfig.HTTP_SUCCESS)){
                    httpModel.setCode(HttpResponseModel.RESPONSE_SUCCESS);
                    httpModel.setMsg(json);
                }else {
                    httpModel.setCode(HttpResponseModel.RESPONSE_SERVER_ERROR);
                    httpModel.setMsg(HttpResponseModel.RESPONSE_SERVER_ERROR_MSG);
                    httpModel.setSubCode(model.getErrorCode());
                    httpModel.setSubMsg(model.getResultMsg());
                }
                callBack.callBack(new Gson().toJson(httpModel));
            }

            @Override
            public void onError(String errors) {
                HttpModel model = new HttpModel(
                        HttpResponseModel.RESPONSE_SDK_ERROR, HttpResponseModel.RESPONSE_SDK_ERROR_MSG,
                        HttpResponseModel.SDK_WEBEXCEPTION, HttpResponseModel.SDK_WEBEXCEPTION_MEG);
                callBack.callBack(new Gson().toJson(model));
            }
        });
    }


    private void getCrt(){
        ApiHttp apiHttp = new ApiHttp();
        String encrypt = DesUtil.encrypt(UID);
        apiHttp.put("uid", encrypt);
        String url = MethodConfig.BASE_URL + MethodConfig.SDK_GET_CRT;
        apiHttp.post(url, new ApiCallback() {
            @Override
            public void onSuccess(Object resultData) {
                String json = resultData.toString();
                CrtModel crtModel = new Gson().fromJson(json, CrtModel.class);
                if (crtModel.getErrorCode().equals(MethodConfig.HTTP_SUCCESS)){
                    PRIVATE_KEY = DesUtils4CSharp.decrypt(crtModel.getData().getPrivateKey());
                }else {
                    Debug.i(crtModel.getResultMsg());
                }
            }

            @Override
            public void onError(String errors) {
                Debug.i(errors);
            }
        });
    }

    /**
     * 初始化网络请求
     */
    private void initHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(TAG);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        builder.readTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(mApp)
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        ;
    }

    /**
     * 取消所有网络请求
     */
    public void cancelAllHttp(){
        checkInit();
        OkGo.getInstance().cancelAll();
    }


    private void checkInit(){
        HttpUtils.checkNotNull(mApp, "请调用初始化接口 LamicPay.getInstance().init()，在自定义的Application中!");
        HttpUtils.checkNotNull(UID,"请在init或changeAccount接口中传入uid!");
    }
}
