package com.caitlyn.membersysdemo.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
    //32位小寫
    public static String md5(String input) {
        return DigestUtils.md5Hex(input);
    }
}