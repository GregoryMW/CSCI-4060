package edu.uga.cs.statecapitalquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A {@link SQLiteOpenHelper} subclass that is used to create the
 * quiz database.
 */
public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "states.db";
    private static final int DB_VERSION = 1;
    private static QuizDBHelper helperInstance;

    public static final String TABLE_STATES = "states";
    public static final String STATES_COLUMN_ID = "_id";
    public static final String STATES_COLUMN_STATE = "state";
    public static final String STATES_COLUMN_CAPITAL = "capital";
    public static final String STATES_COLUMN_SECOND = "second_city";
    public static final String STATES_COLUMN_THIRD = "third_city";
    public static final String STATES_COLUMN_STATEHOOD = "statehood";
    public static final String STATES_COLUMN_SINCE = "capital_since";
    public static final String STATES_COLUMN_RANK = "capital_rank";

    public static final String TABLE_SCORES = "scores";
    public static final String SCORES_COLUMN_ID = "_id";
    public static final String SCORES_COLUMN_SCORE = "score";
    public static final String SCORES_COLUMN_DATETIME = "dateTime";

    private static final String CREATE_STATES =
            "create table " + TABLE_STATES + " ("
            + STATES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATES_COLUMN_STATE + " TEXT, "
            + STATES_COLUMN_CAPITAL + " TEXT, "
            + STATES_COLUMN_SECOND + " TEXT, "
            + STATES_COLUMN_THIRD + " TEXT, "
            + STATES_COLUMN_STATEHOOD + " TEXT, "
            + STATES_COLUMN_SINCE + " TEXT, "
            + STATES_COLUMN_RANK + " TEXT" + ")";

    private static final String CREATE_SCORES =
            "create table " + TABLE_SCORES + " ("
            + SCORES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SCORES_COLUMN_SCORE + " TEXT, "
            + SCORES_COLUMN_DATETIME + " TEXT" + ")";

    /**
     * Constructor for the database helper.
     * @param context context of the database.
     */
    private QuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Gets a helper instance of the database created.
     * @param context context of the database.
     * @return helper instance of the database
     */
    public static synchronized QuizDBHelper getInstance(Context context) {
        // Check if the instance already exists and if not, create the instance
        if (helperInstance == null) {
            helperInstance = new QuizDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    /**
     * Creates the SQLite database for the quiz.
     * @param db database for the quiz.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATES);
        db.execSQL(CREATE_SCORES);
    }

    /**
     * Upgrades the database to a new version, upon the first creation.
     * @param db database for the quiz.
     * @param oldVersion old version of the database.
     * @param newVersion new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_STATES);
        db.execSQL("drop table if exists " + TABLE_SCORES);
        onCreate(db);
    }
}
