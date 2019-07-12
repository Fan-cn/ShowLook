package com.hltx.lamic.lamicpaysdk;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hltx.lamic.lamicpay.LamicPay;
import com.hltx.lamic.lamicpay.bean.BaseModel;
import com.hltx.lamic.lamicpay.bean.HttpModel;
import com.hltx.lamic.lamicpay.bean.HttpResponseModel;
import com.hltx.lamic.lamicpay.bean.MethodConfig;
import com.hltx.lamic.lamicpay.http.LamicApiCallBack;
import com.hltx.lamic.lamicpay.utils.Debug;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_activity);
        textView = findViewById(R.id.text);
    }

    public void create(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "999372131285");
        params.put("goods_detail", "[{\"goods_id\":\"06922868285730\",\"goods_name\":\"心相印茶语经典茶香型纸面巾\",\"quantity\":1,\"price\":0.01,\"body\":\"规格型号：168mm×210mm 152抽×3层（456张）\",\"show_url\":\"\",\"tax_code\":\"304010100000000000001\"}]");
        params.put("pay_type", "17");
//        params.put("pay_type_description", "7");
        params.put("total_amount", "0.01");
        params.put("discountable_amount", "2");
        params.put("auth_code", "134944934419391547");

        LamicPay.getInstance().invoke(MethodConfig.TRADE_CREATE, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    public void query(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "99937213128");
        LamicPay.getInstance().invoke(MethodConfig.TRADE_QUERY, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    public void close(View view) {
    }

    public void refund(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "999372131285");
        params.put("refund_amount", "0.01");
        params.put("refund_pwd", "20152016");
        LamicPay.getInstance().invoke(MethodConfig.TRADE_REFUND, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    public void memberQuery(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("vip_card_no", "18980489167");
        LamicPay.getInstance().invoke(MethodConfig.MEMBER_QUERY, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    public void memberModify(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", +20);
        params.put("vip_card_no", "18980489167");
        LamicPay.getInstance().invoke(MethodConfig.MEMBER_MODIFY, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    public void memberAdd(View view) {
        Map<String, Object> params = new HashMap<>();
//        params.put("tel", "15708416715");
//        params.put("credits", "100");
//        params.put("nickName", "火锅");
//        params.put("sex", "1");
//        params.put("name", "凡凡");
//        params.put("birth", "2019-06-26");
//        LamicPay.getInstance().invoke(MethodConfig.MEMBER_ADD, params, new LamicApiCallBack() {
//            @Override
//            public void callBack(String json) {
//                Debug.i(json);
//                textView.setText(json);
//            }
//        });
        params.put("strpayid", "201907031826129199");
        LamicPay.getInstance().invoke(MethodConfig.TRADE_FACE_QUERY, params, new LamicApiCallBack() {
            @Override
            public void callBack(String json) {
                Debug.i(json);
                textView.setText(json);
            }
        });
    }

    String json = "{\"errorCode\":\"SUCCESS\",\"isResultTrue\":true,\"resultMsg\":\"{\\\"coupon_amount\\\":\\\"0.0\\\",\\\"coupon_detail_list\\\":\\\"\\\",\\\"invoice_url\\\":\\\"http://www.lamic.cn/fapiao?uid=22222222222&mpid=201907121057013068\\\",\\\"make_invoice\\\":true,\\\"mch_discount_amount\\\":\\\"0.0\\\",\\\"out_trade_no\\\":\\\"201907121057013068\\\",\\\"pay_type\\\":11,\\\"pay_type_description\\\":\\\"现金支付\\\",\\\"promotion_discount_amount\\\":\\\"0.0\\\",\\\"receipt_amount\\\":\\\"0.01\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"trade_no\\\":\\\"19071210562859803206235\\\",\\\"trade_status\\\":2,\\\"user_pay_amount\\\":\\\"0.01\\\"}\"}";

    public void test(View view) {
        BaseModel model = new Gson().fromJson(json, BaseModel.class);
        HttpModel httpModel = new HttpModel();
        if (model.getErrorCode().equals(MethodConfig.HTTP_SUCCESS)) {
            httpModel.setCode(HttpResponseModel.RESPONSE_SUCCESS);
            httpModel.setMsg(json);
            try {
                Map<String, Object> data = GsonToMaps(json);
                if (data.containsKey("resultMsg")) {
                    String resultMsg = (String) data.get("resultMsg");
                    try {
                        Map<String, Object> map = GsonToMaps(resultMsg);
                        data.put("resultMsg", map);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Debug.i(e.getMessage() + ":" + resultMsg);
                    }
                }
                httpModel.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
                Debug.i(e.getMessage() + ":" + json);
            }
        }
        Log.i("-----", new Gson().toJson(httpModel));
    }


    /**
     * string转map
     */
    public <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }
}
