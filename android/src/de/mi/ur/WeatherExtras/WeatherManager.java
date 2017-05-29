package de.mi.ur.WeatherExtras;

import android.app.Activity;
import android.content.Context;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 11.08.2016.
 * This class bundles all things concerning the weather functions of NNC
 *
 */
public class WeatherManager implements WeatherListener {
    private int currentWeather;
    private WeatherAsyncTask weatherTask;
    private LocationController locationController;
    private WeatherListener weatherListener;

    /*
     * In the constructor, the locationController is prompted to update the location, so the actions needing location-information
     * can be performed
     */
    public WeatherManager(Context context, Activity activity, WeatherListener listener) {
        locationController = new LocationController(context, activity);
        locationController.setCurrentPosition();
        weatherListener = listener;
    }

    /*
     * Generates the URL to request weather data for the current location
     */
    private String generateUrl(){
        return Constants.WEATHER_API_URL_1_LAT + locationController.getLatitude() + Constants.WEATHER_API_URL_2_LON + locationController.getLongitude() + Constants.WEATHER_API_URL_3;
    }


    /*
     * Starts the WeatherAsyncTask with the updated location-information in the URL
     */
    public void startCurrentWeatherGetter() {
        locationController.setCurrentPosition();
        String Url = generateUrl();
        weatherTask = new WeatherAsyncTask(this);
        weatherTask.execute(Url);
    }

    public int getCurrentWeather() {
        return currentWeather;
    }

    /*
     * Notifies the instance WeatherListener which was a parameter in the constructor
     * updates the instance variable currentWeather according to the information processed by the weatherTask
     */
    @Override
    public void onDownloadFinished() {
        currentWeather = weatherTask.getCurrentWeather();
        weatherListener.onDownloadFinished();
    }
}
