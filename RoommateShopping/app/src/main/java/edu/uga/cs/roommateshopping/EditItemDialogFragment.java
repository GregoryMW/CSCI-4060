package edu.uga.cs.roommateshopping;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A {@link DialogFragment} subclass that represents the edit item dialog.
 */
public class EditItemDialogFragment extends DialogFragment {

    public static final int UPDATE = 1;
    public static final int REMOVE = 2;
    public static final int PURCHASE = 3;

    private EditText itemInput;
    private EditText quantityInput;
    private EditText priceInput;
    private Button cancel;
    private Button remove;
    private Button purchase;
    private Button update;

    int position;
    String key;
    String item;
    int quantity;
    double price;

    /**
     * Obtains the Dialog interface to edit an item.
     */
    public interface EditItemDialogListener {
        void updateItem(int position, Item item, int action);
    }

    /**
     * Creates a new instance of each item already added to the list.
     * The edit item dialog initializes the item's position, key, item,
     * quantity, and price.
     * @param position Represents the position of the item in the list.
     * @param key Represents the item's key in the database.
     * @param item Represents item the added already listed.
     * @param quantity Represents the number of units needed per item.
     * @param price Represents the price of each item unit.
     * @return Returns the edit item dialog.
     */
    public static EditItemDialogFragment newInstance(int position, String key, String item, int quantity, double price) {
        EditItemDialogFragment dialog = new EditItemDialogFragment();

        // Supply item values as an argument
        Bundle args = new Bundle();

        args.putString("key", key);
        args.putInt( "position", position);
        args.putString("item", item);
        args.putInt("quantity", quantity);
        args.putDouble("price", price);

        dialog.setArguments(args);

        return dialog;
    }

    /**
     * Creates and sets the variables of the Dialog for the edit item function.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data of the dialog.
     * @return Returns the edit item dialog displaying the current variables assigned to each item.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        item = getArguments().getString("item");
        quantity = getArguments().getInt("quantity");
        price = getArguments().getDouble("price");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.edit_item_dialog, getActivity().findViewById(R.id.rootEdit));

        itemInput = layout.findViewById(R.id.item);
        quantityInput = layout.findViewById(R.id.quantity);
        priceInput = layout.findViewById(R.id.price);
        cancel = layout.findViewById(R.id.cancel);
        remove = layout.findViewById(R.id.remove);
        purchase = layout.findViewById(R.id.purchase);
        update = layout.findViewById(R.id.update);

        // Prefill with current values
        itemInput.setText(item);
        quantityInput.setText(Integer.toString(quantity));
        priceInput.setText(Double.toString(price));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setView(layout);

        builder.setTitle("Edit Item");

        cancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of exiting out of the dialog.
             * @param view Reference to a View object that represents the view of the dialog.
             */
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of removing an item from the list
             * through the dialog.
             * @param view Reference to a View object that represents the view of the dialog.
             */
            @Override
            public void onClick(View view) {
                Item item1 = new Item(item, quantity, price);
                item1.setKey(key);

                // Removes Item
                EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
                listener.updateItem(position, item1, REMOVE);

                dismiss();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of moving an item from the list to the purchase
             * list through the dialog.
             * @param view Reference to a View object that represents the view of the dialog.
             */
            @Override
            public void onClick(View view) {
                Item item1 = new Item(item, quantity, price);
                item1.setKey(key);

                EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
                listener.updateItem(position, item1, PURCHASE);

                dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            /**
             * Implements the functionality of updating an item from the dialog.
             * The user is able to update the item name, quantity, and price.
             * @param view Reference to a View object that represents the view of the dialog.
             */
            @Override
            public void onClick(View view) {
                String item = itemInput.getText().toString().trim();
                int quantity = Integer.parseInt(quantityInput.getText().toString().trim());
                double price = Double.parseDouble(priceInput.getText().toString());

                Item item1 = new Item(item, quantity, price);
                item1.setKey(key);

                // Update Item
                EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
                listener.updateItem(position, item1, UPDATE);

                dismiss();
            }
        });

        return builder.create();
    }
}