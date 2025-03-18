package seedu.exceptions;

public class InvalidMcostException extends EZMealPlanException {
    @Override
    public String getMessage() {
        return "The /mcost input must be parsable into a double and the resulting double value\n" +
                "must be more than 0.";
    }
}
