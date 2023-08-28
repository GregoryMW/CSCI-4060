package edu.uga.cs.units;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "edu.uga.cs.units.Units";

    public static final String CONVTYPE = "edu.uga.cs.units.CONVTYPE";
    public static final int LENGTHS = 1;
    public static final int WEIGHTS = 2;

    // UI object references
    private Spinner  spinner;
    private TextView infoView;
    private Button   convertButton;
    private int      spinnerPos;

    // which type of conversion to perform
    private int      conversionType = LENGTHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(DEBUG_TAG, "MainActivity.onCreate(): savedInstanceState: " + savedInstanceState );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // obtain references to UI objects
        spinner = findViewById( R.id.spinner2 );
        infoView = findViewById( R.id.textView3 );
        convertButton = findViewById( R.id.button3 );

        // set the listener for the Spinner
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int pos, long id ) {
                // An item was selected. You can retrieve the selected item using
                if( ((String) parent.getItemAtPosition(pos)).equals( "Convert lengths" ) )
                    conversionType = LENGTHS;
                else if( ((String) parent.getItemAtPosition(pos)).equals( "Convert weights" ) )
                    conversionType = WEIGHTS;
                spinnerPos = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        } );

        // set the listener for the Button to transition to conversion activity
        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), ConversionActivity.class );
                // send the conversion type info to the child activity
                intent.putExtra( CONVTYPE, conversionType );
                v.getContext().startActivity( intent );

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(DEBUG_TAG, "MainActivity.onSaveInstanceState(): " + spinnerPos);

        // Save the counter value
        savedInstanceState.putInt( "spinnerpos", spinnerPos );

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(DEBUG_TAG, "MainActivity.onRestoreInstanceState()" );

        // Restore the counter value
        if( savedInstanceState != null ) {
            spinnerPos = savedInstanceState.getInt("spinnerpos");
            spinner.setSelection( spinnerPos );
            Log.d(DEBUG_TAG, "MainActivity.onRestoreInstanceState(): " + spinnerPos);
        }

    }
}
