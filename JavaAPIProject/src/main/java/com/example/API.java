package com.example;

import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {
    // gets data from an inputed api with the headers of the api url, client id, bearer code, and the command to the api
    public static String getData(String link, String bearer, String clientId, String work) throws Exception {
        /*endpoint is a url (string) that you get from an API website*/
        URL url = new URL(link);
        /*connect to the URL*/
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // creates a post request for the api. cannot use a get because the api specifies for a post
        connection.setRequestMethod("POST");
        
        // sets the header for the api. it needs a client id and a bearer code
        connection.setRequestProperty("Client-ID", clientId);
        connection.setRequestProperty("Authorization", bearer);

        // i learned this from https://www.baeldung.com/httpurlconnection-post
        connection.setDoOutput(true);
        String requestBody = work;
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);           
        }
        
        /* When you read data from the server, it wil be in bytes, the InputStreamReader will convert it to text. 
        The BufferedReader wraps the text in a buffer so we can read it line by line*/
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //variable to store text, line by line
        String inputLine;
        /*A string builder is similar to a string object but faster for larger strings, 
        you can concatenate to it and build a larger string. Loop through the buffer 
        (read line by line). Add it to the stringbuilder */
        StringBuilder content = new StringBuilder();
        while ((inputLine = buff.readLine()) != null) {
            content.append(inputLine);
        }
        //close the bufferreader
        buff.close();
        //disconnect from server 
        connection.disconnect();
        //return the content as a string
        return content.toString();
    }
}