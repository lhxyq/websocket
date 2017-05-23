package com.lh.customer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by LH 2446059046@qq.com on 2017/5/23.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        WebSocketClinet clinet = new WebSocketClinet(new URI("ws://localhost:8080/chart/LH" + random.nextInt(100)));

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String message = br.readLine();
            if (message.equals("q")) {
                clinet.onClose();
                break;
            }
            clinet.sendMessage(message);
        }
    }
}
