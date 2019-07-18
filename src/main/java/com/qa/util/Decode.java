package com.qa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Decode {

    static Logger log = LoggerFactory.getLogger(Decode.class);

    public static String encodeText(String stringToEncode){
        return Base64.getEncoder().encodeToString(stringToEncode.getBytes());
    }

    public static String decodeText(String encodedString){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static void main(String args[]) {
        log.info("After Encoding [" + encodeText("StringtoEncode") + "]");
        log.info("After Decoding [" + decodeText("U3RyaW5ndG9FbmNvZGU=") + "]");
    }
}