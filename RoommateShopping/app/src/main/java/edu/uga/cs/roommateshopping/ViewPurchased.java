package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A {@link EditItemDialogFragment} and {@link SettleCostDialogFragment} subclass that
 * represents the view purchased list screen.
 */
public class ViewPurchased extends AppCompatActivity implements EditPurchasedDialogFragment.EditPurchasedDialogListener, SettleCostDialogFragment.SettleCostDialogListener {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private RecyclerView recyclerView;
    private PurchasedRecyclerAdapter adapter;
    private List<Item> list;
    private FirebaseDatabase database;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Creates and sets the variables of the View for the view purchased list activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_purchased);
        recyclerView = findViewById(R.id.recyclerView);

        ExtendedFloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of displaying the dialog.
             * @param view Reference to a View object that represents the current view.
             */
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new SettleCostDialogFragment();
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        // Initialize Item List
        list = new ArrayList<Item>();

        // Linear Layout Manager for Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PurchasedRecyclerAdapter(list, ViewPurchased.this);
        recyclerView.setAdapter(adapter);

        // Get Firebase Instance Reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Purchased");

        // Listener to Receive Value for Database Reference
        reference.addValueEventListener(new ValueEventListener() {
            /**
             * Adds an item to the purchased list.
             * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Item item1 = postSnapshot.getValue(Item.class);
                    item1.setKey(postSnapshot.getKey());
                    list.add(item1);
                }
                adapter.notifyDataSetChanged();
            }
            /**
             * Displays an error message if there was an issue adding an
             * item to the purchased list.
             * @param error Reference to a DatabaseError object that represents the error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Implements the functionality of the user being able to update
     * an item from the purchased list.
     * @param position Represents the position of the item in the list.
     * @param item Represents the item being updated.
     * @param action Represents the action the user wants to take on the item.
     */
    @Override
    public void updatePurchased(int position, Item item, int action) {
        if (action == EditPurchasedDialogFragment.UPDATE) {
            // Update Recycler Adapter to Show Changes
            adapter.notifyItemChanged(position);

            // Update in Firebase
            DatabaseReference reference = database.getReference().child("Purchased").child(item.getKey());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Updates an item on the purchased list. The user is able
                 * to update the name of the item, quantity, and price.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that item was successfully updated
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Updated Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue updating an
                 * item on the purchased list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Failed to Update Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (action == EditPurchasedDialogFragment.REMOVE) {
            // Remove from List
            list.remove(position);

            // Update Adapter
            adapter.notifyItemRemoved(position);

            // Remove from Firebase
            DatabaseReference reference = database.getReference().child("Purchased").child(item.getKey());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Removes an item from the purchased list.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that item was successfully removed.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Removed Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue removing an
                 * item from the purchased list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Failed to Remove Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (action == EditPurchasedDialogFragment.TOLIST) {
            list.remove(position);
            adapter.notifyItemRemoved(position);

            DatabaseReference itemsRef = database.getReference().child("Purchased").child(item.getKey());
            DatabaseReference purchasedRef = database.getReference("Items");

            purchasedRef.push().setValue(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that item was successfully returned to the shopping list.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Item Returned to Shopping List: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        /**
                         * Displays an error message indicating the item failed to return to the shopping list.
                         * @param e Reference to an Exception object
                         */
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to Return to Shopping List: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });

            itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Removes the item from the list.
                 * @param snapshot eference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that item was successfully removed.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            //Toast.makeText(getApplicationContext(), "Removed Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue removing an
                 * item from the list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //Toast.makeText(getApplicationContext(), "Failed to Remove Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Settles the cost based on the number of roommates entered.
     * @param numRoommates Represents the number of roommates.
     * @return Returns the cost each roommate owes.
     */
    @Override
    public double settleCost(double numRoommates) {
        double totalCost = 0;
        int numItems = 0;
        for (Item item : list) {
            numItems += item.getQuantity();
            totalCost += item.getPrice() * (double)item.getQuantity();
        }
        DatabaseReference pastRef = database.getReference("Past Purchases");
        PastPurchase purchase = new PastPurchase(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()), numItems, totalCost/numRoommates);
        pastRef.push().setValue(purchase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * Indicates to the user that new purchase was complete.
                     * @param unused Represents a Void object
                     */
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "New Purchase Completed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    /**
                     * Indicates to the user that the purchase failed to complete.
                     * @param e Represents an Exception object
                     */
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to Complete Purchase", Toast.LENGTH_SHORT).show();
                    }
                });
        return Double.parseDouble(df.format(totalCost/numRoommates));
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