package edu.uga.cs.statecapitalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the data for each quiz from the database.
 */
public class QuizData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    private static final String[] allColumns = {
            QuizDBHelper.STATES_COLUMN_ID,
            QuizDBHelper.STATES_COLUMN_STATE,
            QuizDBHelper.STATES_COLUMN_CAPITAL,
            QuizDBHelper.STATES_COLUMN_SECOND,
            QuizDBHelper.STATES_COLUMN_THIRD,
            QuizDBHelper.STATES_COLUMN_STATEHOOD,
            QuizDBHelper.STATES_COLUMN_SINCE,
            QuizDBHelper.STATES_COLUMN_RANK
    };

    /**
     * Constructor to get the data of each quiz from the database.
     * @param context data of each quiz.
     */
    public QuizData(Context context) {
        this.quizDBHelper = QuizDBHelper.getInstance(context);
    }

    /**
     * Opens the database for a quiz.
     */
    public void open() {
        db = quizDBHelper.getWritableDatabase();
    }

    /**
     * Closes the database for a quiz.
     */
    public void close() {
        if (quizDBHelper != null) {
            quizDBHelper.close();
        }
    }

    /**
     * Checks whether the database is open.
     * @return True if the database is open. Otherwise, false.
     */
    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Creates a list of the quiz questions for each quiz taken.
     * @return questions of the quiz.
     */
    public List<QuizQuestion> retrieveAllStateData() {
        ArrayList<QuizQuestion> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_STATES, allColumns, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 8) {
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_STATE);
                        String state = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_CAPITAL);
                        String capital = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_SECOND);
                        String second = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_THIRD);
                        String third = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_STATEHOOD);
                        String statehood = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_SINCE);
                        String since = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.STATES_COLUMN_RANK);
                        String rank = cursor.getString(columnIndex);

                        QuizQuestion quizQuestion = new QuizQuestion(state, capital, second, third, statehood, since, rank);
                        quizQuestion.setId(id);
                        questions.add(quizQuestion);
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
        return questions;
    }

    /**
     * Stores the data from each state assigned to a quiz question.
     * @param quizQuestion question that is assigned to the state.
     * @return quiz question with the state data.
     */
    public QuizQuestion storeStateData(QuizQuestion quizQuestion) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.STATES_COLUMN_STATE, quizQuestion.getState());
        values.put(QuizDBHelper.STATES_COLUMN_CAPITAL, quizQuestion.getCapital());
        values.put(QuizDBHelper.STATES_COLUMN_SECOND, quizQuestion.getSecondChoice());
        values.put(QuizDBHelper.STATES_COLUMN_THIRD, quizQuestion.getThirdChoice());
        values.put(QuizDBHelper.STATES_COLUMN_STATEHOOD, quizQuestion.getStatehood());
        values.put(QuizDBHelper.STATES_COLUMN_SINCE, quizQuestion.getSince());
        values.put(QuizDBHelper.STATES_COLUMN_RANK, quizQuestion.getRank());

        long id = db.insert(QuizDBHelper.TABLE_STATES, null, values);
        quizQuestion.setId(id);
        return quizQuestion;
    }
}
