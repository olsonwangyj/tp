package seedu.food;

import seedu.exceptions.InvalidPriceException;

public class Ingredient extends Product {
    public Ingredient(String ingredientName, double ingredientPrice) throws InvalidPriceException {
        setName(ingredientName);
        setPrice(ingredientPrice);
    }

    public void setPrice(double price) throws InvalidPriceException {
        super.setPrice(price);
    }

    public String toString(){
        return super.toString();
    }
}
