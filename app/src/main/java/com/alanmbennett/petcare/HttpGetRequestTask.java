package com.alanmbennett.petcare;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequestTask extends AsyncTask<String, Void, String>
{
    private HttpGetCallback callback;
    private String header;
    private String headerVal;

    public HttpGetRequestTask()
    {
        callback = null;
    }

    public HttpGetRequestTask(HttpGetCallback callback)
    {
        this.callback = callback;
    }

    public HttpGetRequestTask(HttpGetCallback callback, String header, String headerVal) {
        this.callback = callback;
        this.header = header;
        this.headerVal = headerVal;
    }

    protected String doInBackground(String... str) {

        try {
            URL url = new URL(str[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            if(header != null && headerVal != null){
                conn.setRequestProperty(header, headerVal);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            return response.toString();
        }
        catch(IOException e) {
            return null;
        }
    }

    protected void onPostExecute(String result)
    {
        if(callback != null)
        {
            callback.onHttpGetDone(result);
        }
    }

}