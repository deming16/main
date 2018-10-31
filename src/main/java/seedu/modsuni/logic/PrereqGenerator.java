package seedu.modsuni.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqDetails;

/**
 * This class is used to facilitate the generation of a {@code Prereq} object.
 */
public class PrereqGenerator {
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

}
