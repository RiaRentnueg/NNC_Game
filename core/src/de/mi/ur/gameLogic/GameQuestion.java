package de.mi.ur.gameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.sprites.AnswerPhone;
import de.mi.ur.states.PlayState;

public class GameQuestion {


    private String toSolve;

    private static String possAnswer1;
    private static String possAnswer2;
    private static String possAnswer3;
    private static String possAnswer4;
    public static boolean answerGenerated = false;

    private static String[] question;

    private static ArrayList<String> possAnswers;

    private BitmapFont toSolveBitmap;

    private BitmapFont number1;
    private BitmapFont number2;
    private BitmapFont number3;
    private BitmapFont number4;

    private BitmapFont possAnswer1Bitmap;
    private BitmapFont possAnswer2Bitmap;
    private BitmapFont possAnswer3Bitmap;
    private BitmapFont possAnswer4Bitmap;


    private MultipleChoiceListener multipleChoiceGenerator;
    private int numeral1Base;
    private int numeral2Base = 10;
    private int maxDigits;

    private boolean counted;

    private Random random;
    private boolean isBinary;



    public GameQuestion(MultipleChoiceListener multipleChoiceGenerator) {
        random = new Random();

        counted = false;
        this.multipleChoiceGenerator = multipleChoiceGenerator;



        toSolve = "";

        possAnswer1 = "";
        possAnswer2 = "";
        possAnswer3 = "";
        possAnswer4 = "";


        toSolveBitmap = new BitmapFont();
        toSolveBitmap.setUseIntegerPositions(false);

        number1 = new BitmapFont();
        number1.setUseIntegerPositions(false);

        number2 = new BitmapFont();
        number2.setUseIntegerPositions(false);

        number3 = new BitmapFont();
        number3.setUseIntegerPositions(false);

        number4 = new BitmapFont();
        number4.setUseIntegerPositions(false);

        possAnswer1Bitmap = new BitmapFont();
        possAnswer1Bitmap.setUseIntegerPositions(false);

        possAnswer2Bitmap = new BitmapFont();
        possAnswer2Bitmap.setUseIntegerPositions(false);

        possAnswer3Bitmap = new BitmapFont();
        possAnswer3Bitmap.setUseIntegerPositions(false);

        possAnswer4Bitmap = new BitmapFont();
        possAnswer4Bitmap.setUseIntegerPositions(false);
    }


    public ArrayList<String> generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();
        for (int i = ConstantsGame.POSS_ANSWER1_POS; i <= ConstantsGame.POSS_ANSWER4_POS; i++) {
            possAnswers.add(question[i]);
        }

        return possAnswers;
    }

    public static int getRightAnswer() {
        String rightAnswer = question[ConstantsGame.RIGHT_ANSWER_POS];
        if (rightAnswer.equals(possAnswer1)) {
            return possAnswers.indexOf(possAnswer1) + 1;
        }
        if (rightAnswer.equals(possAnswer2)) {
            return possAnswers.indexOf(possAnswer2) + 1;
        }
        if (rightAnswer.equals(possAnswer3)) {
            return possAnswers.indexOf(possAnswer3) + 1;
        }
        if (rightAnswer.equals(possAnswer4)) {
            return possAnswers.indexOf(possAnswer4) + 1;
        }
        return 0;
    }

    /**
     * this method chooses randomly, whether the next question is in the binary- or the hex-system.
     */
    private void binaryOrHex() {
        isBinary = random.nextBoolean();

        //binär oder Hex-Abfrage
        if (isBinary) {
            numeral1Base = 2;
            maxDigits = 6;
        } else {
            numeral1Base = 16;
            maxDigits = 2;
        }


    }

    /**
     * This method updates the questions. It generates randomly a new question and 4 possible answers
     */
    public void updateQuestions() {
        binaryOrHex();

        if (PlayState.isQuestionPhase() && !isCounted()) {

            question = multipleChoiceGenerator.getQuestionInfos(numeral1Base, numeral2Base, maxDigits, 0);
            if (isBinary) {

                toSolve = question[ConstantsGame.QUESTION_POS] + " (2)";
            } else {
                toSolve = question[ConstantsGame.QUESTION_POS] + " (16)";
            }
            possAnswers = generatePossAnswers();
            possAnswer1 = possAnswers.get(0);
            possAnswer2 = possAnswers.get(1);
            possAnswer3 = possAnswers.get(2);
            possAnswer4 = possAnswers.get(3);

            answerGenerated = true;
            AnswerPhone.resetCounted();



            setCounted();


        }
    }


    public void drawTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x + ConstantsGame.QUESTION_TOSOLVE_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        toSolveBitmap.setColor(Color.RED);

        number1.draw(batch, "1:", cam.position.x + ConstantsGame.QUESTION_POSSANS_1_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        number1.setColor(Color.RED);
        possAnswer1Bitmap.draw(batch, "   " + possAnswer1, cam.position.x + ConstantsGame.QUESTION_POSSANS_1_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer1Bitmap.setColor(Color.BLACK);
        number2.draw(batch, "2:", cam.position.x, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        number2.setColor(Color.RED);
        possAnswer2Bitmap.draw(batch, "   " + possAnswer2, cam.position.x, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer2Bitmap.setColor(Color.BLACK);
        number3.draw(batch, "3:", cam.position.x + ConstantsGame.QUESTION_POSSANS_3_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        number3.setColor(Color.RED);
        possAnswer3Bitmap.draw(batch, "   " + possAnswer3, cam.position.x + ConstantsGame.QUESTION_POSSANS_3_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer3Bitmap.setColor(Color.BLACK);
        number4.draw(batch, "4:", cam.position.x + ConstantsGame.QUESTION_POSSANS_4_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        number4.setColor(Color.RED);
        possAnswer4Bitmap.setColor(Color.BLACK);
        possAnswer4Bitmap.draw(batch, "   " + possAnswer4, cam.position.x + ConstantsGame.QUESTION_POSSANS_4_OFFSET, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);


    }

    public void dipose() {
        number1.dispose();
        possAnswer1Bitmap.dispose();
        number2.dispose();
        possAnswer2Bitmap.dispose();
        number3.dispose();
        possAnswer3Bitmap.dispose();
        number4.dispose();
        possAnswer4Bitmap.dispose();
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        counted = true;
    }

    public void resetCounted() {
        counted = false;
    }
}
