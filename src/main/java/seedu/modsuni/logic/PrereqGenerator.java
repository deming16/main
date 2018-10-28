package seedu.modsuni.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqAnd;
import seedu.modsuni.model.module.PrereqOr;

/**
 * This class is used to facilitate the generation of a {@code Prereq} object.
 */
public class PrereqGenerator {
    private Stack<String> stack;
    private Stack<Optional<List<PrereqOr>>> orStack;
    private Stack<Optional<List<PrereqAnd>>> andStack;
    private Prereq start;
    private String strings = null;
    private StringBuilder builder;

    public PrereqGenerator() {
        stack = new Stack();
        orStack = new Stack();
        andStack = new Stack();
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
                attachModules(module);
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
            attachNewOrList(start);
            orStack.push(start.getOr());
            stack.push("|");
        } else if (strings.charAt(0) == '&') {
            attachNewAndList(start);
            andStack.push(start.getAnd());
            stack.push("&");
        }
    }

    /**
     * Attach the given {@code module} to the current list.
     */
    private void attachModules(String module) {
        if (stack.peek().equals("|")) {
            orStack.peek().get().add(attachOrModule(module));
        } else {
            andStack.peek().get().add(attachAndModule(module));
        }
    }

    private PrereqOr attachOrModule(String module) {
        PrereqOr current = new PrereqOr();
        current.setCode(Optional.of(new Code(module)));
        return current;
    }

    private PrereqAnd attachAndModule(String module) {
        PrereqAnd current = new PrereqAnd();
        current.setCode(Optional.of(new Code(module)));
        return current;
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
        if (stack.peek().equals("|")) {
            PrereqOr current = new PrereqOr();
            attachNewOrListToOr(current);
            orStack.peek().get().add(current);
            orStack.push(current.getOr());
        } else if (stack.peek().equals("&")) {
            PrereqAnd current = new PrereqAnd();
            attachNewOrListToAnd(current);
            andStack.peek().get().add(current);
            orStack.push(current.getOr());
        }
        stack.push("|");
    }

    /**
     * Constructs and attachs a new list of prereqAnd to the current node.
     */
    private void createNewAndList() {
        if (stack.peek().equals("|")) {
            PrereqOr current = new PrereqOr();
            attachNewAndListToOr(current);
            orStack.peek().get().add(current);
            andStack.push(current.getAnd());
        } else if (stack.peek().equals("&")) {
            PrereqAnd current = new PrereqAnd();
            attachNewAndListToAnd(current);
            andStack.peek().get().add(current);
            andStack.push(current.getAnd());
        }
        stack.push("&");
    }


    private void attachNewOrListToOr(PrereqOr current) {
        Optional<List<PrereqOr>> next = Optional.of(new ArrayList());
        current.setOr(next);
    }

    private void attachNewOrListToAnd(PrereqAnd current) {
        Optional<List<PrereqOr>> next = Optional.of(new ArrayList());
        current.setOr(next);
    }

    private void attachNewOrList(Prereq start) {
        Optional<List<PrereqOr>> next = Optional.of(new ArrayList());
        start.setOr(next);
    }

    private void attachNewAndListToOr(PrereqOr current) {
        Optional<List<PrereqAnd>> next = Optional.of(new ArrayList());
        current.setAnd(next);
    }

    private void attachNewAndListToAnd(PrereqAnd current) {
        Optional<List<PrereqAnd>> next = Optional.of(new ArrayList());
        current.setAnd(next);
    }
    private void attachNewAndList(Prereq start) {
        Optional<List<PrereqAnd>> next = Optional.of(new ArrayList());
        start.setAnd(next);
    }

    /**
     * Traverse back up the prereq graph by one level.
     */
    private void moveToPreviousList() {
        if (stack.pop().equals("|")) {
            orStack.pop();
        } else {
            andStack.pop();
        }
    }

}
