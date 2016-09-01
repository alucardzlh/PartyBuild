package com.example.a25908.partybuild.Utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 * Created by alucard on 2016-06-28.
 */
public class MD5 {



    /**
     * MD5加密
     * @param
     * @return
     */
    public final static String MD5s(String s) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            byte[] btInput = s.getBytes("UTF-8");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
