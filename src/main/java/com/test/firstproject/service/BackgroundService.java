package com.test.firstproject.service;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class BackgroundService extends Thread {

    @Override
    public void run() {

        System.out.println("Background thread: "
                + Thread.currentThread().getName());
        try {
            URL url = new URL("https://www.youtube.com");

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.disconnect();

            System.out.println("Request completed");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}