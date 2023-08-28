package edu.uga.cs.androidversionsfragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


/**
 * A simple activity to show Android versions utilizing fragments.
 * KJK
 */
public class AndroidVersionInfoActivity extends AppCompatActivity {
    // a TAG to identify logcat events
    private static final String TAG = "AndroidVersions";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( TAG, "AndroidVersionInfoActivity.onCreate()" );

        super.onCreate( savedInstanceState );

        // IMPORTANT:
        // Add the back button in the ActionBar of this activity.  It will appear
        // as a left chevron (arrow tip), the regular back button.
        //
        // We can't use the Parent specification in the AndroidManifest, since
        // it would place the activity on the back stack.  Consequently, when going
        // back to the screen with the list of Android versions (using the "parent-specified"
        // back button, this would cause a recreation of the ListFragment.  The new list
        // would show the initial item (Android version) as selected, not the one we
        // actually selected when transitioning to the Android version details screen.
        //
        // However, there must be a listener added for this back button (look below).
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled( true );

        // If this activity is in foreground in portrait orientation, the MainActivity is also present
        // in on the back stack.  When the user changes the orientation to landscape, Android kills
        // ALL activities in the app and restarts them. All fragments hosted in them are killed, as well.
        // This activity (AndroidVersionInfoActivity) will be restarted, but it will no be needed, as
        // the AndroidVersionsMainActivity's landscape orientation will include all needed data (both
        // fragments).  AndroidVersionsMainActivity will also be restarted and it will build and show
        // both fragments.  AndroidVersionListFragment hosted there will control this work.
        // Consequently, this activity will not be needed after the restart.  So, in landscape orientation
        // we should kill this activity.  It is done using the finish() method.  Note that we immediately
        // return from onCreate(), as well.
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            Log.d( TAG, "AndroidVersionInfoActivity.onCreate(): in landscape mode; returning" );
            finish();
            return;
        }

        Log.d(TAG, "AndroidVersionInfoActivity.onCreate(): in portrait mode; replacing fragments");

        AndroidVersionInfoFragment androidVersionInfoFragment = new AndroidVersionInfoFragment();
        Log.d(TAG, "AndroidVersionInfoActivity.onCreate(): androidVersionInfoFragment: " + androidVersionInfoFragment);


        // pass on any saved data, i.e., Android version no (list index)
        androidVersionInfoFragment.setArguments( getIntent().getExtras() );

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add the fragment within a transaction
        // android.R.id.content is used to obtain the view reference without having to have its id
        // it reference the root (ViewGroup) of the entire content area of an Activity
        getSupportFragmentManager().beginTransaction().replace( android.R.id.content, androidVersionInfoFragment ).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implement Back button listener method.
        // This method may be used for other actions from the ActionBar menu, if provided in the layout.
        int id = item.getItemId();

        // android.R.id.home is the built-in designation of the back button widget.
        if( id == android.R.id.home ) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( TAG, "AndroidVersionInfoActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( TAG, "AndroidVersionInfoActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( TAG, "AndroidVersionInfoActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( TAG, "AndroidVersionInfoActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( TAG, "AndroidVersionInfoActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( TAG, "AndroidVersionInfoActivity.onRestart()" );
        super.onRestart();
    }
}
