package com.caitlyn.membersysdemo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartupRedis {

    public static void main(String[] args) {
        String url = "http://localhost:8080/redis/keys"; //redis key
//        url = "http://localhost:8080/redis/value?key=admin_session:admin"; //redis value
//        url = "http://localhost:8080/redis/delete?name=admin"; //清除redis鎖>>>已改啟動時清掉
//        url = "http://localhost:8080/redis/deleteAll"; //清除redis
        /*--
        //redis connect test
        url = "http://localhost:8080/redis/test/set";
        url = "http://localhost:8080/redis/test/get";
        --*/

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("已開啟瀏覽器跳轉到: " + url);
            } catch (IOException | URISyntaxException e) {
                System.err.println("無法開啟瀏覽器: " + e.getMessage());
            }
        } else {
            System.err.println("Desktop 不支援，請手動開啟網址：" + url);
        }
    }
}