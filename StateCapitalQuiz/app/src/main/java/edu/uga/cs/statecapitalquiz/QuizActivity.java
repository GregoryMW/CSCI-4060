package edu.uga.cs.statecapitalquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A {@link Fragment} subclass that creates a fragment for
 * the activity of a Quiz.
 */
public class QuizActivity extends Fragment {

    private Button toQuiz;
    private Button toResults;
    private QuizData quizData = null;

    /**
     * Required empty public constructor.
     */
    public QuizActivity() {
    }

    /**
     * Creates and returns the view hierarchy for the Quiz Activity Fragment.
     * @param inflater Inflates the views in the fragment.
     * @param container Parent view that is attached by the fragment's user interface.
     * @param savedInstanceState Reconstructs the fragment from a previously saved state.
     * @return view of the fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!doesDatabaseExist(getActivity(), "states.db")) {
            quizData = new QuizData(getActivity());
            quizData.open();

            try {
                CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("state_capitals.csv")));
                String[] states;
                // Skip the first line of the csv file. This is the "state, capital, second city, ..." row and should not be inserted as a row in the database
                reader.readNext();
                while ((states = reader.readNext()) != null) {
                    String state = states[0];
                    String capital = states[1];
                    String second = states[2];
                    String third = states[3];
                    String statehood = states[4];
                    String since = states[5];
                    String rank = states[6];
                    QuizQuestion quizQuestion = new QuizQuestion(state, capital, second, third, statehood, since, rank);
                    new QuizDBWriter().execute(quizQuestion);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }
        }

        // Links the quiz_activity.xml file to this QuizActivity.java class
        View v = inflater.inflate(R.layout.quiz_activity, container, false);
        toQuiz = v.findViewById(R.id.toQuiz);
        toResults = v.findViewById(R.id.toResults);

        // Makes the two buttons replace the FrameLayout with their respective xml file
        toQuiz.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ViewPagerActivity.class));
        });

        toResults.setOnClickListener((view -> {
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.quizFrame, new ViewResultsFragment()).addToBackStack(null).commit();
        }));
        return v;
    }

    /**
     * Performs the quiz question calculation in the background while the user has
     * a quiz in progress.
     */
    public class QuizDBWriter extends AsyncTask<QuizQuestion, QuizQuestion> {
        /**
         * Performs the calculation of a quiz score in the background.
         * @param questions question being assigned in the quiz.
         * @return questions being given.
         */
        @Override
        protected QuizQuestion doInBackground(QuizQuestion... questions) {
            quizData.storeStateData(questions[0]);
            return questions[0];
        }

        /**
         * Invoked after the background computation finishes.
         * @param quizQuestion quiz questions being assigned.
         */
        @Override
        protected void onPostExecute(QuizQuestion quizQuestion) {
            // Needed only for class declaration
        }
    }

    /**
     * Checks whether the database exists for the quiz application.
     * @param context context of the database.
     * @param dbName name of the database.
     * @return True if the database exists. Otherwise, false.
     */
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
