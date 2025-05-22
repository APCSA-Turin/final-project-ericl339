package com.example;

import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class twitchGetter {
    // sets the client id and client secret
    private static String clientId = "8cwctwfwcvjc15rne9orytpl2yegdu";
    private static String clientSecret = "m63g4ojnfqn22vcjpce0kf2bpxhc2y";
    
    // method to get the bearer id
    public static String getBearer() throws IOException {
        /*endpoint is a url (string) that you get from an API website*/
        URL url = new URL("https://id.twitch.tv/oauth2/token?client_id=8cwctwfwcvjc15rne9orytpl2yegdu&client_secret=m63g4ojnfqn22vcjpce0kf2bpxhc2y&grant_type=client_credentials");
        /*connect to the URL*/
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // creates a post request to the api since it specifies to do so
        connection.setRequestMethod("POST");
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

        buff.close(); //close the bufferreader
        connection.disconnect(); //disconnect from server 
        // formats the bearer code
        String output = "Bearer " + content.toString().substring(content.toString().indexOf(":") + 2, content.toString().indexOf(",") - 1); 
        return output; //return the content as a string
    }
    // returns the client id
    public static String getClientId(){return clientId;}
    // returns the client secret
    public static String getClientSecret( ){return clientSecret;}
}