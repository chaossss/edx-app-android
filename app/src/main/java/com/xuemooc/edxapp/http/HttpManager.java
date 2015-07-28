package com.xuemooc.edxapp.http;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * Created by hackeris on 15/7/28.
 */
public class HttpManager {

    protected void log(String msg) {
        Log.d("HttpManager", msg);
    }

    /**
     * Executes a GET request to given URL with given parameters.
     *
     * @param urlWithAppendedParams
     * @param headers
     * @return
     * @throws IOException
     */
    public String get(String urlWithAppendedParams, Bundle headers) throws IOException {

        URL url = new URL(urlWithAppendedParams);
        StringBuilder resultBuilder = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");

        // set request headers
        if (headers != null) {
            for (String key : headers.keySet()) {
                log(key + ": " + headers.getString(key));
                connection.setRequestProperty(key, headers.getString(key));
            }
        }

        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            resultBuilder.append(inputLine);
        }
        connection.disconnect();
        return resultBuilder.toString();
    }

    public String post(String urlString, Bundle params, Bundle headers) throws IOException {

        StringBuilder paramsBuilder = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                log(key + ": " + params.getString(key));
                paramsBuilder.append(key);
                paramsBuilder.append("=");
                paramsBuilder.append(params.getString(key));
                paramsBuilder.append("&");
            }
            //  remove the last &
            paramsBuilder.delete(paramsBuilder.length() - 1, paramsBuilder.length());
        }
        return post(urlString, paramsBuilder.toString(), headers, false);
    }

    /**
     * Performs a HTTP POST or PATCH request with given postBody and headers.
     *
     * @param urlString
     * @param postBody
     * @param headers
     * @param isPatchRequest - If true, then performs PATCH request, POST otherwise.
     * @return
     * @throws IOException
     */
    public String post(String urlString, String postBody, Bundle headers, boolean isPatchRequest) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        if (isPatchRequest) {
            connection.setRequestMethod("PATCH");
        } else {
            connection.setRequestMethod("POST");
        }

        // set request headers
        if (headers != null) {
            for (String key : headers.keySet()) {
                log(key + ": " + headers.getString(key));
                connection.setRequestProperty(key, headers.getString(key));
            }
        }

        byte[] bytes = postBody.getBytes();
        connection.getOutputStream().write(bytes);

        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String inputLine;
        StringBuilder resultBuilder = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            resultBuilder.append(inputLine);
        }
        connection.disconnect();
        return resultBuilder.toString();
    }

    /**
     * Performs a HTTP POST with given postBody and headers.
     *
     * @param url
     * @param postBody
     * @param headers
     * @return
     * @throws IOException
     */
    public String post(String url, String postBody, Bundle headers)
            throws IOException {
        // this is POST, so isPathRequest=false
        return post(url, postBody, headers, false);
    }

    /**
     * Returns GET url with appended parameters.
     *
     * @param url
     * @param params
     * @return
     */
    public static String toGetUrl(String url, Bundle params) {
        if (params != null) {
            if (!url.endsWith("?")) {
                url = url + "?";
            }

            for (String key : params.keySet()) {
                url = url + key + "=" + params.getString(key) + "&";
            }
        }
        return url;
    }

    /**
     * Returns Header for a given URL.
     *
     * @param url
     * @throws ParseException
     * @throws IOException
     */
    public Map<String, List<String>> getRequestHeader(String url)
            throws ParseException, IOException {
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        Map<String, List<String>> map = conn.getHeaderFields();
        conn.disconnect();
        return map;
    }
}
