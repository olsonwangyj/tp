package seedu.food;

import seedu.exceptions.EZMealPlanException;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public class MealTest {
    @Test
    public void meal_success() throws EZMealPlanException {
        String chickenRice = "Chicken Rice";
        Meal newChickenRiceMeal = new Meal(chickenRice);
        String[] crIngredientNames = {"chicken breast", "rice", "egg", "cucumber"};
        String[] crIngredientPrices = {"2.5", "1.5", "0.5", "1"};
        for (int i = 0; i < crIngredientNames.length; i++) {
            String currentIngName = crIngredientNames[i];
            String currentIngPrice = crIngredientPrices[i];
            newChickenRiceMeal.addIngredient(currentIngName, currentIngPrice);
            Double computedMealPrice = newChickenRiceMeal.computeMealPrice();
            Double currentMealPrice = newChickenRiceMeal.getPrice();
            assertEquals(currentMealPrice, computedMealPrice);
        }
        double expectedPrice = 2.5 + 1.5 + 0.5 + 1;
        assertEquals(expectedPrice, newChickenRiceMeal.getPrice());
        String expectedString = "Chicken Rice ($5.50)";
        assertEquals(expectedString, newChickenRiceMeal.toString());
    }

    @Test
    public void ingredient_success() throws EZMealPlanException {
        String ingredientName = "salt";
        double ingredientPrice = 2.5;
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
        String expectedOutput = "salt ($2.50)";
        assertEquals(expectedOutput, newIngredient.toString());
    }
}
