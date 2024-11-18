package com.example.ruslanfifthapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader extends AsyncTask<String, Void, String> {

    private DataLoaderCallback callback;

    public DataLoader(DataLoaderCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.onDataLoaded(result);
        }
    }

    public interface DataLoaderCallback {
        void onDataLoaded(String data);
    }
}