package de.mi.ur.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class GameStateManager {
    /**
     * manages the states of the game. It does that with a stack. E.g. the player hits a pit and a new state is put ontop. When the player taps again
     * the playState restarts and the gameOverState gets removed.
     */


    private Stack<State> states;
    private MultipleChoiceListener multipleChoiceListener;
    private WeatherDataListener weatherDataListener;
    private HighscoreListener highscoreListener;
    private DialogListener dialogListener;

    public GameStateManager(MultipleChoiceListener multipleChoiceListener, WeatherDataListener weatherDataListener, HighscoreListener highscoreListener, DialogListener dialogListener) {
        this.multipleChoiceListener = multipleChoiceListener;
        this.weatherDataListener = weatherDataListener;
        this.highscoreListener = highscoreListener;
        this.dialogListener = dialogListener;

        states = new Stack <State>();

    }
    public void push(State state) {
        states.push(state);
    }

    public void pop () {
        states.pop().dispose();
    }

    public void set (State state) {
        states.pop().dispose();
        states.push(state);

    }

    //only the state on top of the stack needs to be updatet, so it does first peek and then update
    public void update (float flt) {
        states.peek().update(flt);
    }

    //same functionality with the peek() as the update method
    public void render (SpriteBatch batch) {
        states.peek().render(batch);
    }

    public MultipleChoiceListener getMultipleChoiceListener(){
        return multipleChoiceListener;
    }

    public WeatherDataListener getWeatherDataListener(){
        return weatherDataListener;
    }

    public HighscoreListener getHighscoreListener() {
        return highscoreListener;
    }

    public DialogListener getDialogListener() {
        return dialogListener;
    }
}
