package edu.uga.cs.roommateshopping;

/**
 * This class represents a single item, including the name of the
 * item, quantity, and price.
 */
public class Item {

    private String key;
    private String item;
    private int quantity;
    private double price;

    /**
     * Constructor of an item that initializes an item's key, name,
     * quantity, and price.
     */
    public Item() {
        this.key = null;
        this.item = null;
        this.quantity = 0;
        this.price = 0;
    }

    /**
     * Constructor of an item that initializes an item's name, quantity,
     * and price.
     * @param item Represents the name of the item.
     * @param quantity Represents the number of units needed per item.
     * @param price Represents the price of an item.
     */
    public Item(String item, int quantity, double price) {
        this.key = null;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the key of an item.
     * @return Returns the key of the item.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of an item.
     * @param key Represents an item's unique key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the name of an item.
     * @return Returns the name of the item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the name of an item.
     * @param item Represents an item's unique name.
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Gets the quantity of units needed for an item.
     * @return Returns the quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of units needed for an item.
     * @param quantity Represents an item's unique quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of an item.
     * @return Returns the price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of an item.
     * @param price Represents an item's unique price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Creates a string.
     * @return Returns the string format.
     */
    public String toString() {
        return item + " " + quantity + " " + price;
    }
}
