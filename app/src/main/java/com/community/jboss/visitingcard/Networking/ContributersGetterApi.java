package com.community.jboss.visitingcard.Networking;

import android.content.Context;
import android.os.AsyncTask;

import com.community.jboss.visitingcard.DevelopersAboutActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ContributersGetterApi extends AsyncTask {
    private Context context;
    private String[][] dataset;
    private String apiURL;

    public ContributersGetterApi(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        apiURL = "https://api.github.com/repos/JBossOutreach/visiting-card-android/contributors?q=contributions&order=desc";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .build();
            Response responses = null;
            String jsonData = null;
            try {
                responses = client.newCall(request).execute();
                jsonData = responses.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONArray Jarray = new JSONArray(jsonData);
            dataset = new String[Jarray.length()][3];
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject object = Jarray.getJSONObject(i);
                dataset[i][0] = object.getString("avatar_url");
                dataset[i][1] = object.getString("login");
                dataset[i][2] = object.getString("html_url");
                if (dataset[i][1].equals("null")) dataset[i][1] = "Not given";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((DevelopersAboutActivity) context).receiveData(dataset);
    }
}
