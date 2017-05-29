package de.mi.ur;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.Dialogs.HighscoreDialog;
import de.mi.ur.Dialogs.MultipleChoiceDialog;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;

public class AndroidLauncher extends AndroidApplication implements MultipleChoiceListener, WeatherDataListener, DialogListener {
	private int currentWeather;
	private NNCDatabase db;
	private HighscoreDialog highscoreDialog;
	private MultipleChoiceDialog multipleChoiceDialog;
	private boolean backgroundMusic;
	private boolean soundEffects;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			currentWeather = extras.getInt(Constants.CURRENT_WEATHER);
			backgroundMusic = extras.getBoolean(Constants.BACKGROUND_MUSIC, true);
			soundEffects = extras.getBoolean(Constants.SOUND_EFFECTS, true);
			System.out.println("AndroidLaunecher: BackgorundMusik: " + backgroundMusic + " SoundEffects: " + soundEffects);
		}
		db = new NNCDatabase(this);
		db.open();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NerdyNumeralChallenge(this, this, db, this), config);
	}

	/*
	 *This method returns a String Array which contains information for the multipleChoiceQuestions in the game.
	 */
	@Override
	public String[] getQuestionInfos(int numeral1Base, int numeral2Base, int maxDigits, int difficulty) {
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(numeral1Base, numeral2Base, maxDigits);
		String[] possAnswers = question.generatePossAnswers();
		String[] multipleChoiceQuestionInfos = {question.getQuestion(), question.getRightAnswerString(), possAnswers[0], possAnswers[1], possAnswers[2], possAnswers[3]};
		return multipleChoiceQuestionInfos;
	}


	/*
	 * This method returns the current weather.
	 */
	@Override
	public int getCurrentWeather() {
		return currentWeather;
	}

	/*
	 *This method shows a new highscoreDialog.
	 */
	@Override
	public void showHighscoreDialog() {
		highscoreDialog = new HighscoreDialog();
		highscoreDialog.show(getFragmentManager(), "My HighscoreDialog");
	}

	/*
	 *This method returns the boolean dialogDone from the highscoreDialog class.
	 */
	@Override
	public boolean getDialogDone() {
		if (highscoreDialog != null) {
			return highscoreDialog.getDialogDone();
		} else {
			return true;
		}

	}

	/*
	 *This method returns the userName.
	 */
	@Override
	public String getUserName() {
		return highscoreDialog.getUserName();
	}

	/*
	 *This method shows a new multipleChoiceDialog
     */
	@Override
	public void showMultipleChoiceDialog() {
		multipleChoiceDialog = new MultipleChoiceDialog();
		multipleChoiceDialog.show(getFragmentManager(), "My MultipleChoiceDialog");
	}

	/*
	 *This method calls the dismissDialog method from the MultipleChoiceDialog class.
	 */
	@Override
	public void dismissDialog() {
		multipleChoiceDialog.dismissDialog();
	}

	/*
	 *This method returns the boolean rightAswer from the MultipeChoiceDialog class.
	 */
	@Override
	public boolean getRightDialogAnswer() {
		return multipleChoiceDialog.getRightAnswer();
	}

	/*
	 *This method returns the boolean wrongAnswer from the MultipleChoiceDialog class.
	 */
	@Override
	public boolean getWrongDialogAnswer() {
		return multipleChoiceDialog.getWrongAnswer();
	}

	/*
	 *This method returns the boolean backgroundMusic which is saved in the settings.
	 */
	@Override
	public boolean getBackgroundMusic() {
		return backgroundMusic;
	}

	/*
	 *This method returns the boolean soundEffects which is saved in the settings.
	 */
	@Override
	public boolean getSoundEffects() {
		return soundEffects;
	}
}
