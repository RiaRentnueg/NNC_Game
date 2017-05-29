package de.mi.ur.AndroidCommunication;

/**
 * Created by Anna-Marie on 31.08.2016.
 */
public interface HighscoreListener {


    public int checkIfNewHighscore(int points);

    public void saveHighscoreToDatabase(int rank, int points, String userName);



}

