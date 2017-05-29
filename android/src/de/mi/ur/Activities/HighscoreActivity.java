package de.mi.ur.Activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import de.mi.ur.DataBase.HighscoreAdapter;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 01.09.2016.
 * Evtl CursorLoader verwenden um den Main-Thread nicht zu blockieren!
 */
public class HighscoreActivity extends AppCompatActivity {
    private ListView highscoreListView;
    private NNCDatabase db;
    private Toolbar myToolbar;
    private TextView noHighscoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        db = new NNCDatabase(this);
        setUpUI();
        setupToolbar();
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.game_highscore_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.game_highscore_toolbar_headline);
        myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*
     * handles the setting up of UI-components
     */
    private void setUpUI() {
        noHighscoreView = (TextView) findViewById(R.id.no_highscore_view);
        highscoreListView = (ListView) findViewById(R.id.highscore_list);
        db.open();
        Cursor allHighscoresCursor = db.getAllHighscoresCursor();
        HighscoreAdapter adapter = new HighscoreAdapter(this, allHighscoresCursor);
        displayIfIsHighscore(allHighscoresCursor);
        highscoreListView.setAdapter(adapter);
        db.close();
    }

    /*
     * displays the current highscores if there are any
     */
    private void displayIfIsHighscore(Cursor allHighscoresCursor) {
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.highscore_listitem, null);
        if ((allHighscoresCursor.moveToFirst()) || allHighscoresCursor.getCount() != 0) {
            highscoreListView.addHeaderView(v);
            noHighscoreView.setVisibility(View.GONE);
        }else{
            highscoreListView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
