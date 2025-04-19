package com.caitlyn.membersysdemo.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartupBrowserOpener {

    public static void main(String[] args) {
        String url = "http://localhost:8080/admin/login";

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