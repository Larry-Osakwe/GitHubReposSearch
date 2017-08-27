package com.aerofs.takehometest;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.aerofs.takehometest.MainActivity.LOG_TAG;

/**
 * Created by Larry Osakwe on 8/26/2017.
 */

public class QueryUtils {

    private QueryUtils() {

    }


    public static ArrayList<Repo> extractRepos(String repoJSON) {
        if (TextUtils.isEmpty(repoJSON)) {
            return null;
        }

        ArrayList<Repo> repositories = new ArrayList<>();

        URL url = createURL(repoJSON);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
            JSONArray baseJsonResponse = new JSONArray(jsonResponse);


            for (int i = 0; i < baseJsonResponse.length(); i++) {
                JSONObject repo = baseJsonResponse.getJSONObject(i);
                String name = repo.getString("name");
                int count = repo.getInt("forks_count");
                String language = repo.getString("language");
                String date = repo.getString("updated_at");


                repositories.add(new Repo(name, count, date, language));
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the review JSON results", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem parsing the review JSON results", e);
        }

        return repositories;

    }

    private static URL createURL(String repoJSON) {
        URL url = null;
        try {
            url = new URL(repoJSON);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the review JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}
