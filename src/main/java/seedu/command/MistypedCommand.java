package seedu.command;

import seedu.logic.MealManager;
import seedu.ui.UserInterface;

public class MistypedCommand extends Command {
    private final String actualCommand;

    public MistypedCommand(String userInputText, String actualCommand) {
        validUserInput = userInputText;
        this.actualCommand = actualCommand;
    }
    @Override
    public void execute(MealManager mealManager, UserInterface ui) {
        ui.printMistypedCommand(validUserInput, actualCommand);
    }
}
