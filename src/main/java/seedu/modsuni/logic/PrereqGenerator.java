package seedu.modsuni.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqDetails;

/**
 * This class is used to facilitate the generation of a {@code Prereq} object.
 */
public class PrereqGenerator {
    private static final String MESSAGE_INVALID_CHARACTERS =
            "Only alphanumeric, parentheses, commas, ampersand and pipe are allowed.";
    private static final String MESSAGE_INVALID_PREFIX = "Prereq string must start with ampersand or pipe.";
    private static final String MESSAGE_WRONG_ORDER_PREFIX = "No consecutive & or |.";
    private static final String MESSAGE_PAREN_NOT_CLOSED = "Make sure all parenthesis are closed properly.";
    private static final String MESSAGE_COMMA_WRONG_POSITION = "Commas should be at the end of module code only.";
    private static final String MESSAGE_CODE_WRONG_POSITION = "Code should only be after '&' or '|' or ',' and must "
            + "end with ','.";
    private Stack<String> stack;
    private Stack<Optional<List<PrereqDetails>>> listStack;
    private Prereq start;
    private String strings = null;
    private StringBuilder builder;

    public PrereqGenerator() {
        stack = new Stack();
        listStack = new Stack();
        start = new Prereq();
        builder = new StringBuilder();
    }

    /**
     * Generate a prereq object based on the given {@code strings}.
     */
    public Prereq generate(String strings) {
        this.strings = strings;
        if (strings.isEmpty()) {
            return start;
        }
        initialisePrereq();
        for (int i = 1; i < strings.length(); i++) {
            if (strings.charAt(i) == '(') {
                createNewList(i + 1);
                i++;
            } else if (strings.charAt(i) == ')') {
                moveToPreviousList();
            } else if (strings.charAt(i) == ',') {
                String module = builder.toString();
                attachModule(module);
                builder.setLength(0);
                continue;
            } else {
                builder.append(strings.charAt(i));
            }
        }
        return start;
    }

    /**
     * Initialise the start of the Prereq object with a initial list.
     */
    private void initialisePrereq() {
        start = new Prereq();
        if (strings.charAt(0) == '|') {
            attachFirstOrList(start);
            listStack.push(start.getOr());
            stack.push("|");
        } else if (strings.charAt(0) == '&') {
            attachFirstAndList(start);
            listStack.push(start.getAnd());
            stack.push("&");
        }
    }

    /**
     * Attach the given {@code module} to the current list.
     */
    private void attachModule(String module) {
        PrereqDetails current = new PrereqDetails();
        current.setCode(Optional.of(new Code(module)));
        listStack.peek().get().add(current);
    }

    /**
     * Constructs and attaches a new list to the current node.
     * The type of list constructed is based on the operator found in {@code operatorIndex}.
     */
    private void createNewList(int operatorIndex) {
        if (strings.charAt(operatorIndex) == '|') {
            createNewOrList();
        } else {
            createNewAndList();
        }
    }

    /**
     * Constructs and attaches a new list of prereqOr to the current node.
     */
    private void createNewOrList () {
        PrereqDetails current = new PrereqDetails();
        attachNewOrList(current);
        listStack.peek().get().add(current);
        listStack.push(current.getOr());
        stack.push("|");
    }

    /**
     * Constructs and attachs a new list of prereqAnd to the current node.
     */
    private void createNewAndList() {
        PrereqDetails current = new PrereqDetails();
        attachNewAndList(current);
        listStack.peek().get().add(current);
        listStack.push(current.getAnd());
        stack.push("&");
    }


    private void attachNewOrList(PrereqDetails current) {
        Optional<List<PrereqDetails>> next = Optional.of(new ArrayList());
        current.setOr(next);
    }
    private void attachFirstOrList(Prereq start) {
        Optional<List<PrereqDetails>> next = Optional.of(new ArrayList());
        start.setOr(next);
    }
    private void attachNewAndList(PrereqDetails current) {
        Optional<List<PrereqDetails>> next = Optional.of(new ArrayList());
        current.setAnd(next);
    }
    private void attachFirstAndList(Prereq start) {
        Optional<List<PrereqDetails>> next = Optional.of(new ArrayList());
        start.setAnd(next);
    }

    /**
     * Traverse back up the prereq graph by one level.
     */
    private void moveToPreviousList() {
        stack.pop();
        listStack.pop();
    }

    /**
     * Check if the given (@code String string} is valid.
     */
    public static void checkValidPrereqString(String string) throws ParseException {

        if (string.isEmpty()) {
            return;
        }
        Pattern pattern = Pattern.compile("[^&|()A-Za-z0-9,]");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            throw new ParseException(MESSAGE_INVALID_CHARACTERS);
        }
        if (string.charAt(0) != '&' && string.charAt(0) != '|') {
            throw new ParseException(MESSAGE_INVALID_PREFIX);
        }
        StringBuilder builder = new StringBuilder();
        int openParenthesis = 0;

        for (int i = 1; i < string.length(); i++) {
            char current = string.charAt(i);
            char prev = string.charAt(i - 1);

            checkConsecutiveAndOr(current, prev);

            // Paren check count.
            if (current == '(') {
                openParenthesis++;
            } else if (current == ')') {
                checkParenPosition(prev, builder);
                openParenthesis--;
            }
            if (openParenthesis < 0) {
                throw new ParseException(MESSAGE_PAREN_NOT_CLOSED);
            }
            checkCommaPosition(current, prev, builder);
            checkCodePosition(current, prev, builder);
        }
        checkClosing(openParenthesis, builder);
    }
    private static void checkConsecutiveAndOr(char current, char prev) throws ParseException {
        if ((current == '&' || current == '|') && prev != '(') {
            throw new ParseException(MESSAGE_WRONG_ORDER_PREFIX);
        }
    }

    /**
     * Check that module code start at the correct place.
     */
    private static void checkCodePosition(char current, char prev, StringBuilder builder) throws ParseException {
        if ((current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z')
                || (current >= '0' && current <= '9')) {
            if (builder.length() == 0 && (prev == '(' || prev == ')')) {
                throw new ParseException(MESSAGE_CODE_WRONG_POSITION);
            }
            builder.append(current);
        }

    }

    /**
     * Check that comma is placed correctly.
     */
    private static void checkCommaPosition(char current, char prev, StringBuilder builder) throws ParseException {
        if (current == ',' && (prev == '&' || prev == '|' || prev == ',' || prev == '(' || prev == ')')) {
            throw new ParseException(MESSAGE_COMMA_WRONG_POSITION);
        } else {
            String code = builder.toString();
            if (!Code.isValidCode(code)) {
                throw new ParseException(Code.MESSAGE_CODE_CONSTRAINTS);
            }
            builder.setLength(0);
        }

    }

    /**
     * Check if there are empty parenthesis or empty list or empty module.
     */
    private static void checkParenPosition(char prev, StringBuilder builder) throws ParseException {
        if (builder.length() != 0) {
            throw new ParseException(MESSAGE_CODE_WRONG_POSITION);
        }
        if (prev == '(' || prev == '&' || prev == '|') {
            throw new ParseException("Empty paren");
        }
    }

    /**
     * Check if there are still open parenthesis or remaining code at the end of the string.
     */
    private static void checkClosing(int openParenthesis, StringBuilder builder) throws ParseException {
        if (builder.length() != 0) {
            throw new ParseException(MESSAGE_CODE_WRONG_POSITION);
        }
        if (openParenthesis > 0) {
            throw new ParseException(MESSAGE_PAREN_NOT_CLOSED);
        }
    }
}
