package edu.uga.cs.ugafragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


// This fragment presents two buttons for the user: one for overview and one for the details.
// It is reused in portrait and landscape orientations.
public class ChoiceFragment extends Fragment {

    private static final String TAG = "UGAInfoChoiceFragment";

    private boolean showTwoFragments = false;
    private boolean overview = true;

    // Required public default constructor
    public ChoiceFragment() {
    }

    // This method is similar to the factory method design pattern
    // to create new instances of this fragment.
    // There is a specific reason for having this method, though.  If we need to send some data into the new fragment,
    // Android disallows overloaded constructors for fragments, and so we can't create a Fragment with
    // argument(s).  But we can use the Bundle and send the data this way (commented out here).  Also, the setArguments call
    // must happen BEFORE the fragment is attached an activity.
    //
    // It is included here as an example, as it is never used in this example app as the
    // ChoiceFragment instance is created and attached to the activity by Android.
    public static ChoiceFragment newInstance() {
        ChoiceFragment fragment = new ChoiceFragment();
        return fragment;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView()" );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choice, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated()" );

        super.onViewCreated(view,savedInstanceState);

        Button overviewButton = getView().findViewById( R.id.button );
        Button detailsButton = getView().findViewById( R.id.button2 );
        overviewButton.setOnClickListener( new ButtonClickListener() );
        detailsButton.setOnClickListener( new ButtonClickListener() );

        View detailsFrame = getActivity().findViewById(R.id.frameLayout);
        showTwoFragments = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if( savedInstanceState != null ) {
            // we have a saved value for overview due to an orientation change
            overview = savedInstanceState.getBoolean("overview");
            Log.d( TAG, "onActivityCreated(): overview restored: " + overview );
        }
        else
            Log.d( TAG, "onActivityCreated(): overview: savedInstanceState == null" );

        Log.d( TAG, "onActivityCreated(): overview: " + overview );

        if( showTwoFragments ) {
            showInfo( overview );
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            switch( v.getId() ) {
                case R.id.button:
                    overview = true;
                    break;
                case R.id.button2:
                    overview = false;
                    break;
            }
            showInfo( overview );
        }
    }

    private void showInfo( boolean overview )
    {
        if( showTwoFragments ) {
            // this is in landscape mode (showTwoFragments is true)

            // create a new fragment
            InfoFragment infoFragment = InfoFragment.newInstance(overview);

            // replace the framelayout placeholder with the new fragment instance within a transaction
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, infoFragment);

            // commit the transaction, i.e. make the changes
            fragmentTransaction.commit();
        }
        else {
            // this is in portrait mode (showTwoFragments is false)
            // perform a simple activity transition using an Intent
            Intent intent = new Intent();
            intent.setClass(getActivity(), ViewInfoActivity.class);
            intent.putExtra("overview", overview);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ) {

        Log.d( TAG, "onSaveInstanceState()" );

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState( savedInstanceState );

        // Save the overview value;  on orientation change we will restore the info properly
        savedInstanceState.putBoolean( "overview", overview );
        Log.d( TAG, "onSaveInstanceState(): overview: " + overview );

    }

    // These fragment callback methods are not needed and are for educational purposes only
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate()" );
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach( Context context ) {
        Log.d( TAG, "onAttach()" );
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        Log.d( TAG, "onStart()" );
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume()"  );
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause()" );
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d( TAG, "onStop()" );
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView()" );
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy()" );
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d( TAG, "onDetach()" );
        super.onDetach();
    }
}