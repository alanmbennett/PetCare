package com.alanmbennett.petcare;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequestTask extends AsyncTask<String, Void, String>
{
    private AsyncTaskCallback callback;

    public HttpGetRequestTask()
    {
        callback = null;
    }

    public HttpGetRequestTask(AsyncTaskCallback callback)
    {
        this.callback = callback;
    }

    protected String doInBackground(String... str) {

        try {
            URL url = new URL(str[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

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
            callback.onPostExecute(result);
        }
    }

}