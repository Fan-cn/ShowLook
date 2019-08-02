package com.hltx.lamic.lamicpay;

import android.app.Application;

import com.google.gson.Gson;
import com.hltx.lamic.lamicpay.bean.MethodConfig;
import com.hltx.lamic.lamicpay.bean.BaseModel;
import com.hltx.lamic.lamicpay.bean.CrtModel;
import com.hltx.lamic.lamicpay.bean.HttpModel;
import com.hltx.lamic.lamicpay.bean.HttpResponseModel;
import com.hltx.lamic.lamicpay.bean.PaySuccessModel;
import com.hltx.lamic.lamicpay.bean.TOLOrder;
import com.hltx.lamic.lamicpay.db.DBManager;
import com.hltx.lamic.lamicpay.db.TimerManager;
import com.hltx.lamic.lamicpay.http.ApiCallback;
import com.hltx.lamic.lamicpay.http.ApiHttp;
import com.hltx.lamic.lamicpay.http.LamicApiCallBack;
import com.hltx.lamic.lamicpay.utils.Debug;
import com.hltx.lamic.lamicpay.utils.DesUtil;
import com.hltx.lamic.lamicpay.utils.DesUtils4CSharp;
import com.hltx.lamic.lamicpay.utils.NetCheckUtil;
import com.hltx.lamic.lamicpay.utils.SignUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
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

    /**
     * 日志标识
     */
    public static final String      TAG                 = "LamicSDK";
    /**
     * 是否打印日志
     */
    public static boolean           isLoggable          = true;
    /**
     * 是否是测试环境地址
     */
    public static boolean          isDebuggle           = false;
    /**
     * 是否使用离线功能
     */
    public static boolean          isUseOutLineOrder    = false;
    /**
     * 默认超时时间 毫秒
     */
    private static long             DEF_MILLISECONDS    = 15000;
    /**
     * 数据同步时间间隔
     */
    private static long             DEF_DELAY_SECONDS   = 1 * 60 * 1000;


    /**
     * 莱米UID
     */
    public static String            UID;
    /**
     * 私钥
     */
    public static String            PRIVATE_KEY;

