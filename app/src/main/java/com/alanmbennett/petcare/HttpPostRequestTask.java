package com.alanmbennett.petcare;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostRequestTask extends AsyncTask<String, Void, String> {

    private String jsonStr;

    public HttpPostRequestTask(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Override
    protected String doInBackground(String... str) {
        try {
            URL url = new URL(str[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream writer = conn.getOutputStream();
            writer.write(jsonStr.getBytes());
            writer.close();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_CREATED)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                return response.toString();
            }

        }
        catch(IOException e)
        {

        }

        return "POST Failed!";
    }
}
