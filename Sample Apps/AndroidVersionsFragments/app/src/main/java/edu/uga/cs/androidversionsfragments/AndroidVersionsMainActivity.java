package edu.uga.cs.androidversionsfragments;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


/**
 * A simple activity to show Android versions utilizing fragments.
 * KJK
 */
public class AndroidVersionsMainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "AndroidVersions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onCreate(): savedInstanceState: " + savedInstanceState );

        // this call will create the UI based on the screen in portrait orientation.
        // /res/layout/activity_android_versions_main.xml will be used;
        // in landscape orientation /res/layout-land/activity_android_versions_main.xml will be used
        setContentView( R.layout.activity_android_versions_main );
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "AndroidVersionsMainActivity.onRestart()" );
        super.onRestart();
    }
}
