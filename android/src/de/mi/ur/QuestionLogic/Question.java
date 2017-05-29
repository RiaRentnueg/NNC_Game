package de.mi.ur.QuestionLogic;

import android.content.res.Resources;

import java.util.Random;

/**
 * Created by Anna-Marie on 02.08.2016.
 *
 * This class is the superclass for the questions, and makes it possible to have an instance variable where
 * the practice-questions can be saved, regardless of which kind of question it is.
 * The methods do not have any functionality.
 */
public abstract class Question {
    private static Random randomGen;
    private int numeral1Base;
    private int numeral2Base;
    private int maxDigits;

    public boolean isCorrectAnswer(String answer) {
        return false;
    }

    public String getQuestionString(Resources resources) {
        return "";
    }

    public String getRightAnswerString() {
        return "";
    }

    //numeral1Base ist die Basis des Fragesystems, numeral2Base ist die Basis des Antwortsystems
    public Question (int numeral1Base, int numeral2Base, int maxDigits){
        randomGen = new Random();
        this.numeral1Base = numeral1Base;
        this.numeral2Base = numeral2Base;
        this.maxDigits = maxDigits;
    }


    public String[] generatePossAnswers() {
        return null;
    }

    public String getQuestion(){
        return null;
    }

    public int getNumeral1Base(){
        return this.numeral1Base;
    }

    public int getNumeral2Base(){
        return this.numeral2Base;
    }

    public int getMaxDigits(){
        return this.maxDigits;
    }

    public Random getRandomGenerator(){
        return randomGen;
    }
}
