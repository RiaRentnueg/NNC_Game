package de.mi.ur.AndroidCommunication;

/**
 * Created by Lydia on 21.09.2016.
 */
public interface DialogListener {

    public void showHighscoreDialog();

    public boolean getDialogDone();

    public String getUserName();

    public void showMultipleChoiceDialog();

    public boolean getRightDialogAnswer();

    public boolean getWrongDialogAnswer();

    public void dismissDialog();

    public boolean getBackgroundMusic();

    public boolean getSoundEffects();
}
