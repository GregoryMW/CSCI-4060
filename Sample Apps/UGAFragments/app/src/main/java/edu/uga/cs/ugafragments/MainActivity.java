package edu.uga.cs.ugafragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "UGAInfoMainActivity";

    // The onCreate callback is very simple.  However, several things will be happening here.
    // There are two layouts for this activity: portrait and landscape.  Android will pick the
    // appropriate layout depending on the device's orientation.
    //
    // In portrait mode, the layout has only one fragment -- the ChoiceFragment.  The class for it
    // is specified in the XML layout.
    // In landscape mode, there are two fragments: the ChoiceFragment (on the left) and the InfoFragment
    // (on the right).  The layout has a <fragment> element for ChoiceFragment and <FrameLayout> for
    // InfoFragment.  When ChoiceFragment is automatically created by Android while in the landscape mode,
    // the fragment class will sense that it is running in the landscape mode and will create an InfoFragment
    // It will then replace it with the <FrameLayout> on the right.  The ChoiceFragment will act in a way
    // as the "driver".
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( TAG, "onCreate()" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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