package de.mi.ur.WeatherExtras;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 11.08.2016.
 *
 * This class keeps the downloading of weather data away from the main thread and handles further processing of weather data
 */
public class WeatherAsyncTask extends AsyncTask<String, Integer, String> {
    private WeatherListener listener;
    private int currentWeather;

    public WeatherAsyncTask(WeatherListener listener) {
        this.listener = listener;
    }

    /*
     * The actual background task,
     * downloads data from given URL in params
     */
    @Override
    protected String doInBackground(String[] params) {
        String jsonString = "";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString += line;
                }
                br.close();
                is.close();
                conn.disconnect();
            } else {
                throw new IllegalStateException("HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    /*
     * handles post executive tasks and notifies the listener, that the download has been finished
     */
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        int currentWeatherId = getWeatherIdFromJson(result);
        currentWeather = calculateCurrentWeather(currentWeatherId);
        listener.onDownloadFinished();
    }

    /*
     * Gets the weatherId (an int value representing different states of weather) out of the JSON received as an answer
     */
    private int getWeatherIdFromJson(String text) {
        int weatherId = -1;
        try {
            JSONObject jsonObj = new JSONObject(text);
            JSONArray weatherArray = jsonObj.getJSONArray("weather");
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            weatherId = weatherObj.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherId;
    }


    /*
     * Transfers the weatherId into one of the four possible weather states in NNC (sunny, rainy, cloudy, snowing)
     * 2xx - 5xx different types of rain
     * 6xx different kinds of snow
     * 800 clear (=sunny)
     * 80x diffent kinds of clouds
     * rest: extremes (e.g. volcanic ash, tornado...) are not represented in NNC
     * For further information regarding the weather codes see http://openweathermap.org/weather-conditions
     */
    private int calculateCurrentWeather(int weatherId) {
        if (weatherId >= 200 && weatherId < 600) {
            return Constants.WEATHER_RAINY;
        } else if (weatherId >= 600 && weatherId < 700) {
            return Constants.WEATHER_SNOWY;
        } else if (weatherId == 800) {
            return Constants.WEATHER_SUNNY;
        } else {
            return Constants.WEATHER_CLOUDY;
        }
    }

    /*
     * Allows other classes to get the information on currentWeather
     */
    public int getCurrentWeather() {
        return currentWeather;
    }

}
