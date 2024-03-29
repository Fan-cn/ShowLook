package com.hltx.lamic.lamicpay.http;


import com.google.gson.Gson;
import com.hltx.lamic.lamicpay.bean.PaySuccessModel;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ApiHttp extends ApiRequest{
    private HttpHeaders headers     = new HttpHeaders();
    private HttpParams params      = new HttpParams();
    private ApiCallback     callback;

    public void putHeader(String key, String val){
        headers.put(key, val);
    }

    public void put(String key, String val){
        params.put(key,val);
    }

    public void putParams(Map<String,Object> map){
        for (String key:map.keySet()) {
            params.put(key, String.valueOf(map.get(key)));
        }
    }

    /**
     *   Activity销毁时，取消网络请求
     *   OkNet.getInstance().cancelTag(tag);
     *
     * @param url 地址
     * @param cb 回调
     */
    public void post(String url, ApiCallback cb){
        this.callback = cb;

        Post(url, headers, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                headers.clear();
                params.clear();
                String s = response.body();

                callback.onSuccess(s);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                headers.clear();
                params.clear();

                try {
                    int code = response.code();
                    if (code != 200)
                        callback.onError("服务器连接失败!");
                } catch (Exception e1) {
                    callback.onError("网络连接失败！");
                }
            }
        });
    }
    /**
     *   Activity销毁时，取消网络请求
     *   OkNet.getInstance().cancelTag(tag);
     *
     * @param url 地址
     * @param cb 回调
     */
    public void post(final String outTradeNo, String url, ApiCallback cb){
        this.callback = cb;

        Post(url, headers, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                headers.clear();
                params.clear();
                String s = response.body();

                try {
                    JSONObject obj = new JSONObject(s);
                    String errorCode = obj.optString("errorCode");
                    boolean isResultTrue = obj.optBoolean("isResultTrue");
                    String resultMsg = obj.optString("resultMsg");
                    if (isResultTrue){
                        callback.onSuccess(s);
                    }else {
                        PaySuccessModel model = new PaySuccessModel();
                        model.setErrorCode(errorCode);
                        model.setIsResultTrue(false);
                        model.setResultMsg(resultMsg);
                        model.setOutTradeNo(outTradeNo);
                        String toJson = new Gson().toJson(model);
                        callback.onSuccess(toJson);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                headers.clear();
                params.clear();

                try {
                    int code = response.code();
                    if (code != 200)
                        callback.onError("服务器连接失败!");
                } catch (Exception e1) {
                    callback.onError("网络连接失败！");
                }
            }
        });
    }

}
