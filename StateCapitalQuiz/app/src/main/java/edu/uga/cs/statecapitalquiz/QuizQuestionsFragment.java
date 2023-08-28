package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass that creates a fragment for
 *  the quiz question page of the quiz application.
 */
public class QuizQuestionsFragment extends Fragment {

    private TextView questionNumber;
    private TextView state;
    private RadioButton choice1;
    private RadioButton choice2;
    private RadioButton choice3;
    private QuizData quizData = null;

    /**
     * Required empty public constructor.
     */
    public QuizQuestionsFragment() {
    }

    /**
     * Creates and returns the view hierarchy for the Quiz Questions Fragment.
     * @param inflater Inflates the views in the fragment.
     * @param container Parent view that is attached by the fragment's user interface.
     * @param savedInstanceState Reconstructs the fragment from a previously saved state.
     * @return view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        quizData = new QuizData(getActivity());
        quizData.open();

        List<QuizQuestion> states = quizData.retrieveAllStateData();
        int random = new Random().nextInt(50);
        QuizQuestion question = states.get(random);

        while (((ViewPagerActivity)getActivity()).containsState(question)) {
            random = new Random().nextInt(50);
            question = states.get(random);
        }
        ((ViewPagerActivity)getActivity()).addToList(question);

        // Inflates the layout for this fragment
        View v = inflater.inflate(R.layout.quiz_questions, container, false);

        questionNumber = v.findViewById(R.id.questionNumber);
        state = v.findViewById(R.id.state);
        choice1 = v.findViewById(R.id.choice1);
        choice2 = v.findViewById(R.id.choice2);
        choice3 = v.findViewById(R.id.choice3);

        questionNumber.setText("Question #" + ((ViewPagerActivity)getActivity()).getCurrentNum());
        state.setText(question.getState() + "?");
        int random2 = new Random().nextInt(3);

        switch (random2) {
            case 0:
            {
                choice1.setText("A) " + question.getCapital());
                choice1.setOnClickListener(view -> {
                        ((ViewPagerActivity) getActivity()).incrementScore();
                });
                choice2.setText("B) " + question.getSecondChoice());
                choice3.setText("C) " + question.getThirdChoice());
                break;
            }
            case 1: {
                choice1.setText("A) " + question.getThirdChoice());
                choice2.setText("B) " + question.getCapital());
                choice2.setOnClickListener(view -> {
                    ((ViewPagerActivity)getActivity()).incrementScore();
                });
                choice3.setText("C) " + question.getSecondChoice());
                break;
            }
            case 2: {
                choice1.setText("A) " + question.getSecondChoice());
                choice2.setText("B) " + question.getThirdChoice());
                choice3.setText("C) " + question.getCapital());
                choice3.setOnClickListener(view -> {
                    ((ViewPagerActivity)getActivity()).incrementScore();
                });
                break;
            }
        }

        ((ViewPagerActivity)getActivity()).incrementCurrentNum();
        return v;
    }
}