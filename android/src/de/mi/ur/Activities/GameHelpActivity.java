package de.mi.ur.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 15.08.2016.
 *
 * This activity displays a help text for the game
 */
public class GameHelpActivity extends AppCompatActivity {
    private Toolbar myToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_help_activity);
        setupToolbar();
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.game_help_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.game_help_toolbar_headline);
        myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
