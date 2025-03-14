package seedu.exceptions;

public class DuplicateIngredientException extends EZMealPlanException {
    String ingredientName;
    String mealName;

    public DuplicateIngredientException(String ingredientName, String mealName) {
        this.ingredientName = ingredientName;
        this.mealName = mealName;
    }

    @Override
    public String getMessage() {
        return "Ingredient " + this.ingredientName + " already exists in the meal " + this.mealName + ".";
    }
}
