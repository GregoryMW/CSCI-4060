package edu.uga.cs.roommateshopping;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link EditItemDialogFragment} subclass that represents the view past purchased screen.
 */
public class ViewPastPurchased extends AppCompatActivity implements EditPastPurchasedDialogFragment.EditPastPurchasedDialogListener {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private RecyclerView recyclerView;
    private PastPurchasedRecyclerAdapter adapter;
    private List<PastPurchase> list;
    private FirebaseDatabase database;

    /**
     * Creates and sets the variables of the View for the view past purchased activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_past_purchased);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize Item List
        list = new ArrayList<PastPurchase>();

        // Linear Layout Manager for Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PastPurchasedRecyclerAdapter(list, ViewPastPurchased.this);
        recyclerView.setAdapter(adapter);

        // Get Firebase Instance Reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Past Purchases");

        // Listener to Receive Value for Database Reference
        reference.addValueEventListener(new ValueEventListener() {
            /**
             * Receives the value for the database reference.
             * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    PastPurchase purchase = postSnapshot.getValue(PastPurchase.class);
                    purchase.setKey(postSnapshot.getKey());
                    list.add(purchase);
                }
                adapter.notifyDataSetChanged();
            }
            /**
             * Displays an error message if there was an issue receiving the
             * value for the database reference.
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
     * Implements the functionality of being able to remove a purchase from the list.
     * @param position Represents the position of the purchase in the list.
     * @param purchase Represents the purchase in the list.
     */
    @Override
    public void removePurchase(int position, PastPurchase purchase) {
        // Remove from List
        list.remove(position);

        // Update Adapter
        adapter.notifyItemRemoved(position);

        // Remove from Firebase
        DatabaseReference reference = database.getReference().child("Past Purchases").child(purchase.getKey());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Removes the past purchase from the list.
             * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * Indicates to the user that the item was successfully removed.
                     * Then, redirects the user back to the past purchased list.
                     * @param unused Represents a Void object
                     */
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Removed Past Purchase: " + purchase.getDate(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            /**
             * Displays an error message if there was an issue removing the purchase
             * from the list.
             * @param error Reference to a DatabaseError object that represents the error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to Remove Past Purchase: " + purchase.getDate(), Toast.LENGTH_SHORT).show();
            }
        });
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
