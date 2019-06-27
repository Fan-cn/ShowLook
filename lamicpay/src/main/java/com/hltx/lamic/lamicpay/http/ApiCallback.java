package com.hltx.lamic.lamicpay.http;

public interface ApiCallback{
    void onSuccess(Object resultData);
    void onError(String errors);
}
