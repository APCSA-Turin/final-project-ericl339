package com.example;

import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class twitchGetter {
    private static String clientId = "8cwctwfwcvjc15rne9orytpl2yegdu";
    private static String clientSecret = "m63g4ojnfqn22vcjpce0kf2bpxhc2y";
    
    public static String getBearer() throws IOException {
        
        URL url = new URL("https://id.twitch.tv/oauth2/token?client_id=8cwctwfwcvjc15rne9orytpl2yegdu&client_secret=m63g4ojnfqn22vcjpce0kf2bpxhc2y&grant_type=client_credentials");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        
        while ((inputLine = buff.readLine()) != null) {
            content.append(inputLine);
        }
        buff.close();
        connection.disconnect();
        String output = "Bearer " + content.toString().substring(content.toString().indexOf(":") + 2, content.toString().indexOf(",") - 1);
        return output;
    }
    public static String getClientId(){return clientId;}

    public static String getClientSecret( ){return clientSecret;}
}