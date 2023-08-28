package edu.uga.cs.androidversionsfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

/**
 * A simple activity to show Android versions utilizing fragments.
 * KJK
 */
public class AndroidVersionListFragment extends ListFragment {
    // a TAG string for logcat messages identification
    private static final String TAG = "AndroidVersions";

    // Array of Android versions strings for the list fragment
    private final String[] androidVersions = {
            "13",
            "12",
            "11",
            "10",
            "Pie",
            "Oreo",
            "Nougat",
            "Marshmallow",
            "Lollipop",
            "KitKat",
            "Jelly Bean",
            "Ice Cream Sandwich",
            "Honeycomb",
            "Gingerbread",
            "Froyo",
            "Eclair",
            "Donut",
            "Cupcake",
            "Petit Four",
            "Android (No codename)"
    };

    // indicate if this is a landscape mode with both fragments present or not
    boolean twoFragmentsActivity = false;

    // list selection index
    int versionIndex = 0;


    public AndroidVersionListFragment()
    {
        // required default constructor
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {

        super.onViewCreated(view, savedInstanceState);

        Log.d( TAG, "AndroidVersionListFragment.onActivityCreated(): savedInstanceState: " + savedInstanceState );

        // create a new ArrayAdapter for the list
        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1, androidVersions ) );

        // set the twoFragmentsActivity variable to true iff we are in 2 fragment (landscape) view
        View detailsFrame = getActivity().findViewById( R.id.androidVersionInfo );
        Log.d( TAG, "AndroidVersionListFragment.onActivityCreated(): detailsFrame: " + detailsFrame );

        twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // restore the saved list index selection (Android version no), if available
        if( savedInstanceState != null ) {
            // Restore last state for checked position.
            versionIndex = savedInstanceState.getInt("androidVersionSelection", 0 );
            Log.d( TAG, "AndroidVersionListFragment.onActivityCreated(): restored versionIndex: " + versionIndex );
        }

        // set the list mode as single choice and
        getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
        getListView().setItemChecked( versionIndex, true );

        // if we are in 2 fragment (landscape) orientation, show the Android version information on the right side
        // This class will act as the "driver" here by displaying the details using the other fragment.
        if( twoFragmentsActivity ) {

            // display the information about the selected Android version in the fragment on the right (details)
            showAndroidVersionInfo( versionIndex );

            // The list in the landscape orientation may be shorter and the selected item
            // which was visible in portrait mode might be invisible (out of view)
            // i.e., "below" the end of the screen in landscape orientation.
            // To make it visible again, call smoothScrollToPosition
            getListView().smoothScrollToPosition( versionIndex );
        }

    }

    @Override
    public void onListItemClick( ListView l, View v, int position, long id ) {
        // on a click on a list item, show the selected Android version info
        // store the list view and selection for coming back to the list (using the back button)
        //firstVisibleVersionIndex = l.getFirstVisiblePosition();
        //versionIndex = position;
        showAndroidVersionInfo( position );
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);

        // save the list index selection
        outState.putInt( "androidVersionSelection", versionIndex);
        Log.d( TAG, "AndroidVersionListFragment.onSaveInstanceState(): saved versionIndex: " + versionIndex );
    }

    void showAndroidVersionInfo( int versionIndex ) {

        this.versionIndex = versionIndex;

        if( twoFragmentsActivity ) {  // only in the 2 fragment (landscape) orientation

            getListView().setItemChecked( versionIndex, true );

            // get the placeholder element (FrameLayout) in a 2 fragment (landscape) orientation layout
            AndroidVersionInfoFragment details =
                    (AndroidVersionInfoFragment) getParentFragmentManager().findFragmentById( R.id.androidVersionInfo );

            Log.d( TAG, "AndroidVersionListFragment.showAndroidVersionInfo(): details: " + details );

            if( details == null || details.getShownVersionIndex() != versionIndex ) {

                // obtain a new Android info fragment instance
                details = AndroidVersionInfoFragment.newInstance( versionIndex );

                // replace the placeholder with the new fragment stance within a transaction
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.androidVersionInfo, details );

                // TRANSIT_FRAGMENT_FADE means that the fragment should fade in or fade out
                fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

                // commit the transaction, i.e. make the changes
                fragmentTransaction.commit();
            }
        }
        else {
            // In a 1 fragment orientation (portrait), start a new activity using an Intent, as in the previous demo app
            Intent intent = new Intent();
            intent.setClass( getActivity(), AndroidVersionInfoActivity.class );
            intent.putExtra("versionIndex", versionIndex);

            startActivity( intent );
        }
    }

    // These list fragment callback methods are not needed and are for educational purposes only
    @Override
    public void onAttach(Context context) {
        Log.d( TAG, "AndroidVersionListFragment.onAttach()" );
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d( TAG, "AndroidVersionListFragment.onCreate()" );

        // IMPORTANT: this method call will prevent this fragment from being destroyed when
        // recreating the hosting activity. Consequently, the list will be retained.
        //setRetainInstance( true );
    }

    @Override
    public void onStart() {
        Log.d( TAG, "AndroidVersionListFragment.onStart()" );
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d( TAG, "AndroidVersionListFragment.onResume()" );
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d( TAG, "AndroidVersionListFragment.onPause()" );
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d( TAG, "AndroidVersionListFragment.onStop()" );
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "AndroidVersionListFragment.onDestroyView()" );
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "AndroidVersionListFragment.onDestroy()" );
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d( TAG, "AndroidVersionListFragment.onDetach()" );
        super.onDetach();
    }

}
