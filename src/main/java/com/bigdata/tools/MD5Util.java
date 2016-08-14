package com.bigdata.tools;
import java.security.MessageDigest;

/**
 * Created by stone on 2016/5/12.
 */
public class MD5Util {
//    private static MessageDigest md5 = null;
//    static {
//        try{
//            md5 = MessageDigest.getInstance("MD5");
//        }catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//    }

    public String getMd5(String str){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs){
            if((x & 0xff)>>4 == 0 ){
                sb.append("0").append(Integer.toHexString(x & 0xff));
            }
            else
            {
                sb.append(Integer.toHexString((x & 0xff)));
            }
        }
        return sb.toString();
    }

//    public static void main(String[] args){
//        System.out.println(getMd5("hello world"));
//    }
}
