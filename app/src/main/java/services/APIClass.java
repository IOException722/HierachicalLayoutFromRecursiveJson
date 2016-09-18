package services;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Abhay on 16-09-2016.
 */
public class APIClass extends AsyncTask<String, String, String> {
    private MyService Receiver;
    private int type;
    private final Bundle b = new Bundle();
    HttpURLConnection urlConnection;
    public APIClass(int type, MyService Receiver)
    {
        this.type= type;
        this.Receiver=Receiver;
    }
    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            Log.v("Ascii","after");
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            b.putString("apiresult",result.toString());
            Receiver.send(type,b);
        } catch (Exception e) {
            b.putString("apiresult","");
            Receiver.send(type,b);
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }
}