//    public static String            UID = "22222222222";
//    public static String            PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM3NjLXb5w5RBRXau1Ud/ORcGnyOk3vGoL6FBQOfdKlD1JFftabexrJg7E/jXkR3KfGDnAtzoKct/ckpvxiVboK7he2Gxn+DavBlREJinSVUCda60sph2w889b5ns8hudqIsrb2/fxdkPxEAboE7YSBok/QpwtndOpkippdMLccnAgMBAAECgYAQ4cupH2DUy9Ce+lJjqRIVqXiCvy9Z9/E3r7G5mlr3h5joU/GSvrON7mV0KDKTazMFnMYwKkwDasJmvgXu/lMSQKQucSyzm7bMAlgEAX4D8eAeNJIt9/v1sZ5Bg3/0jZNuk/PHiFxjOD+0yBrXtCwLmUzpJixagQmvrPSPN4J4AQJBAOt6mHCuhIZK+4mhQnKuZv8UgQErIFQGpP2hzjI6ewZreL1uSUVmJVp2c0g2MQrXFPCc92wTkr4x0laPVYUIJk0CQQDfvOZa5XeKV/g7ce98Bt2TPbZDipdIyRjWwkPvTwOehT/wqNAW3PcmmMHrSRWR4m/JFVjI2emH+kQx/AK6HUVDAkBl43z0PL8A8I7YJVuADbBpLLEJFWT+loVrbUiv+RfkVjo/FOpFSgZdlyUYmMIto5Te67wvGmUDQMF3TLu/PSB9AkBLxnpuBpF59VlJKMlnRBv/JkN4lJOwPwt+kMTZY/Vh1tdU9pejZqr+E3Z57YK0qfAaNnSfcc46E3TNSQDTb95pAkEAsReDNCCcsDW5VbHCmUpyta86+LDilbuDYmblx/gSqfsuGEDmqo7eWk+idrPt980wvhs88XGSBV1/hJ0aPRo38g==";

    private static String           ORIGIN_KEY = "E2D109F7BFF127A0697DCCC9F98C995E60A76180D0C7C577CBB5DEE4FC05E0E94491F61AF4273E2437EAD0057E8F0B85D88AD5AD5CC81FE52AA9091D432591A9E1509075C1E56F490615E95EDC180EAB8B508142864C9C02B0D7E68F851A792AB7E2D59D4932A78C5F8AA09E8F58EDBFC8C708832A467FE81FB6F899F42D24C8F727DBF6CF134D60B7CADA040B93C155014C520C444361E3C0065C6EC1C358F299BB205C83FA01984955B74D5469EE5A880B45F811413DCF8D6690E9CDA96E38E026DF784A1C03D442AC0F8533C37B5E8F1C8160307EF5D7157FC53A6420355E3E1B63BE65CD068203EFBD133ACCAEB563CC6E01984E6940FEDA01792D46759CFAE1C19939AB55FFFA58BD518D1E6BB865B9E7B73AE3B55A567D8C807316403289734BAE08C5B7A3243F22E34C26CF8ADD213EB7978F57D411C86213F9A846C7A75203AE5D05BEA15DE87DF05AB2838533932FAC4B6133ADFBB9BF0E89C3D58690A6259ABE7930A95EB877626AAC01AE2400C02D7EFA42D85FE8B65A5B6798D72965D1C946C66BBE8EFB971E4A53DC6EF0243727A5ABBBCF68D26A6E1CFDEEC13D802C4B2229C669713DA1B17A5F10D97988B00CC51AA8A3F2273AA4BD0C57372BD32E8BD8EAFC24F267ABC0AB2B3CB779A47E25BCD70755F27880FC5C7FEB3E58B3FBF12CBE2D5E556544BEE0DFEA2CB3CC450C7705086097DE3E6F52236C091673A2245480517B9935F8FB5768F8CDE656A41C47C4F2FBC52A45F21A743C45599AC26D398174437D0465909E18C78B8DBBFCBD500ADABF4A51EBB053E627BDF65476257C39FB206F894E5CE5F38BBAF615EF976EF3CC656B187B5DD1E32AAD535F8D13353D9E70A1EBB220476FF6B445F0225798E13CFEEFE5B5A95016602F9DE9464675A49983F8F0E412CFCE3D83A16E5751BA85FA4667816D02822EA37F09DAAD6CA5DDAA3E6D790BC6ED1C88282FADA0F4605C913D534F3431CB34D5213552AFE93107C24A266B80AB71BDC20FF100914989A6FD0986F5F02AB9D8019080E7C66405AC7F6DE8D5F71E2B0363EFBECBB416DD27EA0F81F21F9AE81F43DF25F0092A0736115749D31ED1D228B85BC1BF2B28D017D702FDAB5C4FD0036EB3A3B3512381659DFEC30E3BB3F110D2E5A34A159AC46FE06C1C9D9527022297FCBBA0E35FE5A757624C8C30C7B28197C92EA5F9282F81D41B";

    private static LamicPay         instance;

    /**
     * 全局Application
     */
    private Application             mApp;


    static {
        PRIVATE_KEY = DesUtils4CSharp.decrypt(ORIGIN_KEY);
    }

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
    public LamicPay init(Application application, String uid){
        HttpUtils.checkNotNull(application, "请调用初始化接口 LamicPay.getInstance().init()，在自定义的Application中!");
        HttpUtils.checkNotNull(uid,"请在init或changeAccount接口中传入uid!");

        this.mApp = application;
        changeAccount(uid);

        initHttp();
        getCrt();

        if (isUseOutLineOrder){
            DBManager.initHelper(application);
            initTimer();
        }

        return instance;
    }

    private void initTimer(){
        Timer timer = new Timer();
        TimerManager manager = new TimerManager();
        timer.schedule(manager, DEF_DELAY_SECONDS, DEF_DELAY_SECONDS);
    }

    /**
     * 统一接口请求
     * @param method    接口{@link MethodConfig}
     * @param params    参数
     * @param callBack  回调
     */
    public void invoke(final String method, final Map<String, Object> params, final LamicApiCallBack callBack){

        checkInit();
        HttpUtils.checkNotNull(method, "请传入method参数(如MethodConfig.TRADE_CREATE)");
        HttpUtils.checkNotNull(params, "请传入参数");

        if (isUseOutLineOrder){
            if (method.equals(MethodConfig.TRADE_CREATE)){
                NetCheckUtil.isNetWorkAvailableOfGet(new Comparable<Boolean>() {
                    @Override
                    public int compareTo(Boolean available) {
                        if (available){
                            request(method, params, callBack);
                        }else {
                            DBManager.getHelper().save(new TOLOrder(
                                    valueOf(params.get("out_trade_no")), valueOf(params.get("goods_detail")),
                                    valueOf(params.get("total_amount")), valueOf(params.get("auth_code")),
                                    valueOf(params.get("pay_type")),     valueOf(params.get("discountable_amount")),
                                    valueOf(params.get("vip_card_no")),  valueOf(params.get("terminalName")),
                                    valueOf(params.get("terminalNo")),   valueOf(params.get("outcashier")),
                                    valueOf(params.get("make_invoice"))));
                            HttpModel httpModel = new HttpModel();
                            httpModel.setCode(HttpResponseModel.RESPONSE_SUCCESS);
                            String json = new Gson().toJson(new PaySuccessModel());
                            httpModel.setMsg(json);
                            httpModel = ResultToMap(json, httpModel);
                            if(callBack != null)
                                callBack.callBack(new Gson().toJson(httpModel));
                        }
                        return 0;
                    }
                });
            }else {
                request(method, params, callBack);
            }
        }else {
            request(method, params, callBack);
        }
    }

    private void request(String method, Map<String, Object> params, final LamicApiCallBack callBack) {
        ApiHttp apiHttp = new ApiHttp();
        apiHttp.putParams(SignUtils.makeParamMap(params));
        String url = getHost() + method;
        apiHttp.post(url, new ApiCallback() {
            @Override
            public void onSuccess(Object resultData) {
                String json = resultData.toString();
                BaseModel model = new Gson().fromJson(json, BaseModel.class);
                HttpModel httpModel = new HttpModel();

                try {
                    if (model.isResultTrue()){
                        httpModel.setCode(HttpResponseModel.RESPONSE_SUCCESS);
                        httpModel.setMsg(json);
                        httpModel = ResultToMap(json, httpModel);
                    }else {
                        httpModel.setCode(HttpResponseModel.RESPONSE_SERVER_ERROR);
                        httpModel.setMsg(HttpResponseModel.RESPONSE_SERVER_ERROR_MSG);
                        httpModel.setSubCode(model.getErrorCode());
                        httpModel.setSubMsg(model.getResultMsg());
                    }
                } catch (Exception e) {
                    httpModel.setCode(HttpResponseModel.RESPONSE_SDK_ERROR);
                    httpModel.setMsg(e.getMessage());
                    httpModel.setSubCode(HttpResponseModel.SDK_OTHER_EXCEPTION);
                    httpModel.setSubMsg(json);
                }

                if(callBack != null)
                    callBack.callBack(new Gson().toJson(httpModel));
            }

            @Override
            public void onError(String errors) {
                HttpModel model = new HttpModel(
                        HttpResponseModel.RESPONSE_SDK_ERROR, HttpResponseModel.RESPONSE_SDK_ERROR_MSG,
                        HttpResponseModel.SDK_WEB_EXCEPTION, HttpResponseModel.SDK_WEB_EXCEPTION_MSG);
                if(callBack != null)
                    callBack.callBack(new Gson().toJson(model));
            }
        });
    }

    /**
     * 将JsonObject转换为Map
     */
    HttpModel ResultToMap(String json, HttpModel httpModel) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("resultMsg")) {
                JSONObject object = new JSONObject(jsonObject.optString("resultMsg"));
                Iterator<?> it = object.keys();
                String key = "";
                String val = "";
                while (it.hasNext()) {//遍历JSONObject
                    key = (String) it.next().toString();
                    val = object.optString(key);
                    map.put(key, val);
                }
            }
        } catch (JSONException e) {
            map.put("error", "数据解析失败");
        }
        httpModel.setData(map);
        return httpModel;
    }

    /**
     * 私钥获取
     */
    private void getCrt(){
        ApiHttp apiHttp = new ApiHttp();
        String encrypt = DesUtil.encrypt(UID);
        apiHttp.put("uid", encrypt);
        String url = getHost() + MethodConfig.SDK_GET_CRT;
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
    private void initHttp(long... args) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(TAG);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        if (args.length > 0){
            DEF_MILLISECONDS = args[0];
        }
        builder.readTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEF_MILLISECONDS, TimeUnit.MILLISECONDS);

        if (args.length > 0){
            OkGo.getInstance().setOkHttpClient(builder.build());
        }else {
            OkGo.getInstance().init(mApp).setOkHttpClient(builder.build());
        }
    }


    /**
     * 账号信息 发生变更
     * @param uid   莱米UID
     */
    public void changeAccount(String uid){
        UID = uid;
    }


    /**
     * 取消所有网络请求
     */
    public void cancelAllHttp(){
        checkInit();
        OkGo.getInstance().cancelAll();
    }

    /**
     * 设置运行的环境
     * @param debuggle 默认测试环境
     */
    public LamicPay setIsDebuggle(boolean debuggle) {
        isDebuggle = debuggle;
        return instance;
    }

    /**
     * 是否打印日志
     * @param loggable  boolean {@link LamicPay#isLoggable 默认值}
     */
    public LamicPay setIsLoggable(boolean loggable){
        isLoggable = loggable;
        return instance;
    }

    /**
     * 是否使用离线现金支付功能
     * @param isUseOutLine  boolean {@link LamicPay#isUseOutLineOrder 默认值}
     */
    public LamicPay setIsUseOutLineOrder(boolean isUseOutLine){
        isUseOutLineOrder = isUseOutLine;
        return instance;
    }

    /**
     * 网络连接超时时间设置
     * @param milliseconds 毫秒 {@link LamicPay#DEF_MILLISECONDS 默认值}
     */
    public LamicPay setConnTimeout(long milliseconds){
        initHttp(milliseconds);
        return instance;
    }

    /**
     * 数据同步时间间隔设置
     * @param defDelaySeconds 毫秒{@link LamicPay#DEF_DELAY_SECONDS 默认值}
     *                          必须当{@link LamicPay#isUseOutLineOrder}为true的情况下，才有效
     */
    public LamicPay setDefDelaySeconds(long defDelaySeconds) {
        DEF_DELAY_SECONDS = defDelaySeconds;
        return instance;
    }

    /**
     * 判断是否为空
     */
    private void checkInit(){
        HttpUtils.checkNotNull(mApp, "必须调用初始化接口 LamicPay.getInstance().init()，在自定义的Application中!");
        HttpUtils.checkNotNull(UID,"请在init或changeAccount接口中传入uid!");
    }

    /**
     * 获取地址
     * @return 环境
     */
    private String getHost(){
        return isDebuggle ? MethodConfig.BASE_URL_DEV : MethodConfig.BASE_URL_GETE;
    }

    private String valueOf(Object obj){
        return String.valueOf(obj);
    }
}
