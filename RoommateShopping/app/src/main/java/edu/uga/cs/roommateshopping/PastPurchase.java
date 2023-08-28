package edu.uga.cs.roommateshopping;

/**
 * This class represents a single past completed purchase, including the
 * key, date, number of items in the purchase, and the cost each roommate
 * owed.
 */
public class PastPurchase {

    private String key;
    private String date;
    private int numItems;
    private double costPer;

    /**
     * Constructor of a past purchase that initializes a purchase's key,
     * date, number of items, and cost per roommate.
     */
    public PastPurchase() {
        this.key = null;
        this.date = null;
        this.numItems = 0;
        this.costPer = 0;
    }

    /**
     * Constructor of a past purchase that initializes a purchase's
     * date, number of items, and cost per roommate.
     */
    public PastPurchase(String date, int numItems, double costPer) {
        this.key = null;
        this.date = date;
        this.numItems = numItems;
        this.costPer = costPer;
    }

    /**
     * Gets the key of a past purchase.
     * @return Returns the key of the past purchase.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key for each past purchase.
     * @param key Represents the key for each past purchase.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the date of a past purchase.
     * @return Returns the date of the past purchase.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for each past purchase.
     * @param date Represents the date for each past purchase.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the number of items in each past purchase.
     * @return Returns the number of items of each past purchase.
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Sets the number of items for each past purchase.
     * @param numItems Represents the number of items for each past purchase.
     */
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    /**
     * Gets the cost per roommate of a past purchase.
     * @return Returns the cost per roommate of the past purchase.
     */
    public double getCostPer() {
        return costPer;
    }

    /**
     * Sets the cost for each past purchase.
     * @param costPer Represents the cost for each past purchase.
     */
    public void setCostPer(double costPer) {
        this.costPer = costPer;
    }

    /**
     * Returns the string value of the date, number of items, and cost.
     * @return
     */
    public String toString() {
        return date + " " + numItems + " " + costPer;
    }
}
