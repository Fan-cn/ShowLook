package com.hltx.lamic.lamicpay.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author Moses
 * @date 2018-02-01 18:02:07 针对C-Sharp的des加密，解密工具类
 */

public class DesUtils4CSharp {
    private final static String DES_PKCS = "DES/CBC/PKCS5Padding";
    public static final String SDK_DES_KEY = "DES*5/>^";

    /**
     * 加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     *
     * @return 返回加密后的数据
     *
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_PKCS);
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     *
     * @return 返回解密后的原始数据
     *
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_PKCS);
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(src);
    }


    /**
     * 解密
     *
     * @param data
     *
     * @return
     *
     * @throws Exception
     */
    public final static String decrypt(String data, String key) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()),
                    key.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data
     *
     * @return
     *
     * @throws Exception
     */
    public final static String decrypt(String data) {
        String key = "SDK/3*(^";
        try {
            return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
        } catch (Exception e) {
            Debug.i(e.getMessage());
        }
        return null;
    }

    /**
     * 加密
     *
     * @param str
     *
     * @return
     *
     * @throws Exception
     */
    public final static String encrypt(String str, String key) {
        try {
            return byte2hex(encrypt(str.getBytes(), key.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 二进制转字符串
     *
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

//    public static void main(String[] args){
//        System.out.println(encrypt("22222222222", SDK_DES_KEY));
//        System.out.println(decrypt("E2D109F7BFF127A0697DCCC9F98C995E60A76180D0C7C577CBB5DEE4FC05E0E94491F61AF4273E2437EAD0057E8F0B85D88AD5AD5CC81FE52AA9091D432591A9E1509075C1E56F490615E95EDC180EAB8B508142864C9C02B0D7E68F851A792AB7E2D59D4932A78C5F8AA09E8F58EDBFC8C708832A467FE81FB6F899F42D24C8F727DBF6CF134D60B7CADA040B93C155014C520C444361E3C0065C6EC1C358F299BB205C83FA01984955B74D5469EE5A880B45F811413DCF8D6690E9CDA96E38E026DF784A1C03D442AC0F8533C37B5E8F1C8160307EF5D7157FC53A6420355E3E1B63BE65CD068203EFBD133ACCAEB563CC6E01984E6940FEDA01792D46759CFAE1C19939AB55FFFA58BD518D1E6BB865B9E7B73AE3B55A567D8C807316403289734BAE08C5B7A3243F22E34C26CF8ADD213EB7978F57D411C86213F9A846C7A75203AE5D05BEA15DE87DF05AB2838533932FAC4B6133ADFBB9BF0E89C3D58690A6259ABE7930A95EB877626AAC01AE2400C02D7EFA42D85FE8B65A5B6798D72965D1C946C66BBE8EFB971E4A53DC6EF0243727A5ABBBCF68D26A6E1CFDEEC13D802C4B2229C669713DA1B17A5F10D97988B00CC51AA8A3F2273AA4BD0C57372BD32E8BD8EAFC24F267ABC0AB2B3CB779A47E25BCD70755F27880FC5C7FEB3E58B3FBF12CBE2D5E556544BEE0DFEA2CB3CC450C7705086097DE3E6F52236C091673A2245480517B9935F8FB5768F8CDE656A41C47C4F2FBC52A45F21A743C45599AC26D398174437D0465909E18C78B8DBBFCBD500ADABF4A51EBB053E627BDF65476257C39FB206F894E5CE5F38BBAF615EF976EF3CC656B187B5DD1E32AAD535F8D13353D9E70A1EBB220476FF6B445F0225798E13CFEEFE5B5A95016602F9DE9464675A49983F8F0E412CFCE3D83A16E5751BA85FA4667816D02822EA37F09DAAD6CA5DDAA3E6D790BC6ED1C88282FADA0F4605C913D534F3431CB34D5213552AFE93107C24A266B80AB71BDC20FF100914989A6FD0986F5F02AB9D8019080E7C66405AC7F6DE8D5F71E2B0363EFBECBB416DD27EA0F81F21F9AE81F43DF25F0092A0736115749D31ED1D228B85BC1BF2B28D017D702FDAB5C4FD0036EB3A3B3512381659DFEC30E3BB3F110D2E5A34A159AC46FE06C1C9D9527022297FCBBA0E35FE5A757624C8C30C7B28197C92EA5F9282F81D41B"));
//    }
}