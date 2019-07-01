package com.hltx.lamic.lamicpay.http;


import com.hltx.lamic.lamicpay.net.OkNet;
import com.hltx.lamic.lamicpay.net.callback.StringCallback;
import com.hltx.lamic.lamicpay.net.model.HttpHeaders;
import com.hltx.lamic.lamicpay.net.model.HttpParams;

public class ApiRequest {

    void Post(String url, HttpHeaders headers, HttpParams httpParams, StringCallback callback){
        OkNet.<String>post(url)
                .params(httpParams)
                .headers(headers)
//                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),params.toString()))
                .execute(callback);
    }

    void Get(String url, HttpHeaders headers, HttpParams httpParams, StringCallback callback){
        OkNet.<String>get(url)
                .headers(headers)
                .params(httpParams)
                .execute(callback);
    }


    /**
     * 请求标识符
     */
    public enum RequestFlag{
        FLAG1, FLAG2, FLAG3, FLAG4, FLAG5
    }
}
