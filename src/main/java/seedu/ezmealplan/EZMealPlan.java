package seedu.ezmealplan;

import seedu.command.Command;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.ParserException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.MealList;
import seedu.storage.Storage;
import seedu.ui.UserInterface;
import seedu.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EZMealPlan {
    /**
     * Main entry-point for EZMealPlan.
     */
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final UserInterface ui = new UserInterface();
    private static final MealManager mealManager = new MealManager();

    public static void main(String[] args) {
        String fileName = "EZMealPlan.log";
        setupLogger(fileName);
        checkConstructedLists();
        // Check for valid meals that are present in the wishlist but not in the recipes list
        // and remove these meals from the recipes list.
        mealManager.removeIllegalMeals();
        logger.fine("running EZMealPlan");
        ui.printGreetingMessage();
        String userInput;
        while (true) {
            ui.prompt();
            userInput = ui.readInput();
            // extracts out the command from the user input
            Command command = checkParsedCommand(userInput);
            if (command != null) {
                executeCommand(command);
                if (command.isExit()) {
                    break;
                }
            }
        }
        logger.fine("exiting EZMealPlan");
    }

    private static Command checkParsedCommand(String userInput) {
        try {
            return Parser.parse(userInput);
        } catch (ParserException | EZMealPlanException ezmealPlanException) {
            ui.printErrorMessage(ezmealPlanException);
            return null;
        }
    }

    private static void checkConstructedLists() {
        // Create and load both main meal list (mainList.txt) and user meal list (userList.txt)
        try {
            Storage.createListFiles();
            Storage.loadExistingInventory(mealManager);
            constructRecipesList();
            constructWishList();
        } catch (IOException ioException) {
            System.err.println("Could not load tasks: " + ioException.getMessage());
        }
    }

    private static void constructWishList() throws IOException {
        File wishListFile = Storage.getWishListFile();
        MealList wishList = mealManager.getWishList();
        constructList(wishListFile, wishList);
    }

    private static void constructRecipesList() throws IOException {
        File recipesListFile = Storage.getRecipesListFile();
        MealList recipesList = mealManager.getRecipesList();
        constructList(recipesListFile, recipesList);
    }

    private static void constructList(File selectedFile, MealList selectedMeals)
            throws IOException {
        // Retrieve saved meals from the respective file and append them into the respective Meals class
        // If the file (mainList.txt) is empty, preset meals are appended into the RecipesList class instead.
        List<Meal> mealList = Storage.loadExistingList(selectedFile);
        // Load pre-set meals if the meal list from the main list file is empty.
        if (mealList.isEmpty() && selectedFile.equals(Storage.getRecipesListFile())) {
            mealList = Storage.loadPresetMeals();
        }
        for (Meal meal : mealList) {
            extractMealIntoList(meal, selectedMeals);
        }
    }

    private static void extractMealIntoList(Meal meal, MealList mealList) {
        //Throw error message if detected an ingredient with invalid price and skips to the next meal.
        try {
            mealManager.addMeal(meal, mealList);
        } catch (EZMealPlanException ezMealPlanException) {
            System.err.println(ezMealPlanException.getMessage());
            System.err.println("The current meal will be skipped.\n");
            logger.info("EZMealPlanException triggered");
        }
    }

    private static void executeCommand(Command command) {
        try {
            // Executes the command parsed out
            command.execute(mealManager, ui);
        } catch (EZMealPlanException ezMealPlanException) {
            ui.printErrorMessage(ezMealPlanException);
            logger.info("EZMealPlanException triggered");
        }
    }

    private static void setupLogger(String fileName) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        createLogFile(fileName);
    }

    private static void createLogFile(String fileName) {
        try {
            FileHandler fileHandler = new FileHandler(fileName, true);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        } catch (IOException ioException) {
            logger.log(Level.WARNING, "File logger is not working.", ioException);
        }
    }
}
