package com.hltx.lamic.lamicpay.db;

import com.google.gson.Gson;
import com.hltx.lamic.lamicpay.LamicPay;
import com.hltx.lamic.lamicpay.bean.BaseModel;
import com.hltx.lamic.lamicpay.bean.MethodConfig;
import com.hltx.lamic.lamicpay.bean.PaySuccessModel;
import com.hltx.lamic.lamicpay.bean.TOLOrder;
import com.hltx.lamic.lamicpay.http.ApiCallback;
import com.hltx.lamic.lamicpay.http.ApiHttp;
import com.hltx.lamic.lamicpay.utils.Debug;
import com.hltx.lamic.lamicpay.utils.MyUtil;
import com.hltx.lamic.lamicpay.utils.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-08-02 09:15
 *     desc  : LamicPaySdk
 * </pre>
 */
public class TimerManager extends TimerTask {
    @Override
    public void run() {
        List<TOLOrder> orders = DBManager.getHelper().queryAll(TOLOrder.class);

        if (orders == null || orders.size() == 0){
            Debug.i("------>无订单数据需要上传");
            return;
        }
        Debug.i("------>"+orders.size()+" 条未上传订单数据");
        Debug.i(orders.toString());

        for (TOLOrder order : orders) {

            String outTradeNo = order.getOut_trade_no();
            String goodsDetail = order.getGoods_detail();
            String totalAmount = order.getTotal_amount();
            String authCode = order.getAuth_code();
            String payType = order.getPay_type();
            String amount = order.getDiscountable_amount();
            String vipCardNo = order.getVip_card_no();
            String terminalName = order.getTerminalName();
            String terminalNo = order.getTerminalNo();
            String outcashier = order.getOutcashier();
            String invoice = order.getMake_invoice();

            Map<String, Object> params = new HashMap<>();
            params.put("out_trade_no",  outTradeNo);
            params.put("goods_detail",  goodsDetail);
            params.put("pay_type",      payType);
            params.put("total_amount",  totalAmount);
            if (MyUtil.isNoEmpty(authCode)){
                params.put("auth_code", authCode);
            }
            if (MyUtil.isNoEmpty(amount)){
                params.put("discountable_amount", amount);
            }
            if (MyUtil.isNoEmpty(vipCardNo)){
                params.put("vip_card_no", vipCardNo);
            }
            if (MyUtil.isNoEmpty(terminalNo)){
                params.put("terminalNo", terminalNo);//终端编号
            }
            if (MyUtil.isNoEmpty(terminalName)){
                params.put("terminalName", terminalName);//设备名称
            }
            if (MyUtil.isNoEmpty(outcashier)){
                params.put("outcashier", outcashier);//收银员账号
            }
            if (MyUtil.isNoEmpty(invoice)){
                params.put("make_invoice", invoice);
            }

            ApiHttp apiHttp = new ApiHttp();
            apiHttp.putParams(SignUtils.makeParamMap(params));
            String url = LamicPay.isDebuggle ? MethodConfig.BASE_URL_DEV : MethodConfig.BASE_URL_GETE + MethodConfig.TRADE_CREATE;
            apiHttp.post(outTradeNo, url, new ApiCallback() {
                @Override
                public void onSuccess(Object resultData) {
                    String json = resultData.toString();
                    BaseModel model = new Gson().fromJson(json, BaseModel.class);
                    if (model.isResultTrue()){
                        PaySuccessModel.ResultMsg fromJson = new Gson().fromJson(model.getResultMsg(), PaySuccessModel.ResultMsg.class);
                        DBManager.getHelper().deleteByOutTradeNo(fromJson.getOut_trade_no());
                        Debug.i(fromJson.getOut_trade_no() + "，上传成功，删除本地记录-----");
                    }else {
                        if (model.getErrorCode().equals("ORDER_EXISTS_OUT_TRADE_NO_REPEAT")){
                            PaySuccessModel successModel = new Gson().fromJson(json, PaySuccessModel.class);
                            Debug.i(successModel.getOutTradeNo()+"------->"+successModel.getResultMsg());
                            DBManager.getHelper().deleteByOutTradeNo(successModel.getOutTradeNo());
                        }
                    }
                }

                @Override
                public void onError(String errors) {

                }
            });
        }
    }


}
