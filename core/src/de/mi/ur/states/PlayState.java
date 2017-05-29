package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.GameQuestion;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.sprites.AnswerPhone;
import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Obstacle;
import de.mi.ur.sprites.Pit;
import de.mi.ur.sprites.Woman;

/**
 * Created by maxiwindl on 31.07.16.
 * <p/>
 * Vor Abgeben noch GameQuestion1 und das HoffentlichNurVoruebergehend-Package löschen!
 * <p/>
 * Obstacle funktioniert jetzt.
 */
public class PlayState extends State {

    private static boolean isQuestionMode;

    private static Nerd nerd;
    private Texture background;
    public static Texture ground;
    private static Score score;
    private Texture sun;
    private Random random;
    public static boolean sunny;
    private boolean snowy;
    private Music music;
    private GameQuestion gameQuestion;

    public static boolean alreadChanged = true;

    private Texture flyingPhone1;
    private Texture flyingPhone2;
    private Texture flyingPhone3;
    private Texture flyingPhone4;

    private AnswerPhone phone1;
    private AnswerPhone phone2;
    private AnswerPhone phone3;
    private AnswerPhone phone4;

    private Vector2 groundPos1, groundPos2;
    private Vector2 bgPos1, bgPos2;
    private Array<Obstacle> obstacles;


    private enum CurrentState {
        Running, Paused
    }


    private static float timeSum = 0;

    private HighscoreListener highscoreListener;
    private DialogListener dialogListener;

    private CurrentState currentState = CurrentState.Running;
    public static boolean soundEffects;



    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        initListeners();
        initMusic();
        initScore();
        initGraphics();

