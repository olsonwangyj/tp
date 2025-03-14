package seedu.food;

import seedu.exceptions.InvalidPriceException;


public abstract class Product {
    protected String name;
    protected double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws InvalidPriceException {
        int zeroDollars = 0;
        String name = getName();
        if (price <= zeroDollars) {
            throw new InvalidPriceException(name);
        }
        this.price = price;
    }

    public String toString() {
        String price = String.format("%.2f", getPrice());
        return getName() + " ($" + price +")";
    }
}
