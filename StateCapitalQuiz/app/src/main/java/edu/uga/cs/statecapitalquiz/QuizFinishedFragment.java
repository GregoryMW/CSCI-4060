package edu.uga.cs.statecapitalquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass that creates a fragment for
 *  to represent the completion of a quiz.
 */
public class QuizFinishedFragment  extends Fragment {

    Button submitButton;
    private ScoreData scoreData = null;

    /**
     * Required empty public constructor.
     */
    public QuizFinishedFragment() {
    }

    /**
     * Creates and returns the view hierarchy for the Quiz Completion Fragment.
     * @param inflater Inflates the views in the fragment.
     * @param container Parent view that is attached by the fragment's user interface.
     * @param savedInstanceState Reconstructs the fragment from a previously saved state.
     * @return view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scoreData = new ScoreData(getActivity());
        scoreData.open();
        View v = inflater.inflate(R.layout.quiz_finished, container, false);
        submitButton = v.findViewById(R.id.submitQuiz);
        submitButton.setOnClickListener(view -> {
            Scores newScore = new Scores(Integer.toString(((ViewPagerActivity)getActivity()).getScore()), new SimpleDateFormat("MM--dd--yyyy HH:mm:ss").format(new Date()));
            new ScoreDBWriter().execute(newScore);
            startActivity(new Intent(getActivity(), MainActivity.class));
        });
        return v;

    }

    /**
     * Performs the score calculation in the background while the user is
     * taking the quiz.
     */
    public class ScoreDBWriter extends AsyncTask<Scores, Scores> {
        /**
         * Performs the calculation of a quiz score in the background.
         * @param scores number of questions answered correctly.
         * @return score of a quiz.
         */
        @Override
        protected Scores doInBackground(Scores... scores) {
            scoreData.storeScoreData(scores[0]);
            return scores[0];
        }

        /**
         * Invoked after the background computation finishes.
         * @param score result returned by the quiz.
         */
        @Override
        protected void onPostExecute(Scores score) {
            // Needed only for class declaration
        }
    }
}
