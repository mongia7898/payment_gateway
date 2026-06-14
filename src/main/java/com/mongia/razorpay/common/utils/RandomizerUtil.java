package com.mongia.razorpay.common.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomizerUtil {

    private static final SecureRandom SECURE_RANDOM=new SecureRandom();

    public static String randomBase64(int length){
        byte[] buffer=new byte[length*3/4];
        SECURE_RANDOM.
                nextBytes(buffer);
        String random = Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
//        System.out.println(random.length());
//        return random.substring(0,length);
        return  random;
    }

//    public static void main(String[] args) {
//        for(int i=0;i<100;i++){
//            System.out.println("For length "+ i+ "value of random "+randomBase64(i).length());
//
//        }
//    }
}
