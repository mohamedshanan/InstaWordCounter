package com.shannan.instawordcounter.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkCall {

    public String makeNetworkCall(String urlString) {
        HttpURLConnection connection = null;
        String response = null;
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            is = connection.getInputStream();
            response = readIt(is);
            Log.d("response", response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    connection.disconnect();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private String readIt(InputStream iStream) throws Exception {
        String singleLine;
        StringBuilder totalLines = new StringBuilder(iStream.available());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(iStream));

            while ((singleLine = reader.readLine()) != null) {
                totalLines.append(singleLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return totalLines.toString();
    }

}