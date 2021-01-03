package com.cosateca.apirest.utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {


    private static MessageDigest md;
    private static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            
        }
    }

    public static String calcularHash(byte[] bytes) {
        if (md == null) {
            return "";
        }
        md.update(bytes, 0, bytes.length);
        byte[] hash = md.digest();
        return hexStringFromBytes(hash);
    }

    private static final String hexStringFromBytes(byte[] b) {
        String hex = "";
        int msb;
        int lsb = 0;
        int i;

        // MSB maps to idx 0   
        for (i = 0; i < b.length; i++) {
            msb = ((int) b[i] & 0x000000FF) / 16;
            lsb = ((int) b[i] & 0x000000FF) % 16;
            hex = hex + hexChars[msb] + hexChars[lsb];
        }
        return (hex);
    }
    
}
