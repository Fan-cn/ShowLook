package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-24 15:32
 *     desc  :
 * </pre>
 */
public class MethodConfig {
    //基地址
//    private final static String HOST                    = "gateway.lamic.cn";
    /**
     * 测试地址
     */
    private final static String HOST                    = "dev-gateway.lamic.cn";
    public final static String BASE_URL                 = "https://" + HOST + "/";

    /**
     * 成功状态
     */
    public static final String HTTP_SUCCESS             = "SUCCESS";

    /**
     * 获取证书
     */
     public static final String SDK_GET_CRT              = "port/pay/sdkGetCrt";


    /**
     *下单
     */
    public static final String TRADE_CREATE             = "port/pay/sdkPay";
    /**
     * 查询支付
     */
    public static final String TRADE_QUERY              = "port/pay/sdkQueryPay";
    /**
     * 关闭订单
     */
    public static final String TRADE_CLOSE              = "port/pay/sdkCloseOrder";
    /**
     * 退款
     */
    public static final String TRADE_REFUND             = "port/pay/sdkRefund";
//    /**
//     * 下载对账单
//     */
//    public static final String TRADE_DOWNLOAD_BILL      = "";
    /**
     * 交班扎帐
     */
    public static final String TRADE_BILL_END           = "port/pay/billend";
//    /**
//     * 创建支付通道
//     */
//    public static final String TRADE_CREATE_PAY_TYPE    = "";

    /**
     * 刷脸初始化
     */
    public static final String TRADE_FACE_INIT          = "port/pay/faceInit";
    /**
     * 刷脸支付
     */
    public static final String TRADE_FACE_SCAN          = "port/pay/smartScan";
    /**
     * 刷脸支付结果查询
     */
    public static final String TRADE_FACE_QUERY         = "port/pay/mverify";



    /**
     * 添加会员信息
     */
    public static final String MEMBER_ADD               = "port/pay/addVip";
    /**
     * 查询会员信息方法 (会员积分变动可能会有延迟)
     */
    public static final String MEMBER_QUERY             = "port/pay/sdkQueryVipCredits";
    /**
     * 修改会员信息方法
     */
    public static final String MEMBER_MODIFY            = "port/pay/sdkChangeVipCredits";
}
