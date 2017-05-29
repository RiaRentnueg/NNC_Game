package de.mi.ur.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;

import de.mi.ur.R;

/**
 * Created by Lydia on 21.09.2016.
 */
public class HighscoreDialog extends DialogFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private EditText editText;
    private String userName;
    private boolean dialogDone = false;
    private SharedPreferences sharedPref;

    /*
     *This method creates a new dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        loadPreferences();
        editText = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.highscore_dialog_title))
                .setMessage(getResources().getString(R.string.highscore_dialog_message_part_one) + userName + getResources().getString(R.string.highscore_dialog_message_part_two))
                .setView(editText)
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogDone = true;
            }
                })
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = editText.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getResources().getString(R.string.key_pref_user_name), userName);
                editor.commit();
                dialogDone = true;
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    /*
     *This method initialises the userName with the  value which is saved in the sharedPreferences.
     */
    private void loadPreferences(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userName = sharedPref.getString(getResources().getString(R.string.key_pref_user_name), "");
    }

    /*
     *This method is always called when something in the changes in the sharedPreferences. When the method is called
     *it updates the username.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadPreferences();
    }

    /*
     * This method returns the userName, that other classes have access. In this case the AndroidLauncher needs access
     */
    public String getUserName() {
        return userName;
    }

    /*
     *This method returns the boolean dialog done. This boolean is needed in the GameOverState.
     */
    public boolean getDialogDone() {
        return dialogDone;
    }
}
