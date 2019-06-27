package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-25 17:43
 *     desc  :
 * </pre>
 */
public class BaseModel {
    private String errorCode;
    private boolean isResultTrue;
    private String resultMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isResultTrue() {
        return isResultTrue;
    }

    public void setResultTrue(boolean resultTrue) {
        isResultTrue = resultTrue;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
