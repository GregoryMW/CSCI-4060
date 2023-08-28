package edu.uga.cs.roommateshopping;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link AddItemDialogFragment} and {@link EditItemDialogFragment} subclass that
 * represents the view list screen.
 */
public class ViewListActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener, EditItemDialogFragment.EditItemDialogListener {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private RecyclerView recyclerView;
    private ItemRecyclerAdapter adapter;
    private List<Item> list;
    private FirebaseDatabase database;

    /**
     * Creates and sets the variables of the View for the view list activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_items);
        recyclerView = findViewById(R.id.recyclerView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of displaying the dialog.
             * @param view Reference to a View object that represents the current view.
             */
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddItemDialogFragment();
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        // Initialize Item List
        list = new ArrayList<Item>();

        // Linear Layout Manager for Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemRecyclerAdapter(list, ViewListActivity.this);
        recyclerView.setAdapter(adapter);

        // Get Firebase Instance Reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Items");

        // Listener to Receive Value for Database Reference
        reference.addValueEventListener(new ValueEventListener() {
            /**
             * Adds an item to the list.
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
             * item to the list.
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
     * Implements the functionality of the user being able to add
     * an item to the list.
     * @param item Reference to an Item object that represents the item being added.
     */
    @Override
    public void addItem(Item item) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Items");

        reference.push().setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * Indicates to the user that the item was successfully added.
                     * Then, redirects the user back to the shopping list.
                     * @param unused Represents a Void object
                     */
                    @Override
                    public void onSuccess(Void unused) {
                        recyclerView.post(new Runnable() {
                            /**
                             * Runs the recycler view of the list. The user is able
                             * to scroll.
                             */
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(list.size() - 1);
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Item Added: " + item.getItem(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    /**
                     * Indicates to the user that the item was not successfully added.
                     * Then, redirects the user to attempt again.
                     * @param e Represents an Exception object
                     */
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to Add: " + item.getItem(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Implements the functionality of the user being able to update
     * an item from the list.
     * @param position Represents the position of the item in the list.
     * @param item Represents the item being updated.
     * @param action Represents the action the user wants to take on the item.
     */
    @Override
    public void updateItem(int position, Item item, int action) {
        if (action == EditItemDialogFragment.UPDATE) {
            // Update Recycler Adapter to Show Changes
            adapter.notifyItemChanged(position);

            // Update in Firebase
            DatabaseReference reference = database.getReference().child("Items").child(item.getKey());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Updates the name of the item.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that the item was successfully updated.
                         * Then, redirects the user back to the shopping list.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Updated Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue updating
                 * the name of the item.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Failed to Update Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (action == EditItemDialogFragment.REMOVE) {
            // Remove from List
            list.remove(position);

            // Update Adapter
            adapter.notifyItemRemoved(position);

            // Remove from Firebase
            DatabaseReference reference = database.getReference().child("Items").child(item.getKey());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Removes the item from the list.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that the item was successfully removed.
                         * Then, redirects the user back to the shopping list.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Removed Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue removing
                 * the item from the list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Failed to Remove Item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (action == EditItemDialogFragment.PURCHASE) {
            list.remove(position);
            adapter.notifyItemRemoved(position);

            DatabaseReference itemsRef = database.getReference().child("Items").child(item.getKey());
            DatabaseReference purchasedRef = database.getReference("Purchased");

            purchasedRef.push().setValue(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that the item was successfully
                         * moved to the purchased list.
                         * Then, redirects the user back to the shopping list.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Item Purchased: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        /**
                         * Indicates to the user that the item was not successfully
                         * moved to the purchased list.
                         * Then, redirects the user to attempt again.
                         * @param e Represents an Exception object
                         */
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to Purchase: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        }
                    });

            itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Moves the item from the view list to the purchased list.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * Indicates to the user that the item was successfully
                         * moved to the purchased list.
                         * Then, redirects the user back to the shopping list.
                         * @param unused Represents a Void object
                         */
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
                /**
                 * Displays an error message if there was an issue moving
                 * the item to the purchased list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
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