        isQuestionMode = false;
        gameQuestion = new GameQuestion(gameManager.getMultipleChoiceListener());
    }

    /*
     * Gets the listeners which are relevant for the Playstate
     */
    private void initListeners() {
        this.dialogListener = gameManager.getDialogListener();
        this.highscoreListener = gameManager.getHighscoreListener();

    }

    /*
     * Starts the music loop
     */
    private void initMusic() {
        soundEffects = dialogListener.getSoundEffects();
        music = Gdx.audio.newMusic(Gdx.files.internal("ZeroOne403.mp3"));
        music.setLooping(true);
        music.setVolume(0.6f);
        if (dialogListener.getBackgroundMusic()) {
            music.play();
        }
    }

    /*
     * Inits the graphical parts of the game
     */
    private void initGraphics() {
        nerd = new Nerd(ConstantsGame.NERD_X, ConstantsGame.NERD_Y);
        initPhones();
        initWeather();
        initObstacles();
    }


    /*
     * inits all graphic-parts which change according to which weather it is
     */
    private void initWeather() {
        initGround();
        sun = new Texture("sun.png");
        background = getBackgroundWeather(gameManager);
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x - (cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);
        bgPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), ConstantsGame.BACKGROUND_Y_POS);
        bgPos2 = new Vector2(cam.position.x - (cam.viewportWidth / 2) + background.getWidth(), ConstantsGame.BACKGROUND_Y_POS);
    }

    private void initGround() {
        if (snowy) {
            ground = new Texture("ground_snow.png");
        } else {
            ground = new Texture("ground_anton.png");
        }
    }

    private void initPhones() {
        flyingPhone1 = new Texture("phone_answer_new_1.png");
        flyingPhone2 = new Texture("phone_different_animation_2.png");
        flyingPhone3 = new Texture("phone_answer_new_3.png");
        flyingPhone4 = new Texture("phone_answer_new_4.png");

        phone1 = new AnswerPhone(ConstantsGame.PHONE1_X, ConstantsGame.PHONES_Y, flyingPhone1);
        phone2 = new AnswerPhone(ConstantsGame.PHONE2_X, ConstantsGame.PHONES_Y, flyingPhone2);
        phone3 = new AnswerPhone(ConstantsGame.PHONE3_X, ConstantsGame.PHONES_Y, flyingPhone3);
        phone4 = new AnswerPhone(ConstantsGame.PHONE4_X, ConstantsGame.PHONES_Y, flyingPhone4);
    }

    private void initScore() {
        score = new Score();
        score.startTimer();
        random = new Random();
    }

    /*
     * inits the obstacles, the succession of pits and women is random
     * obstacles are initially positioned outside the screen
     */
    private void initObstacles() {
        obstacles = new Array<Obstacle>();
        for (int i = 0; i < ConstantsGame.TOTAL_NUM_OBSTACLES; i++) {
            if (random.nextInt(2) == ConstantsGame.PIT_TYPE) {
                obstacles.add(new Pit(cam.position.x + (cam.viewportWidth / 2) + ConstantsGame.PIT_WIDTH * 2));
            } else {
                obstacles.add(new Woman(cam.position.x + (cam.viewportWidth / 2) + ConstantsGame.WOMAN_WIDTH * 2));
            }
            setObstaclesPositionOutsideScreen();
        }
    }

    public static Score getScore() {
        return score;
    }


    /*
     * The nerd jumps when the user taps the screen
     */
    @Override
    protected void handleInput() {
        if (Nerd.jumpFinished) {
            if (Gdx.input.justTouched()) {
                nerd.jump();
                Nerd.jumpFinished = false;
            }
        }
    }

    @Override
    //calculations for the render method
    public void update(float dt) {
        switch (currentState) {
            case Running:
                updatePlayState(dt);
                break;
            case Paused:
                //don't Update
                dialogListener.dismissDialog();
                if (dialogListener.getWrongDialogAnswer()) {
                    Score.updateHeart(gameManager, true);
                }
                if (dialogListener.getRightDialogAnswer() || dialogListener.getWrongDialogAnswer()) {
                    currentState = CurrentState.Running;
                } else {
                    currentState = CurrentState.Paused;
                }
                break;
            default:
                updatePlayState(dt);
        }
    }

    public void updatePlayState(float dt) {
        updateTimeSum(dt);
        handleInput();
        updateGraphics(dt);
        score.updateScore(gameManager);
        gameQuestion.updateQuestions();

        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        cam.update();
    }

    /*
     * Is responsible for the changing between GamePhases (QuestionPhase and ObstaclePhase)
     * The deltatimes between frames are summed up and once the reach a certain threshold, the phase is toggled and the timeSum reset to 0
     */
    private void updateTimeSum(float dt) {
        timeSum = timeSum + dt;
        if (timeSum > ConstantsGame.PHASE_DURATION) {
            togglePhase();
            gameQuestion.resetCounted();
            timeSum = 0;
        }
    }

    private void updateGraphics(float dt) {
        updateGround();
        updateBG();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty());
        updatePhones(dt);
        updateObstacles();
    }


    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    private void updateBG() {
        if (cam.position.x - (cam.viewportWidth / 2) > bgPos1.x + background.getWidth()) {
            bgPos1.add(background.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > bgPos2.x + background.getWidth()) {
            bgPos2.add(background.getWidth() * 2, 0);
        }
    }

    /*
     * Handles the case when a user jumps to choose an Answerphone
     * If the answer is wrong, a heart is lost
     * If the answer is right, and all hearts are full, the user gets 10 points,
     *                         else they get a heart refilled
     * In both cases, the gamePhase is changed
     */
    public void handleUserAnswers() {

        if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
            alreadChanged = false;
            togglePhase();
        }

        if (GameQuestion.getRightAnswer() == 1) {
            if (phone1.collides(nerd.getBounds()) && !phone1.isCounted()) {
                setCountedAllPhones();
                Score.refillHeart();
            } else if ((phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                setCountedAllPhones();
                Score.updateHeart(gameManager, true);
            }
        }
        if (GameQuestion.getRightAnswer() == 2) {
            if (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) {
                setCountedAllPhones();
                Score.refillHeart();

            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                setCountedAllPhones();
                Score.updateHeart(gameManager, true);
            }
        }
        if (GameQuestion.getRightAnswer() == 3) {
            if (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) {
                setCountedAllPhones();
                Score.refillHeart();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                setCountedAllPhones();
                Score.updateHeart(gameManager, true);
            }
        }
        if (GameQuestion.getRightAnswer() == 4 && !phone4.isCounted()) {
            if (phone4.collides(nerd.getBounds()) && !phone4.isCounted()) {
                setCountedAllPhones();
                Score.refillHeart();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && phone3.isCounted())) {
                setCountedAllPhones();
                Score.updateHeart(gameManager, true);
            }
        }
    }

    /*
     * set all phones counted
     */
    private void setCountedAllPhones() {
        phone1.setCounted();
        phone2.setCounted();
        phone3.setCounted();
        phone4.setCounted();
    }

    /*
     * updates the position of the phones.
     * If they are counted or it is ObstaclePhase, they are positioned outside the screen so they are not visible to the user
     */
    private void updatePhones(float dt) {
        phone1.update(dt);
        phone2.update(dt);
        phone3.update(dt);
        phone4.update(dt);

        if (phone1.isCounted() || phone2.isCounted() || phone3.isCounted() || phone4.isCounted() || !isQuestionPhase()) {
            setPhonePositionOutsideScreen(phone1, ConstantsGame.PHONE1_X_OUTSIDE_SCREEN);
            setPhonePositionOutsideScreen(phone2, ConstantsGame.PHONE2_X_OUTSIDE_SCREEN);
            setPhonePositionOutsideScreen(phone3, ConstantsGame.PHONE3_X_OUTSIDE_SCREEN);
            setPhonePositionOutsideScreen(phone4, ConstantsGame.PHONE4_X_OUTSIDE_SCREEN);
        }

        updatePhone(phone1, flyingPhone1);
        updatePhone(phone2, flyingPhone2);
        updatePhone(phone3, flyingPhone3);
        updatePhone(phone4, flyingPhone4);

        if (GameQuestion.answerGenerated) {
            handleUserAnswers();
        }


    }

    private void setPhonePositionOutsideScreen(AnswerPhone phone, int distanceFromFirstPhone) {
        phone.getPosition().set((cam.position.x + cam.viewportWidth / 2) + distanceFromFirstPhone, phone.getY());
    }

    /*
     * Changes the position of the phone
     */
    private void updatePhone(AnswerPhone phone, Texture flyingPhone) {
        if (cam.position.x - (cam.viewportWidth / 2) > phone.getPosition().x + flyingPhone.getWidth()) {
            phone.getPosition().add(flyingPhone.getWidth() * ConstantsGame.PHONE_X_TIMES_WIDTH, ConstantsGame.PHONE_Y_TO_ADD);
        }
    }

    /*
     * Sets all the obstacles outside the screen so they are invisible to the user
     */
    private void setObstaclesPositionOutsideScreen() {
        Obstacle firstObstacle = obstacles.get(0);
        firstObstacle.reposition(cam.position.x + (cam.viewportWidth / 2) + firstObstacle.getTexture().getWidth() * 2);
        for (int i = 1; i < obstacles.size; i++) {
            int distance = generateNewDistance(score.getCurrentScorePoints());
            Obstacle obstacle = obstacles.get(i);
            Obstacle before = obstacles.get(i - 1);
            obstacle.reposition(before.getObstaclePos().x + (before.getTexture().getWidth() * 2 + obstacle.getTexture().getWidth() * 2 + distance));
        }

    }


    /*
     * updates the position of the obstacles and handles collision with the nerd
     */
    private void updateObstacles() {
        if (isQuestionPhase()) {
            setObstaclesPositionOutsideScreen();
        } else {
            for (int i = 0; i < obstacles.size; i++) {
                Obstacle obstacle = obstacles.get(i);
                repositionObstacles(i, obstacle);
                if (obstacle.collides(nerd.getBounds()) && !obstacle.isCounted()) {
                    alreadChanged = false;
                    switch (obstacle.getType()) {
                        case ConstantsGame.PIT_TYPE:
                            gameEnds();
                            break;
                        case ConstantsGame.WOMAN_TYPE:
                            possSave(obstacle);
                            break;
                        default:
                    }
                }
            }
        }
    }

    /*
     * Adjusts the position of the obstacles and uses a new random distance between the obstacles
     */
    private void repositionObstacles(int i, Obstacle obstacle) {
        Obstacle before;
        if (i != 0) {
            before = obstacles.get(i - 1);
        } else {
            before = obstacles.get(obstacles.size - 1);
        }

        if (cam.position.x - (cam.viewportWidth / 2) > obstacle.getObstaclePos().x + obstacle.getTexture().getWidth()) {
            int distance = generateNewDistance(score.getCurrentScorePoints());
            obstacle.reposition(before.getObstaclePos().x + (before.getTexture().getWidth() * 2 + obstacle.getTexture().getWidth() * 2 + distance));
            obstacle.resetCounted();
        }
    }

    /*
     * This method is called when the current game is over
     */
    private void gameEnds() {
        cam.setToOrtho(false, ConstantsGame.DEFAULT_CAM_WIDTH, ConstantsGame.DEFAULT_CAM_HEIGHT);
        if (soundEffects) {
            Score.gameOver.play();
        }
        gameManager.set(new GameOverState(gameManager));
    }

    /*
     * This method is called when the nerd runs into a woman and has the possibility to save his life by answering a question,
     * which pops up in a dialog.
     *
     */
    private void possSave(Obstacle obstacle) {
        obstacle.setCounted();
        currentState = CurrentState.Paused;
        dialogListener.showMultipleChoiceDialog();
    }


    /*
     * generates a new random distance to go between obstacles
     * The maximum possible distance becomes smaller the more points the user already has -> difficulty becomes higher
     */
    private int generateNewDistance(long scorePoints) {
        int newInt;
        if (scorePoints < ConstantsGame.SCORE_START) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_LONG);
        } else if (scorePoints < ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE * 2) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_MEDIUM_LONG);
        } else if (scorePoints < ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE * 4) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_MEDIUM_SHORT);
        } else {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_SHORT);
        }

        if (newInt >= ConstantsGame.MIN_DISTANCE) {
            return newInt;
        } else {
            return generateNewDistance(scorePoints);
        }
    }


    /*
     * returns the x-velocity of the nerd, which is higher, the more points the user already has
     */
    private float increaseDifficulty() {
        long value = score.getCurrentScorePoints();
        if (value < ConstantsGame.SCORE_START) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        } else if (value > ConstantsGame.SCORE_START && value <= ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + ConstantsGame.VELOCITY_ADDED;
        } else if (value > ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 2)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 2);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 2) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 3)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 3);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 3) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 4)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 4);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 4) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 5)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 5);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 5)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 6);
        } else {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        }
    }


    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        drawGraphics(spriteBatch);
        score.renderScore(spriteBatch, cam);
        score.showPointUpdate(spriteBatch, cam);
        if (isQuestionMode) {
            gameQuestion.drawTasks(spriteBatch, cam);
        }
        score.showPointUpdate(spriteBatch, cam);
        spriteBatch.end();
    }

    /*
     * draws the basic graphic elements on the screen
     */
    private void drawGraphics(SpriteBatch spriteBatch) {
        spriteBatch.draw(background, bgPos1.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(background, bgPos2.x, ConstantsGame.BACKGROUND_Y_POS);
        if (sunny) {
            spriteBatch.draw(sun, cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X, cam.position.y + ConstantsGame.SUN_Y_POS);
        }
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());
        if (!phone1.isCounted() || !phone2.isCounted() || !phone3.isCounted() || !phone4.isCounted()) {
            drawPhones(spriteBatch);
        }

        for (Obstacle obstacle : obstacles) {
            spriteBatch.draw(obstacle.getTexture(), obstacle.getObstaclePos().x, obstacle.getObstaclePos().y);
        }
    }

    /*
     * draws the 4 phones on the screen
     */
    private void drawPhones(SpriteBatch spriteBatch) {
        spriteBatch.draw(phone1.getTexture(), phone1.getX(), phone1.getY());
        spriteBatch.draw(phone2.getTexture(), phone2.getX(), phone2.getY());
        spriteBatch.draw(phone3.getTexture(), phone3.getX(), phone3.getY());
        spriteBatch.draw(phone4.getTexture(), phone4.getX(), phone4.getY());
    }

    /*
     * the graphical objects are deleted
     */
    @Override
    public void dispose() {
        music.dispose();
        nerd.dispose();
        background.dispose();
        ground.dispose();
        flyingPhone1.dispose();
        flyingPhone2.dispose();
        flyingPhone3.dispose();
        flyingPhone4.dispose();
        if (sunny) {
            sun.dispose();
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        score.dispose();
        gameQuestion.dipose();


    }

    //adjusts the weather according to the current weather
    private Texture getBackgroundWeather(GameStateManager gameManager) {
        int currentWeather = gameManager.getWeatherDataListener().getCurrentWeather();
        String texturePath;
        switch (currentWeather) {
            case ConstantsGame.WEATHER_SUNNY:
                sunny = true;
                snowy = false;
                texturePath = "bg_sunny.png";
                break;
            case ConstantsGame.WEATHER_RAINY:
                sunny = false;
                snowy = false;
                texturePath = "bg_rainy.png";
                break;
            case ConstantsGame.WEATHER_CLOUDY:
                sunny = false;
                snowy = false;
                texturePath = "bg_cloudy.png";
                break;
            case ConstantsGame.WEATHER_SNOWY:
                sunny = false;
                snowy = true;
                texturePath = "bg_snow.png";
                break;
            default:
                texturePath = "bg_sunny.png";
        }
        return new Texture(texturePath);
    }

    /*
     * returns true if the game is in questionPhase and false if it is in obstaclePhase
     */
    public static boolean isQuestionPhase() {
        return isQuestionMode;
    }

    /*
     *switches between the QuestionPhase and the ObstaclePhase in the game
     */
    public static void togglePhase() {
        isQuestionMode = !isQuestionMode;
        timeSum = 0;
    }


}

