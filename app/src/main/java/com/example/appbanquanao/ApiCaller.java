package com.example.appbanquanao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCaller {

    public String callApi(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Or POST, PUT, etc.

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                // Handle error response
                System.out.println("API call failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}