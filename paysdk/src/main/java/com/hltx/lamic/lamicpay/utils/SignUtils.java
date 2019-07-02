package com.hltx.lamic.lamicpay.utils;


import com.hltx.lamic.lamicpay.LamicPay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Moses
 * @date 2017-12-01 16:19:43
 * @copyright Copyright by www.lamic.cn
 * @description 签名工具
 */
public class SignUtils {

    private static String CHARSET = "UTF-8";

    /**
     * 签名
     *
     * @param sPara
     *
     * @return
     */
    private static String sign(Map<String, Object> sPara) {
        String prestr = createLinkString(sPara);
        String md5Str = prestr.substring(0, prestr.length() - 1);
        String md5 = DesUtil.md5(md5Str);
        return buildRsaSign(md5, LamicPay.PRIVATE_KEY);
    }


    private static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            if ("".equals(value.trim())) {
                continue;
            }
            if (!"sign".equals(key)) {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    private static String buildRsaSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求参数
     */
    public static Map<String,Object> makeParamMap(Map<String,Object> paramsMap){
        Map<String, Object> reqMap = new HashMap<>(paramsMap);
        reqMap.put("signType", "RSA");
        reqMap.put("uid", String.format("%s<reqtime>%s", LamicPay.UID, dateFormat()));
        reqMap.put("sign", sign(reqMap));
        return reqMap;
    }

    private static String dateFormat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return format.format(new Date(System.currentTimeMillis()));
    }

//    public static void main(String[] args){
//        Map<String, Object> map = new HashMap<>();
//        map.put("signType", "RSA");
//        String s = dateFormat();
//        map.put("uid", "22222222222<reqtime>"+ s);
//        map.put("out_trade_no", "999372131285");
//        map.put("refund_amount", "0.01");
//        map.put("refund_pwd", "20152016");
//        System.out.println(s);
//        String sign = sign(map);
//        System.out.println(sign);
//    }

}
