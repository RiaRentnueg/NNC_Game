package de.mi.ur;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class Constants {


    public static final int WEATHER_SUNNY = 0;
    public static final int WEATHER_CLOUDY = 1;
    public static final int WEATHER_RAINY = 2;
    public static final int WEATHER_SNOWY = 3;
    public static final int WEATHER_DEFAULT = 0;

    //Extra für AndroidLauncher
    public static final String CURRENT_WEATHER = "current weather";
    public static final String BACKGROUND_MUSIC = "background music";
    public static final String SOUND_EFFECTS = "sound effects";

    //Permission
    public static final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 111;



    //Questions
    public static final int NUM_WRONG_ANSWERS_MULTIPLE_CHOICE = 3;
    public static final int MAX_NUMERAL_BASE = 16;
    public static final int MIN_NUMERAL_BASE = 2;
    public static final int DEFAULT_NUMERAL_BASE = 10;


    //MultipleChoiceDialog
    public static final int MULTIPLE_CHOICE_DIALOG_FIRST_NUMERAL_BASE = 2;
    public static final int MULTIPLE_CHOICE_DIALOG_SECOND_NUMERAL_BASE = 10;
    public static final int MULTIPLE_CHOICE_DIALOG_QUESTION_LENGTH = 6;
    public static final int DIALOG_SHOW_TIME_IN_SECONDS = 5;

    //WeatherExtras

    public static final String API_ID = "50ddb65d4b6d51050e5844a4284d6d46";
    public static final String DEFAULT_LATITUDE = "49";
    public static final String DEFAULT_LONGITUDE = "12";
    public static final String WEATHER_API_URL_1_LAT = "http://api.openweathermap.org/data/2.5/weather?lat=";
    public static final String WEATHER_API_URL_2_LON = "&lon=";
    public static final String WEATHER_API_URL_3 = "&appid=" + API_ID;


    //Font
    public static final String FONT = "cantarell_font.ttf";



}
