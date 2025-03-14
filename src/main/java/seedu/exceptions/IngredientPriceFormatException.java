package seedu.exceptions;

public class IngredientPriceFormatException extends EZMealPlanException {
    String ingredientName;

    public IngredientPriceFormatException(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String getMessage() {
        return "Price input for " + ingredientName + " cannot be converted to double. " +
                "Please enter a valid string price input.";
    }
}
