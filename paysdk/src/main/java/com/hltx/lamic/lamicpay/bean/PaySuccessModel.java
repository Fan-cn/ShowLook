package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-08-02 11:34
 *     desc  : LamicPaySdk
 * </pre>
 */
public class PaySuccessModel {

    /**
     * errorCode : SUCCESS
     * isResultTrue : true
     * resultMsg : {"coupon_amount":"0.0","coupon_detail_list":"","make_invoice":false,"mch_discount_amount":"0.0","out_trade_no":"201908021133334499","pay_type":11,"pay_type_description":"现金支付","promotion_discount_amount":"0.0","receipt_amount":"0.27","total_amount":"0.27","trade_no":"19080211324548693205308","trade_status":2,"user_pay_amount":"0.27"}
     */

    private String errorCode = "SUCCESS";
    private boolean isResultTrue = true;
    private String resultMsg;
    private String outTradeNo;

    public PaySuccessModel() {
        resultMsg = "{\"coupon_amount\":\"\",\"coupon_detail_list\":\"\",\"make_invoice\":false,\"mch_discount_amount\":\"\",\"out_trade_no\":\"\",\"pay_type\":11,\"pay_type_description\":\"现金支付\",\"promotion_discount_amount\":\"0.0\",\"receipt_amount\":\"\",\"total_amount\":\"\",\"trade_no\":\"\",\"trade_status\":2,\"user_pay_amount\":\"\"}";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isIsResultTrue() {
        return isResultTrue;
    }

    public void setIsResultTrue(boolean isResultTrue) {
        this.isResultTrue = isResultTrue;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public static class ResultMsg{

        /**
         * coupon_amount : 0.0
         * coupon_detail_list :
         * make_invoice : false
         * mch_discount_amount : 0.0
         * out_trade_no : 201908021133334499
         * pay_type : 11
         * pay_type_description : 现金支付
         * promotion_discount_amount : 0.0
         * receipt_amount : 0.27
         * total_amount : 0.27
         * trade_no : 19080211324548693205308
         * trade_status : 2
         * user_pay_amount : 0.27
         */

        private String coupon_amount;
        private String coupon_detail_list;
        private boolean make_invoice;
        private String mch_discount_amount;
        private String out_trade_no;
        private int pay_type;
        private String pay_type_description;
        private String promotion_discount_amount;
        private String receipt_amount;
        private String total_amount;
        private String trade_no;
        private int trade_status = 2;
        private String user_pay_amount;

        public String getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(String coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getCoupon_detail_list() {
            return coupon_detail_list;
        }

        public void setCoupon_detail_list(String coupon_detail_list) {
            this.coupon_detail_list = coupon_detail_list;
        }

        public boolean isMake_invoice() {
            return make_invoice;
        }

        public void setMake_invoice(boolean make_invoice) {
            this.make_invoice = make_invoice;
        }

        public String getMch_discount_amount() {
            return mch_discount_amount;
        }

        public void setMch_discount_amount(String mch_discount_amount) {
            this.mch_discount_amount = mch_discount_amount;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_type_description() {
            return pay_type_description;
        }

        public void setPay_type_description(String pay_type_description) {
            this.pay_type_description = pay_type_description;
        }

        public String getPromotion_discount_amount() {
            return promotion_discount_amount;
        }

        public void setPromotion_discount_amount(String promotion_discount_amount) {
            this.promotion_discount_amount = promotion_discount_amount;
        }

        public String getReceipt_amount() {
            return receipt_amount;
        }

        public void setReceipt_amount(String receipt_amount) {
            this.receipt_amount = receipt_amount;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public int getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(int trade_status) {
            this.trade_status = trade_status;
        }

        public String getUser_pay_amount() {
            return user_pay_amount;
        }

        public void setUser_pay_amount(String user_pay_amount) {
            this.user_pay_amount = user_pay_amount;
        }
    }
}
