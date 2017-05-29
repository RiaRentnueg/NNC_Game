package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;

/**
 * Created by maxiwindl on 12.09.16.
 */
public class GameOverState extends State {


    private Texture gameOver;

    private HighscoreListener highscoreListener;
    private DialogListener dialogListener;

    private int rank;
    private int points;
    private Score score;



    public GameOverState(GameStateManager gameManager) {
        super(gameManager);


        Score.state = 4;

        cam.setToOrtho(false, ConstantsGame.SCREEN_WIDTH, ConstantsGame.SCREEN_HEIGHT);

        gameOver = new Texture("game_over.png");

        this.highscoreListener = gameManager.getHighscoreListener();
        this.dialogListener = gameManager.getDialogListener();
        this.score = PlayState.getScore();
        points = (int) score.getCurrentScorePoints();
        rank = highscoreListener.checkIfNewHighscore(points);
        if (rank != -1) {
            dialogListener.showHighscoreDialog();


            while (!dialogListener.getDialogDone()) {
                //do nothing / wait
            }

            String userName = dialogListener.getUserName();

            highscoreListener.saveHighscoreToDatabase(rank, points, userName);

        }



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
        spriteBatch.begin();
        spriteBatch.draw(gameOver, cam.position.x - gameOver.getHeight() / 2 - ConstantsGame.GAME_OVER_OFFSET_X, cam.position.y - ConstantsGame.GAME_OVER_OFFSET_Y);
        spriteBatch.end();

    }

    @Override
    public void dispose() {

        gameOver.dispose();


    }
}


