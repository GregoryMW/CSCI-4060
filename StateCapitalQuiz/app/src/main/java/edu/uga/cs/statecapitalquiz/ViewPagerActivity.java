package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentActivity} subclass that allows the user to swipe left
 * or right through screens of data.
 */
public class ViewPagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 7;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private QuizData quizData = null;
    private ScoreData scoreData = null;
    private List<QuizQuestion> states;
    private int currentNum;
    private int score;

    /**
     * Creates and sets the view pager for the quiz application.
     * @param savedInstanceState Reconstructs the activity from a previously saved state.
     */
    protected void onCreate(Bundle savedInstanceState) {
        quizData = new QuizData(this);
        scoreData = new ScoreData(this);
        quizData.open();
        scoreData.open();

        states = new ArrayList<>();
        currentNum = 1;
        score = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_viewpager);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new QuizPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(7);
    }

    /**
     * Gets the current number of the screen displayed.
     * @return current number of the screen displayed.
     */
    public int getCurrentNum() {
        return currentNum;
    }

    /**
     * Increases the current number of the screen displayed.
     */
    public void incrementCurrentNum() {
        currentNum++;
    }

    /**
     * Gets the score of the user's quiz result.
     * @return score of the user's quiz result.
     */
    public int getScore() {
        return score;
    }

    /**
     * If the user answeres a question correctly, it increases the score
     * of the user's quiz result.
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Adds the state to the quiz question list.
     * @param q Quiz question shown to the user.
     */
    public void addToList(QuizQuestion q) {
        states.add(q);
    }

    /**
     * Checks if the question contains an assigned state.
     * @param q Quiz question being assigned to the user.
     * @return True if the question contains a state. Otherwise, false.
     */
    public boolean containsState(QuizQuestion q) {
        if (states.contains(q)) {
            return true;
        }
        return false;
    }

    /**
     * Triggers a call to the view pager.
     */
    public void onBackPressed()  {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * A {@link FragmentStateAdapter} subclass that handles the view pager
     * in order to manage each page.
     */
    private class QuizPagerAdapter extends FragmentStateAdapter {
        /**
         * Constructor for the Quiz fragment utilizing the view pager.
         * @param fa Activity of the fragment.
         */
        public QuizPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        /**
         * Creates the fragment for each page of the view pager.
         * @param position Position of the page.
         * @return Returns the finished fragment.
         */
        @Override
        public Fragment createFragment(int position) {
            if (position < 6) {
                return new QuizQuestionsFragment();
            }
            return new QuizFinishedFragment();
        }

        /**
         * Gets the number of pages in the view pager.
         * @return number of pages.
         */
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
