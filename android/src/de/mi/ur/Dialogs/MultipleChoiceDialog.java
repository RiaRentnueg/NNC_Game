package de.mi.ur.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import de.mi.ur.Constants;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;
import de.mi.ur.R;


/**
 * Created by Lydia on 25.09.2016.
 */
public class MultipleChoiceDialog extends DialogFragment {


    private MultipleChoiceQuestion currentQuestion;
    private String[] items;
    private TextView messageTextView, questionTextView;
    private RadioGroup radioGroup;
    private boolean rightAnswer, wrongAnswer;
    private long startTime;
    private LayoutInflater inflater;
    private View dialogView;

    /*
     *This method creates a new dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        init();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogView = inflater.inflate(R.layout.multiple_choice_dialog, null);
        messageTextView = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_message);
        messageTextView.setText(R.string.multiple_choice_dialog_message);
        questionTextView = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_question);
        questionTextView.setText(currentQuestion.getQuestion());
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.multiple_choices_dialog);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton button = ((RadioButton) radioGroup.getChildAt(i));
            button.setText(items[i]);
        }
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if the user clicks on the ok button, the dialog disappears and the onDismiss method is called automatically.
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    /*
     *This method initialises values for the Class
     */
    private void init() {
        startTime = System.currentTimeMillis();
        rightAnswer = false;
        wrongAnswer = false;
        inflater = getActivity().getLayoutInflater();
        currentQuestion = new MultipleChoiceQuestion(Constants.MULTIPLE_CHOICE_DIALOG_FIRST_NUMERAL_BASE, Constants.MULTIPLE_CHOICE_DIALOG_SECOND_NUMERAL_BASE, Constants.MULTIPLE_CHOICE_DIALOG_QUESTION_LENGTH);
        items = currentQuestion.generatePossAnswers();
    }

    /*
     *This method is checking if the clicked answer is true ore false and then updates the boolean wrongAnswer ore rightAnswer
     */
    private void checkClickedAnswer (){
        int checkedButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedButtonId == -1) {
            wrongAnswer = true;
        } else {
            RadioButton checkedButton = (RadioButton) dialogView.findViewById(checkedButtonId);
            if (checkedButton.getText().toString().equals(currentQuestion.getRightAnswerString())) {
                rightAnswer = true;
            } else {
                wrongAnswer = true;
            }
        }
    }

    /*
     *This method dismisses the dialog after the DIALOG_SHOW_TIME_IN_SECONDS
     */
    public void dismissDialog(){
        long currentTime = System.currentTimeMillis();
        long timeDifferenceInSeconds = (currentTime-startTime)/1000;
        if(timeDifferenceInSeconds >= Constants.DIALOG_SHOW_TIME_IN_SECONDS){
            dismiss();
        }
    }

    /*
     *This method is always called when the dialog gets dismissed.
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        checkClickedAnswer();
    }

    /*
     *This method returns the boolean rightAnswer, which is needed in the PlayState.
     */
    public boolean getRightAnswer() {
        return rightAnswer;
    }

    /*
     *This method returns the boolean wrongAnswer, which is needed in the PlayState.
     */
    public boolean getWrongAnswer() {
        return wrongAnswer;
    }
}
