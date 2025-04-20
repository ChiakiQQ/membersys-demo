package com.caitlyn.membersysdemo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartupVerify {

    public static void main(String[] args) {
        String url = "http://localhost:8080/verify";

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("已跳轉至: " + url);
            } catch (IOException | URISyntaxException e) {
                System.err.println("無法開啟瀏覽器: " + e.getMessage());
            }
        } else {
            System.err.println("當前系統不支援 Desktop 操作");
        }
    }
}
