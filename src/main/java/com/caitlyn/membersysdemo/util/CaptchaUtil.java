package com.caitlyn.membersysdemo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class CaptchaUtil {

    public static String generateCaptchaText(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static byte[] generateCaptchaImage(String captchaText) throws IOException {
        int width = 100;
        int height = 40;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        Random rand = new Random();

        // 干擾線
        for (int i = 0; i < 20; i++) {
            int x1 = rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int x2 = rand.nextInt(width);
            int y2 = rand.nextInt(height);
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g2d.drawLine(x1, y1, x2, y2);
        }

        // 字體樣式
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        for (int i = 0; i < captchaText.length(); i++) {
            String ch = String.valueOf(captchaText.charAt(i));
            g2d.setColor(new Color(rand.nextInt(150), rand.nextInt(150), rand.nextInt(150)));

            // 保存當前畫布狀態
            Graphics2D g2 = (Graphics2D) g2d.create();
            double angle = Math.toRadians(rand.nextInt(30) - 15); // 傾斜角度 ±15 度
            int x = 20 + i * 18;
            int y = 30;
            g2.rotate(angle, x, y);
            g2.drawString(ch, x, y);
            g2.dispose();
        }

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}