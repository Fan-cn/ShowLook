package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-25 18:23
 *     desc  :
 * </pre>
 */
public class HttpResponseModel {

    /**
     * 调用成功
     */
    public static final int RESPONSE_SUCCESS                = 200;
    public static final String RESPONSE_SUCCESS_MSG         = "调用成功";
    /**
     * Sdk端错误
     */
    public static final int RESPONSE_SDK_ERROR              = 300;
    public static final String RESPONSE_SDK_ERROR_MSG       = "Sdk端错误";
    /**
     * 服务器端错误
     */
    public static final int RESPONSE_SERVER_ERROR           = 400;
    public static final String RESPONSE_SERVER_ERROR_MSG    = "服务器端错误";

    public static final String SDK_WEBEXCEPTION         = "SDK_WEBEXCEPTION";
    public static final String SDK_WEBEXCEPTION_MEG     = "操作失败,网络请求失败";
}
