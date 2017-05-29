package de.mi.ur.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import de.mi.ur.AndroidCommunication.HighscoreListener;

/**
 * Created by Anna-Marie on 09.08.2016.
 *
 * This class is responsible for storing the things needed after the shutdown of the application
 */
public class NNCDatabase implements HighscoreListener {
    // generall database
    private static final String DATABASE_NAME = "NNC Database";
    private static final int DATABASE_VERSION = 1;

    /*
     * highscore table constants
     */
    private static final String TABLE_HIGHSCORE = "nncGameHighscores";
    private static final String KEY_ID = "_id";
    private static final String KEY_RANK = "rank";
    private static final String KEY_POINTS = "points";
    private static final String KEY_NAME = "name";

    private static final int COLUMN_RANK_INDEX = 1;
    private static final int COLUMN_POINTS_INDEX = 2;
    private static final int COLUMN_NAME_INDEX = 3;


    /*
     * access to all columns of the database with this as a where-clause
     */
    private static final String[] ALL_COLUMNS_HIGHSCORE = {KEY_ID, KEY_RANK, KEY_POINTS, KEY_NAME};

    // instance variables
    private NncDBOpenHelper dbHelper;
    private SQLiteDatabase database;

    public NNCDatabase(Context context) {
        dbHelper = new NncDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /*
     * gets a usable database
     */
    public void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        database.close();
    }


    /*
     * Saves a highscore in the database
     */
    public long insertHighscoreData(Highscore highscore) {
        ContentValues highscoreValues = new ContentValues();

        highscoreValues.put(KEY_RANK, highscore.getRank());
        highscoreValues.put(KEY_POINTS, highscore.getPoints());
        highscoreValues.put(KEY_NAME, highscore.getName());

        return database.insert(TABLE_HIGHSCORE, null, highscoreValues);
    }

    /*
     * removes a highscore with a certain rank from the database
     */
    public void removeHighscoreData (int rank){
        String whereClause = KEY_RANK + " = "+ rank;
        database.delete(TABLE_HIGHSCORE, whereClause, null);
    }


    /*
     * Gets a highscore with a certain rank from the database
     */
    public Highscore getHighscoreWithCertainRank(int rank) {
        String whereClause = KEY_RANK + " = " + rank;
        Cursor cursor = database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, whereClause, null, null, null, null);
        ArrayList<Highscore> highscore = buildHighscoresFromCursor(cursor);

        return highscore.get(0);
    }

    /*
     * Gets an ArrayList of all Highscores saved in the database
     */
    public ArrayList<Highscore> getAllHighscores() {
        Cursor cursor = getAllHighscoresCursor();
        return buildHighscoresFromCursor(cursor);
    }

    /*
     * gets a Cursor pointing to all Highscores
     */
    public Cursor getAllHighscoresCursor() {
        return database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, null, null, null, null, KEY_RANK + " ASC");
    }

    /*
     * "building" highscores is done in this method to avoid duplicate code
     */
    private ArrayList<Highscore> buildHighscoresFromCursor(Cursor cursor) {
        ArrayList<Highscore> highscores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _rank = cursor.getInt(COLUMN_RANK_INDEX);
                int points = cursor.getInt(COLUMN_POINTS_INDEX);
                String name = cursor.getString(COLUMN_NAME_INDEX);

                highscores.add(new Highscore(_rank, points, name));

            } while (cursor.moveToNext());
        }
        return highscores;
    }


    /*
     * checks if points is large enough to be a new highscore.
     * returns the rank in the highscore, or -1 if the points are not good enough for a highscore
     */
    @Override
    public int checkIfNewHighscore(int points) {
        if (getAllHighscores().size() == 0 || getHighscoreWithCertainRank(1).getPoints() < points) {
            return 1;
        } else if (getAllHighscores().size() == 1 || getHighscoreWithCertainRank(2).getPoints() < points) {
            return 2;
        } else if (getAllHighscores().size() == 2 || getHighscoreWithCertainRank(3).getPoints() < points) {
            return 3;
        } else {
            return -1;
        }
    }

    /*
     * This method saves the new highscore to the database
     * all the old highscores are adapted to the new one
     */
    @Override
    public void saveHighscoreToDatabase(int rank, int points, String newUserName) {
        ArrayList<Highscore> allHighscores = getAllHighscores();
        int size = allHighscores.size();
        for(int i = size; i > rank; i--){
            removeHighscoreData(i);
            Highscore newHighscore = allHighscores.get(i-2);
            newHighscore.lowerRankByOne();
            insertHighscoreData(newHighscore);
        }
        if((size==rank) && (size != 3)){
            removeHighscoreData(rank);
            Highscore newHighscore = allHighscores.get(size-1);
            newHighscore.lowerRankByOne();
            insertHighscoreData(newHighscore);
        }
        removeHighscoreData(rank);
        insertHighscoreData(new Highscore(rank, points, newUserName));

    }


    private class NncDBOpenHelper extends SQLiteOpenHelper {
        private static final String CREATE_TABLE = "create table ";
        private static final String INTEGER_NOT_NULL = " integer not null, ";
        private static final String INTEGER_1_KEY_AUTOINCREMENT = " integer primary key autoincrement, ";

        public static final String CREATE_HIGHSCORE_TABLE = CREATE_TABLE + TABLE_HIGHSCORE
                + " (" + KEY_ID + INTEGER_1_KEY_AUTOINCREMENT + KEY_RANK + INTEGER_NOT_NULL
                + KEY_POINTS + INTEGER_NOT_NULL + KEY_NAME + " text);";

            public NncDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

        /*
         * The two tables are initiated
         * and the level-table is filled with the information on levels.
         * The current level is the same as level 0 (except for the id) at the beginning
         */
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_HIGHSCORE_TABLE);

            }


        /*
         * This method handles the case of changes to the structure of the database
         */
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // on upgrade drop older tables
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
                // create new tables
                onCreate(db);
            }
        }


}


