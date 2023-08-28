package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.List;

/**
 * A {@link Fragment} subclass that creates a fragment for
 * the results page of the quiz application.
 */
public class ViewResultsFragment extends Fragment {

    private TextView scores;
    private ScoreData scoreData = null;

    /**
     * Required empty public constructor.
     */
    public ViewResultsFragment() {
    }

    /**
     * Creates and returns the view hierarchy for the View Results Fragment.
     * @param inflater Inflates the views in the fragment.
     * @param container Parent view that is attached by the fragment's user interface.
     * @param savedInstanceState Reconstructs the fragment from a previously saved state.
     * @return view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scoreData = new ScoreData(getActivity());
        scoreData.open();
        List<Scores> scoresList = scoreData.retrieveAllScoreData();

        // Inflates the layout for this fragment
        View v = inflater.inflate(R.layout.view_results, container, false);
        scores = v.findViewById(R.id.scores);
        if (scoresList.size() == 0)
        {
            scores.setText("No Quizzes yet taken");
        }
        else
        {
            scores.setText("");
            int count = 1;
            for (int i = scoresList.size() - 1; i >= 0; i--) {
                scores.append(count + ") " + "Score: " + scoresList.get(i).getScore() + " Date: " + scoresList.get(i).getDateTime() + "\n");
                count++;
            }
        }
        return v;
    }
}