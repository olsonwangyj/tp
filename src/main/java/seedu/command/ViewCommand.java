package seedu.command;

import seedu.checkers.ViewChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidViewKeywordException;
import seedu.exceptions.EmptyListException;
import seedu.exceptions.ViewIndexOutOfRangeException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.MealList;
import seedu.ui.UserInterface;


import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String RECIPES_SYMBOL = "/r";
    private static final String WISH_LIST_SYMBOL = "/w";
    String recipesOrWishlist;


    public ViewCommand(String userInput) {
        validUserInput = userInput.trim();
        this.lowerCaseInput = validUserInput.toLowerCase();
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        setRecipesOrWishlist();
        boolean isValidUserInput = checkValidUserInput();
        if (!isValidUserInput) {
            logger.severe("Huge issue detected! The user input format remains invalid despite " +
                    "passing all the checks for input formatting error.");
        }
        assert isValidUserInput;
        viewMeal(recipesOrWishlist, mealManager, ui);
    }

    private void viewMeal(String recipesOrWishlist, MealManager mealManager, UserInterface ui)
            throws EZMealPlanException {
        MealList mealList = recipesOrWishlist.equals(RECIPES_SYMBOL) ? mealManager.getRecipesList()
                : mealManager.getWishList();
        if (mealList.getList().isEmpty()) {
            throw new EmptyListException(mealList.getMealListName());
        }
        int afterKeywordIndex = lowerCaseInput.indexOf(recipesOrWishlist) + recipesOrWishlist.length();
        String afterKeyword = lowerCaseInput.substring(afterKeywordIndex).trim();
        int mealListIndex = Integer.parseInt(afterKeyword);
        Meal meal = getMeal(mealList, mealListIndex);
        ui.printIngredientList(meal);
    }

    private static Meal getMeal(MealList mealList, int mealListIndex) throws EZMealPlanException {
        try {
            int indexOffset = 1;
            int actualIndex = mealListIndex - indexOffset;
            return mealList.getList().get(actualIndex);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ViewIndexOutOfRangeException(mealListIndex, mealList);
        }
    }

    public void setRecipesOrWishlist() throws EZMealPlanException {
        boolean isContainsRecipesSymbol = this.lowerCaseInput.contains(RECIPES_SYMBOL) &&
                                          !this.lowerCaseInput.contains(WISH_LIST_SYMBOL);
        boolean isContainsWishlistSymbol = this.lowerCaseInput.contains(WISH_LIST_SYMBOL) &&
                                           !this.lowerCaseInput.contains(RECIPES_SYMBOL);
        if (isContainsRecipesSymbol) {
            recipesOrWishlist = RECIPES_SYMBOL;
        } else if (isContainsWishlistSymbol) {
            recipesOrWishlist = WISH_LIST_SYMBOL;
        } else {
            throw new InvalidViewKeywordException();
        }
    }

    private boolean checkValidUserInput() throws EZMealPlanException {
        ViewChecker checker = new ViewChecker(validUserInput, recipesOrWishlist);
        checker.check();
        return checker.isPassed();
    }
}

