package de.mi.ur.QuestionLogic;

import java.util.ArrayList;
import java.util.Collections;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class MultipleChoiceQuestion extends Question {
    private String question;
    private String rightAnswer;

    public MultipleChoiceQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);

        this.question = NumeralConverter.generateNumWithMaxDigits(numeral1Base, maxDigits);
        this.rightAnswer = NumeralConverter.convertFromNumeralToNumeral(this.question, numeral1Base, numeral2Base);
    }

    /*
     * generates the possible wrong Answers saves them into an ArrayList together with the right answer.
     * The ArrayList is shuffled so that the right answer is not always at the same position
     */
    public String[] generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();
        for (int i = 0; i < Constants.NUM_WRONG_ANSWERS_MULTIPLE_CHOICE; i++) {
            String possAnswer = NumeralConverter.generateNumBelowMax(getNumeral2Base(), (int) Math.pow(getNumeral1Base(), getMaxDigits()));
            while (possAnswers.contains(possAnswer) || possAnswer.equals(rightAnswer)) {
                possAnswer = NumeralConverter.generateNumBelowMax(getNumeral2Base(), (int) Math.pow(getNumeral1Base(), getMaxDigits()));
            }
            if (!possAnswers.contains(possAnswer)) {
                possAnswers.add(possAnswer);
            }
        }
            possAnswers.add(rightAnswer);
            Collections.shuffle(possAnswers);
            return possAnswers.toArray(new String[4]);

    }

    public String getQuestion(){
        return question;
    }

    public String getRightAnswerString(){
        return rightAnswer;
    }

    @Override
    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }

}
