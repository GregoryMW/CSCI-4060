package edu.uga.cs.units;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

// this class does not save its state;  you may add the necessary save/resotre
// callbacks as an exercise
public class ConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // UI object references
        EditText    editText;
        TextView    resultView;
        TextView    infoView;
        Button      convertButton;
        RadioGroup  radioGroup;
        RadioButton leftRadioButton;
        RadioButton rightRadioButton;
        int leftRadioButtonId;
        int rightRadioButtonId;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        // obtain references to the UI objects
        infoView = findViewById( R.id.textView2 );
        resultView = findViewById( R.id.textView4 );
        leftRadioButtonId = R.id.radioButton2;
        rightRadioButtonId = R.id.radioButton;
        leftRadioButton = findViewById( leftRadioButtonId );
        rightRadioButton = findViewById( rightRadioButtonId );
        convertButton = findViewById( R.id.button2 );
        editText = findViewById( R.id.editText );
        radioGroup = findViewById( R.id.radioGroup );

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int conversionType = intent.getIntExtra( StartActivity.CONVTYPE, StartActivity.LENGTHS );

        // update the UI to show the selected conversion labels on RadioButtons
        if( conversionType == StartActivity.LENGTHS ) {
            infoView.setText( "Perform Lengh Conversion\n\nYou can convert from miles to kilometers or vice versa" );
            leftRadioButton.setText( "Miles to Km" );
            rightRadioButton.setText( "Km to Miles" );
        }
        else {
            infoView.setText( "Perform Weight Conversion\n\nYou can convert from pounds to kilograms or vice versa" );
            leftRadioButton.setText( "Pounds to Kg" );
            rightRadioButton.setText( "Kg to Pounds" );
        }

        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                double inputValue;
                String inputString;
                double result = 0.0;
                String resultString;
                int direction;

                direction = radioGroup.getCheckedRadioButtonId();

                if( direction == -1 ) {
                    Toast toast = Toast.makeText( getApplicationContext(),
                                                 "Please indicate the conversion direction",
                                                  Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                inputString = editText.getText().toString();
                if( inputString == null || inputString.length() == 0 ) {
                    Toast toast = Toast.makeText( getApplicationContext(),
                            "Please provide a value to convert",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                inputValue = Double.parseDouble( inputString );

                // compute the result, based on the requested conversion
                if( conversionType == StartActivity.LENGTHS && direction == leftRadioButtonId ) {  // Miles to KM
                    result = inputValue * 1.609347;
                }
                else if( conversionType == StartActivity.LENGTHS && direction == rightRadioButtonId ) { // KM to Miles
                    result = inputValue * 0.6213;
                }
                if( conversionType == StartActivity.WEIGHTS && direction == leftRadioButtonId ) { // Pounds to Kg
                    result = inputValue * 0.453592;
                }
                else if( conversionType == StartActivity.WEIGHTS && direction == rightRadioButtonId ) { // Kg to Pounds
                    result = inputValue * 2.20462;
                }

                // format the result
                DecimalFormat format = new DecimalFormat(".####");
                //double twoDecimal =  Double.valueOf( format.format( result ) );
                resultString = format.format( result );

                // update the result TextView
                resultView.setText( resultString );
            }
        });
    }
}
