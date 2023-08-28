package edu.uga.cs.tripcost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "TripCost";

    private EditText distanceEditText;
    private EditText gasEditText;
    private EditText mpgEditText;
    private TextView tripCostEditText;
    private Button compute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i( DEBUG_TAG, "MainActivity.onCreate()" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtain references to the relevant UI objects in the screen
        distanceEditText = findViewById( R.id.editTextNumberDecimal );
        mpgEditText = findViewById( R.id.editTextNumberDecimal3 );
        gasEditText = findViewById( R.id.editTextNumberDecimal2 );
        tripCostEditText = findViewById( R.id.textView );
        compute = findViewById( R.id.button );

        // set the compute Button's listener
        compute.setOnClickListener( new ButtonClickListener() );
    }

    // the listener here is defined as a private inner class which provides the
    // onClick method implementation
    private class ButtonClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            double distance;
            double mpg;
            double gasPerGal;
            double tripCost;

            try {
                // obtain the values entered by the user
                distance = Double.parseDouble( distanceEditText.getText().toString() );
                mpg = Double.parseDouble( mpgEditText.getText().toString() );
                gasPerGal = Double.parseDouble( gasEditText.getText().toString() );
            }
            catch( NumberFormatException nfe ) {
                // This check is just a precaution, since the user will be able to enter only numbers
                // into the EditText, as currently included in the layout (note the
                // android:inputType="numberDecimal" attribute).
                // However, we should have this check in case someone changes
                // the layout and uses more general EditTexts, accepting any chars as input.

                // Toast is a short message displayed to the user
                Toast toast = Toast.makeText( getApplicationContext(),
                        "Enter positive decimal values",
                        Toast.LENGTH_SHORT );
                toast.show();
                tripCostEditText.setText( "$0.00" );
                return;
            }

            // check if all values entered are positive
            if( distance <= 0.0 || mpg <= 0.0 || gasPerGal <= 0.0 ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                        "Enter positive decimal values",
                        Toast.LENGTH_SHORT );
                toast.show();
                tripCostEditText.setText( "$0.00" );
                return;
            }

            // compute and format the cost amount
            tripCost = distance / mpg * gasPerGal;
            DecimalFormat df = new DecimalFormat( "####0.00" );

            // show the computed cost to the user
            tripCostEditText.setText( "$" + df.format( tripCost ) );
        }
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "MainActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "MainActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "MainActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "MainActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "MainActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "MainActivity.onRestart()" );
        super.onRestart();
    }
}