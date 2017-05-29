package de.mi.ur.WeatherExtras;

/**
 * Created by Anna-Marie on 18.08.2016.
 *
 * Is implemented by WeatherManager and GameMainActivity
 * Communicates when the new Weatherdata has been downloaded and processed
 */
public interface WeatherListener {

    public void onDownloadFinished();
}
