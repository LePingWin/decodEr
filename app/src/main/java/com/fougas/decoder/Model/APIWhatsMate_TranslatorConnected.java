package com.fougas.decoder.Model;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.fougas.decoder.Model.APIWhatsMate_BuildJSON.BuildJSON;

/**
 * Created by Jean on 22/04/2017.
 * This class translate a file.txt with the API WhatsMates
 */
public class APIWhatsMate_TranslatorConnected {

    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public static String translate(String fromLang, String toLang, String text) throws Exception {

        // TODO: If you have your own Premium account credentials, put them down here:
        final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
        final String CLIENT_SECRET = "PUBLIC_SECRET";
        final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";



        // TODO: Should have used a 3rd party library to make a JSON string from an object

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(BuildJSON(fromLang,toLang,text).getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        String result = "";
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            result += output;
        }
        conn.disconnect();

        return result;
    }
}
