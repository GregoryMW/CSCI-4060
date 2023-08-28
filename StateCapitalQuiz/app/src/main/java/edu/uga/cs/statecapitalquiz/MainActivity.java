package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main activity of the State Capitals Quiz.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Creates the home screen of the quiz home screen, which is shown
     * after the splash screen.
     * @param savedInstanceState the saved instance of the view created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adds the initial home screen of quiz_questions.xml (linked to QuizActivity.java)
        // to the FrameLayout in activity_main.xml
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.quizFrame, new QuizActivity()).commit();
    }

    /**
     * Triggers a call to the view pager.
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            moveTaskToBack(true);
        }
    }
}