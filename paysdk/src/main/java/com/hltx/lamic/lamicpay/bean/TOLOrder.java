package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-08-01 18:28
 *     desc  : 离线订单数据
 * </pre>
 */
public class TOLOrder {
    private String out_trade_no;
    private String goods_detail;
    private String total_amount;
    private String auth_code;
    private String pay_type;
    private String discountable_amount;
    private String vip_card_no;
    private String terminalName;
    private String terminalNo;
    private String outcashier;
    private String make_invoice;

    public TOLOrder(String out_trade_no, String goods_detail, String total_amount, String auth_code, String pay_type, String discountable_amount, String vip_card_no, String terminalName, String terminalNo, String outcashier, String make_invoice) {
        this.out_trade_no = out_trade_no;
        this.goods_detail = goods_detail;
        this.total_amount = total_amount;
        this.auth_code = auth_code;
        this.pay_type = pay_type;
        this.discountable_amount = discountable_amount;
        this.vip_card_no = vip_card_no;
        this.terminalName = terminalName;
        this.terminalNo = terminalNo;
        this.outcashier = outcashier;
        this.make_invoice = make_invoice;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getDiscountable_amount() {
        return discountable_amount;
    }

    public void setDiscountable_amount(String discountable_amount) {
        this.discountable_amount = discountable_amount;
    }

    public String getVip_card_no() {
        return vip_card_no;
    }

    public void setVip_card_no(String vip_card_no) {
        this.vip_card_no = vip_card_no;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getOutcashier() {
        return outcashier;
    }

    public void setOutcashier(String outcashier) {
        this.outcashier = outcashier;
    }

    public String getMake_invoice() {
        return make_invoice;
    }

    public void setMake_invoice(String make_invoice) {
        this.make_invoice = make_invoice;
    }
}
