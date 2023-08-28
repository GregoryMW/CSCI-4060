package com.introtoandroid.simplefragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WeedListFragment extends ListFragment implements
        FragmentManager.OnBackStackChangedListener{

    private static final String DEBUG_TAG = "WeedListFragment";
    int mCurPosition = -1;
    boolean mShowTwoFragments;

    // This example was originally created by the Android textbook authors a few years ago.
    // The method below has been deprecated, but still works for now.
    // The lifecycle method is onViewCreated, which should be used instead.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onActivityCreated()");

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Populate our ListView control within the Fragment
        String[] weeds = getResources().getStringArray(
                R.array.weeds_array);
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, weeds));

        // Check which state we're in
        View detailsFrame = getActivity().findViewById(R.id.weedentry);
        mShowTwoFragments = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            mCurPosition = savedInstanceState.getInt("curChoice", 0);
            Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onActivityCreated(): restored mCurPosition: " + mCurPosition );
        }
        else
            Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onActivityCreated(): null savedInstanceState" );

        if (mShowTwoFragments || mCurPosition != -1) {
            // Set the initial url to our default blog post, or the last one shown
            viewWeedInfo(mCurPosition);
        }

        // monitor back stack changes to update list view
        getFragmentManager().addOnBackStackChangedListener(this);

    }

    @Override
    public void onBackStackChanged() {
        Log.d(DEBUG_TAG, "WeedListFragment.onBackStackChanged()");

        // update position
        WeedWebViewFragment details = (WeedWebViewFragment) getFragmentManager()
                .findFragmentById(R.id.weedentry);
        if (details != null) {
            mCurPosition = details.getShownIndex();
            getListView().setItemChecked(mCurPosition, true);

            // if we're in single pane, then we need to switch forward to the viewer
            if (!mShowTwoFragments) {
                viewWeedInfo(mCurPosition);
            }
        }
    }

    void viewWeedInfo(int index) {
        mCurPosition = index;
        if (mShowTwoFragments) {
            // Check what fragment is currently shown, replace if needed.
            WeedWebViewFragment details = (WeedWebViewFragment) getFragmentManager()
                    .findFragmentById(R.id.weedentry);
            if (details == null || details.getShownIndex() != index) {

                // Make new fragment to show this selection.
                WeedWebViewFragment newDetails = WeedWebViewFragment
                        .newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.weedentry, newDetails);
                // Add this fragment instance to the back-stack of the Activity
                // so we can backtrack through our weeds
                if (index != -1) {
                    String[] weeds = getResources().getStringArray(
                            R.array.weeds_array);
                    String strBackStackTagName = weeds[index];
                    ft.addToBackStack(strBackStackTagName);
                }
                // Fade between Urls
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), WeedViewActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        viewWeedInfo(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onSaveInstanceState(): saved mCurPosition: " + mCurPosition );
        outState.putInt("curChoice", mCurPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onStart()");
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onPause()");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onResume(): "
                + mCurPosition);
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedListFragment.onDestroy()");
        super.onDestroy();
    }

}
