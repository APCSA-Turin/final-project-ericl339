package com.example;

import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//"https://api.igdb.com/v4/genres/"
//"fields *; limit 10;"


public class API {
    public static String getData(String link, String bearer, String clientId, String work) throws Exception {
        URL url = new URL(link);
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        
        connection.setRequestProperty("Client-ID", clientId);
        connection.setRequestProperty("Authorization", bearer);
        
        connection.setDoOutput(true);
        // i learned this from https://www.baeldung.com/httpurlconnection-post
        String requestBody = work;
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);           
        }
        
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        
        while ((inputLine = buff.readLine()) != null) {
            content.append(inputLine);
        }
        buff.close();
        
        connection.disconnect();
        return content.toString();
    }
}