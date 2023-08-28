package edu.uga.cs.statecapitalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the data of a quiz score.
 */
public class ScoreData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    private static final String[] allColumns = {
            QuizDBHelper.SCORES_COLUMN_ID,
            QuizDBHelper.SCORES_COLUMN_SCORE,
            QuizDBHelper.SCORES_COLUMN_DATETIME,
    };

    /**
     * Gets the instance of the data from the database
     * @param context data from the database.
     */
    public ScoreData(Context context) {
        this.quizDBHelper = QuizDBHelper.getInstance(context);
    }

    /**
     * Opens the database to retrieve the data.
     */
    public void open() {
        db = quizDBHelper.getWritableDatabase();
    }

    /**
     * Closes the database.
     */
    public void close() {
        if (quizDBHelper != null) {
            quizDBHelper.close();
        }
    }

    /**
     * Checks whether the database is open
     * @return True if the database is open. Otherwise, false.
     */
    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Gets all the data of each quiz score from the database.
     * @return all score data.
     */
    public List<Scores> retrieveAllScoreData() {
        ArrayList<Scores> scores = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_SCORES, allColumns, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 3) {
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.SCORES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.SCORES_COLUMN_SCORE);
                        String score = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.SCORES_COLUMN_DATETIME);
                        String dateTime = cursor.getString(columnIndex);

                        Scores newScore = new Scores(score, dateTime);
                        newScore.setId(id);
                        scores.add(newScore);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return scores;
    }

    /**
     * Stores the data of each quiz score in the database.
     * @param newScore final score of each quiz.
     * @return final score of each quiz.
     */
    public Scores storeScoreData(Scores newScore) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.SCORES_COLUMN_SCORE, newScore.getScore());
        values.put(QuizDBHelper.SCORES_COLUMN_DATETIME, newScore.getDateTime());

        long id = db.insert(QuizDBHelper.TABLE_SCORES, null, values);
        newScore.setId(id);
        return newScore;
    }
}
