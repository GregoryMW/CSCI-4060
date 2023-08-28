package edu.uga.cs.ugafragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


// This fragment presents the UGA info to the user: either with an overview and with some details.
// It is reused in portrait and landscape orientations.
public class InfoFragment extends Fragment {

    private static final String TAG = "UGAInfoInfoFragment";

    private boolean overview;
    private TextView title;
    private TextView text;

    public InfoFragment() {
        // Required empty public constructor
    }

    // This method is similar to the factory method design pattern to create new instances of this fragment.
    //
    // There is a specific reason for having this method, though.  We want to send some data (overview parameter, here)
    // into the new fragment.  Android disallows overloaded constructors for fragments, and so we can't create a Fragment
    // with overview as argument.  But we can use the Bundle and send the data this way.  Also, the setArguments call
    // must happen BEFORE the fragment is attached an activity.
    public static InfoFragment newInstance( boolean overview ) {
        Log.d(TAG, "newInstance: " + overview );

        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putBoolean( "overview", overview );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate" );
        super.onCreate(savedInstanceState);
        if( getArguments() != null ) {
            overview = getArguments().getBoolean("overview" );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d(TAG, "onCreateView" );

        if( container != null )
            container.removeAllViews();

        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_info, container, false );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d( TAG, "onViewCreated()" );
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById( R.id.textView3 );
        text = view.findViewById( R.id.textView2 );

        showInfo( overview );
    }

    // show the UGA info, either overview or details
    public void showInfo( boolean overview ) {
        if( overview ) {
            title.setText( "Overview" );
            String overviewText = getResources().getString(R.string.overview_text );
            text.setText( overviewText );
        }
        else {
            title.setText( "More info" );
            String detailsText = getResources().getString(R.string.details_text );
            text.setText( detailsText );
        }
    }

    // These fragment callback methods are not needed and are for educational purposes only
    @Override
    public void onAttach(Context context) {
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