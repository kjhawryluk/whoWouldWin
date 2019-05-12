package edu.uchicago.kjhawryluk.prowebservice.data.remote.model;


/**
 * Created by Adam Gerber on 5/3/2015.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONParser {

    //https://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
    public JSONParser() {
    }

    public JSONObject getJSONFromUrl(String url, int timeout) throws JSONException {
        HttpURLConnection c = null;
        try {
            URL u = new URL("https://swapi.co/api/people/?page=1");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    try {
                        return new JSONObject(sb.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        throw e;
                    }

            }

        } catch (MalformedURLException ex) {
            Logger.getLogger("Util").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("Util").log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger("Util").log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

}