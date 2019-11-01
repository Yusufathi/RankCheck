package com.example.rankcheck;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpIDFetcher extends AsyncTask<String,Void,String> {

    String temp;
    @Override
    protected String doInBackground(String... urls) {
        URL url;
        HttpURLConnection urlConnection=null;

        try{
            url=new URL(urls[0]);
            urlConnection=(HttpURLConnection) url.openConnection();
            InputStream in= urlConnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);
            int data=reader.read();

            while(data!=-1){
                char current=(char) data;
                temp+=current;
                data=reader.read();
            }

            return temp;

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "Error";
    }
}
