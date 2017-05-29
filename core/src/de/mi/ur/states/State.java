package de.mi.ur.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 31.07.16.
 */
public abstract class State {
    //camera to locate a position in the world
    public static OrthographicCamera cam;

    //handles all the different states of the game with a stack
    protected GameStateManager gameManager;

    protected State (GameStateManager gameManager) {
        this.gameManager = gameManager;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, ConstantsGame.DEFAULT_CAM_WIDTH, ConstantsGame.DEFAULT_CAM_HEIGHT);
    }

    protected abstract void handleInput ();

    //does all the calculations which are necessary for the render method
    public abstract void update (float flt);

    //draws everything like sprites and bitmapfonts on the screen
    public abstract void render (SpriteBatch spriteBatch);

    //to get rid of all the files, which are no longer needed
    public abstract void dispose ();
}
