package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class MenueState extends State {

    private Texture background;

    public MenueState(GameStateManager gameManager) {
        super(gameManager);
        Score.state = 4;
        cam.setToOrtho(false, ConstantsGame.SCREEN_WIDTH, ConstantsGame.SCREEN_HEIGHT);
        background = new Texture("menu_state.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameManager.set(new PlayState(gameManager));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        /** the spriteBatch needs to be opened and closed before and after using. It's like a box:
         * it gets opened, everything is put into it, and then it gets closed again.
         */
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, ConstantsGame.SCREEN_WIDTH, ConstantsGame.SCREEN_HEIGHT);
        spriteBatch.end();

    }

    @Override
    public void dispose() {
        background.dispose();


    }
}
