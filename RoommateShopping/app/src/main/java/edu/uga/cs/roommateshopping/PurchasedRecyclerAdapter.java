package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A {@link RecyclerView} subclass that represents the recycler view for the purchased items.
 */
public class PurchasedRecyclerAdapter extends RecyclerView.Adapter<PurchasedRecyclerAdapter.PurchasedHolder> {

    private List<Item> list;
    private Context context;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    /**
     * Constructor that initializes the list and context of the
     * recycler.
     * @param list Represents the list in the recycler.
     * @param context Represents the context in the recycler.
     */
    public PurchasedRecyclerAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * Inner class that is a {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * subclass that holds one item to show.
     */
    class PurchasedHolder extends RecyclerView.ViewHolder {
        TextView item;
        TextView quantity;
        TextView price;

        /**
         * Constructor that initializes the name, quantity, and price
         * of the purchased item.
         * @param itemView Represents the view of each purchased item.
         */
        public PurchasedHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
        }
    }

    /**
     * Inflates the recycler adapter based on its parent.
     * @param parent Reference to a ViewGroup object
     * @param viewType Represents the current view type.
     * @return Returns the layout of the recycler adapter.
     */
    @NonNull
    @Override
    public PurchasedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item, parent, false);

        // Get User ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView listedBy = view.findViewById(R.id.roommate);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Sets the name of the user that assigned the item.
             * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String name = user.getName();
                    listedBy.setText("Listed By: " + name);
                }
            }
            /**
             * Displays an error message if there was an issue finding the user's name.
             * @param error Reference to a DatabaseError object that represents the error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return new PurchasedHolder(view);
    }

    /**
     * Fills the values of the views to display the purchased item.
     * @param holder Represents the holder of each item.
     * @param position Represents the position of the item in the list.
     */
    @Override
    public void onBindViewHolder(PurchasedHolder holder, int position) {
        Item item1 = list.get(position);

        String key = item1.getKey();
        String item2 = item1.getItem();
        int quantity = item1.getQuantity();
        double price = item1.getPrice();

        holder.item.setText(item1.getItem());
        holder.quantity.setText("Quantity: " +Integer.toString(item1.getQuantity()));
        holder.price.setText("Price Per Unit: $" + Double.toString(item1.getPrice()));

        // Click Item to Edit
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the function where the user can click
             * on the item to edit it.
             * @param view Reference to a View object that represents the current view.
             */
            @Override
            public void onClick(View view) {
                EditPurchasedDialogFragment editFragment = EditPurchasedDialogFragment.newInstance
                        (holder.getAdapterPosition(), key, item2, quantity, price);
                editFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    }

    /**
     * Gets the number of items in the list view.
     * @return Returns the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}