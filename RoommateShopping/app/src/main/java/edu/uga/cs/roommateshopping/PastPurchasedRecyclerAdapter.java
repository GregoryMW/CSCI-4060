package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * A {@link RecyclerView} subclass that represents the recycler view for the past purchased items.
 */
public class PastPurchasedRecyclerAdapter extends RecyclerView.Adapter<PastPurchasedRecyclerAdapter.ItemHolder> {

    private List<PastPurchase> list;
    private Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Constructor that initializes the list and context of the
     * recycler.
     * @param list Represents the list in the recycler.
     * @param context Represents the context in the recycler.
     */
    public PastPurchasedRecyclerAdapter(List<PastPurchase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * Inner class that is a {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * subclass that holds one item to show.
     */
    class ItemHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView numItems;
        TextView costPer;

        /**
         * Constructor that initializes the date, number of items, and
         * cost per roommate owed of the past purchased item.
         * @param itemView Represents the view of each purchased item.
         */
        public ItemHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            numItems = itemView.findViewById(R.id.numItems);
            costPer = itemView.findViewById(R.id.costPer);
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
    public PastPurchasedRecyclerAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_purchase, parent, false);
        return new PastPurchasedRecyclerAdapter.ItemHolder(view);
    }

    /**
     * Fills the values of the views to display the past purchases.
     * @param holder Represents the holder of each item.
     * @param position Represents the position of the item in the list.
     */
    @Override
    public void onBindViewHolder(PastPurchasedRecyclerAdapter.ItemHolder holder, int position) {
        PastPurchase pastPurchase = list.get(position);

        String key = pastPurchase.getKey();
        String date = pastPurchase.getDate();
        int numItems = pastPurchase.getNumItems();
        double costPer = pastPurchase.getCostPer();

        holder.costPer.setText("Cost Per Roommate: $" + df.format(pastPurchase.getCostPer()));
        holder.numItems.setText("Number of Items: " + pastPurchase.getNumItems());
        holder.date.setText("Date: " + pastPurchase.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the function where the user can click
             * on the past purchase to edit it.
             * @param view Reference to a View object that represents the current view.
             */
            @Override
            public void onClick(View view) {
                EditPastPurchasedDialogFragment editPastFragment = EditPastPurchasedDialogFragment.newInstance
                        (holder.getAdapterPosition(), key, date, numItems, costPer);
                editPastFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), null);
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
