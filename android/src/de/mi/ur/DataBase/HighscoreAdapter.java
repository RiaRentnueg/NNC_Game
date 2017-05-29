package de.mi.ur.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 02.09.2016.
 * This class is used to connect a Cursor containing information on highscores to a listView
 */
public class HighscoreAdapter extends CursorAdapter {


    public HighscoreAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.highscore_listitem, parent, false);
        bindView(view, context, cursor);
        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView rankTextView = (TextView) view.findViewById(R.id.highscore_rank_view);
        TextView pointsTextView = (TextView) view.findViewById(R.id.highscore_points_view);
        TextView nameTextView = (TextView) view.findViewById(R.id.highscore_name_view);

        int rank = cursor.getInt(cursor.getColumnIndexOrThrow("rank"));
        int points = cursor.getInt(cursor.getColumnIndexOrThrow("points"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

        rankTextView.setText(String.valueOf(rank));
        pointsTextView.setText(String.valueOf(points));
        nameTextView.setText(name);

    }

}
