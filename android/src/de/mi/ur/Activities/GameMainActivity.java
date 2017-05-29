package de.mi.ur.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.AndroidLauncher;
import de.mi.ur.Constants;
import de.mi.ur.R;
import de.mi.ur.WeatherExtras.WeatherListener;
import de.mi.ur.WeatherExtras.WeatherManager;

public class GameMainActivity extends AppCompatActivity implements View.OnClickListener, WeatherDataListener, WeatherListener {

    private Button buttonStartGame;
    private Button buttonWeather;
    private Button buttonViewHighscore;
    private Button buttonHelp;

    private WeatherManager weatherManager;

    private Toolbar myToolbar;
    private SharedPreferences sharedPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_activity);
        setupUI();
        setupToolbar();
        weatherManager = new WeatherManager(this, this, this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.game_main_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.game_main_toolbar_headline);
        //myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        /*myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    /*
     * This method shows the menu (only settings icon here) in the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
     * This method is the onClickListener and onClick-Method for the menu-item (settings icon)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(GameMainActivity.this, SettingsActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        buttonStartGame = (Button) findViewById(R.id.game_start_button);
        buttonStartGame.setOnClickListener(this);
        buttonWeather = (Button) findViewById(R.id.game_update_weather_button);
        buttonWeather.setOnClickListener(this);
        buttonViewHighscore = (Button) findViewById (R.id.game_highscore_button);
        buttonViewHighscore.setOnClickListener(this);
        buttonHelp = (Button) findViewById (R.id.game_help_button);
        buttonHelp.setOnClickListener(this);

    }

    /*
     * Handles Click-Events (mostly activity starting)
     */
    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.game_start_button:
                i = new Intent(GameMainActivity.this, AndroidLauncher.class);
                i.putExtra(Constants.CURRENT_WEATHER, weatherManager.getCurrentWeather());
                i.putExtra(Constants.BACKGROUND_MUSIC, getBackgroundMusic());
                i.putExtra(Constants.SOUND_EFFECTS, getSoundEffects());
                break;
            case R.id.game_update_weather_button:
                handleWeatherButtonClick();
                break;
            case R.id.game_highscore_button:
                i = new Intent(GameMainActivity.this, HighscoreActivity.class);
                break;
            case R.id.game_help_button:
                i = new Intent(GameMainActivity.this, GameHelpActivity.class);
                break;
            default:
                break;
        }
        if(i!=null){
            startActivity(i);
        }
    }

    /*
     * requests new weather information (if relevant permissions were given
     * else informs the user that default weather was set)
     */
    private void handleWeatherButtonClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            weatherManager.startCurrentWeatherGetter();
        } else {
            requestWeatherPermission(this);
            String toastMessage = getResources().getString(R.string.default_weather_toast);
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    /*
     * Checks the parameter, which kind of weather it is and generates appropriate content for user information
     */
    private String convertToWeatherName(int weatherNumber) {
        switch (weatherNumber) {
            case Constants.WEATHER_SUNNY:
                return getResources().getString(R.string.weather_sunny);
            case Constants.WEATHER_CLOUDY:
                return getResources().getString(R.string.weather_cloudy);
            case Constants.WEATHER_RAINY:
                return getResources().getString(R.string.weather_rainy);
            case Constants.WEATHER_SNOWY:
                return getResources().getString(R.string.weather_snowy);
            default:
                return getResources().getString(R.string.weather_default);
        }
    }

    @Override
    public int getCurrentWeather() {
        return weatherManager.getCurrentWeather();
    }

    /*
     * Requests the permission to access location data (fine location is required to use the gps-sensor.
     */
    public static void requestWeatherPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            createInformationDialog(activity);
        }
    }


    /*
     * Shows an information dialog to explain why the location permission is needed.
     */
    private static void createInformationDialog(Activity activity){
        final Activity activity1 = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.location_permission_explanation).setTitle(R.string.location_permission_headline);
        builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(activity1, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
            }
        });
        AlertDialog permissionInfoDialog = builder.create();
        permissionInfoDialog.show();
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    weatherManager.getCurrentWeather();
                }
                break;
            }
        }
    }


    /*
     * If the download and processing of new weather data is finished, this method is called to notify the user.
     */
    @Override
    public void onDownloadFinished() {
        String weather = convertToWeatherName(weatherManager.getCurrentWeather());
        String toastMessage = getResources().getString(R.string.weather_toast_1) + weather + getResources().getString(R.string.weather_toast_2);
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    private boolean getBackgroundMusic() {
        return sharedPref.getBoolean("pref_music", true);
    }

    private boolean getSoundEffects() {
        return sharedPref.getBoolean("pref_sound_effects", true);
    }


}

