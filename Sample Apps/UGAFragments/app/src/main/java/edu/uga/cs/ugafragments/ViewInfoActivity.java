package edu.uga.cs.ugafragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class ViewInfoActivity extends AppCompatActivity {

    private static final String TAG = "UGAInfoViewInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( TAG, "onCreate()" );

        super.onCreate(savedInstanceState);

        // If this activity is in foreground in portrait orientation, the MainActivity is also present
        // in on the back stack.  When the user changes the orientation to landscape, Android kills
        // aLL activities in the app and restarts them. All fragments hosted in them are killed, as well.
        // This activity (ViewInfoActivity) will be restarted, but it will not be needed, as the MainActivity's
        // landscape orientation will include all needed data (both fragments).  The MainActivity will
        // also be restarted and it will create and show both fragments.
        // Consequently, this activity will not be needed after the restart.  So, in landscape orientation
        // we should kill this activity.  It is done using the finish() method.  Note that we immediately
        // return from onCreate(), as well.
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            Log.d( TAG, "onCreate() in landscape orientation.  Exiting." );
            finish();
            return;
        }

        Log.d( TAG, "onCreate() in portrait orientation.  Replacing fragment." );

        Intent intent = getIntent();
        boolean overview = intent.getBooleanExtra( "overview", false );

        // create a new InfoFragment with the desired type of information (overview or details)
        InfoFragment infoFragment = InfoFragment.newInstance( overview );

        // Replace the content view of the activity's screen with the InfoView fragment.
        // Each Activity has a FrameLayout with the identifier "@+id/content".  It is its content view.
        // We are replacing this content view with the new fragment (InfoFragment).
        // The InfoFragment instance will be hosted within this activity.
        getSupportFragmentManager().beginTransaction().replace( android.R.id.content, infoFragment ).commit();

    }

    // These activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( TAG, "onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( TAG, "onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( TAG, "onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( TAG, "onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( TAG, "onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( TAG, "onRestart()" );
        super.onRestart();
    }
}