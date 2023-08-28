package edu.uga.cs.roommateshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class implements the functionality of adding an item to the
 * shopping list.
 */
public class AddItem extends AppCompatActivity {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private EditText itemInput;
    private EditText quantityInput;
    private EditText priceInput;

    Button addItem;

    /**
     * Creates and sets the variables of the View for the activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        itemInput = findViewById(R.id.item);
        quantityInput = findViewById(R.id.quantity);
        priceInput = findViewById(R.id.price);
        addItem = findViewById(R.id.addButton);

        addItem.setOnClickListener(new ButtonClickListener());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * add an item through the click of the button present.
     */
    private class ButtonClickListener implements View.OnClickListener {
        /**
         * Implements the functionality of adding an item to the shopping list.
         * The user is able to click. Then, is prompted to enter the item, quantity, and price.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            String item = itemInput.getText().toString().trim();
            int quantity = Integer.parseInt(quantityInput.getText().toString());
            double price = Double.parseDouble(priceInput.getText().toString());

            final Item item1 = new Item(item, quantity, price);

            // Add Item to Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Items");

             // Call to push() appends a new node to the existing list (one is created
             // if this is done for the first time). Then, we set the value in the newly created
             // list node to store the new item.
            reference.push().setValue(item1).addOnSuccessListener(new OnSuccessListener<Void>() {
                /**
                 * Indicates to the user that the item was successfully added.
                 * Then, redirects the user back to the shopping list.
                 * @param unused Represents a Void object
                 */
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "New Item: " + item1.getItem(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                /**
                 * Indicates to the user that the item was not successfully added.
                 * Then, redirects the user to attempt again.
                 * @param e Represents an Exception object
                 */
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to Create Item: " + item1.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Saves the current state of the activity
     * @param outState The bundle that is saved
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of item position
        outState.putInt(SELECTED_ITEM_POSITION, mPosition);
    }

    /**
     * Restores the state of the activity
     * @param savedInstanceState The saved state that is restored
     */
    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        mPosition = savedInstanceState.getInt(SELECTED_ITEM_POSITION);
    }

    /**
     * Chnages the functionality of the action bar back button to the same as that of the system back button
     * @param item The item of the action bar, in this case the back button
     * @return True if the selected item is indeed the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();    //Call the back button's method
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
