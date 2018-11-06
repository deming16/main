package seedu.modsuni.logic.commands;

import static junit.framework.TestCase.assertTrue;

import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SwitchTabCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullCredentialThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SwitchTabCommand(null);
    }

    @Test
    public void equals() {

        String firstTab = "user";
        String secondTab = "a";

        SwitchTabCommand firstSwitchTab = new SwitchTabCommand(firstTab);
        SwitchTabCommand secondSwitchTab = new SwitchTabCommand(secondTab);

        // same object -> returns true
        assertTrue(firstSwitchTab.equals(firstSwitchTab));

        // same values -> returns true
        SwitchTabCommand firstSwitchTabCopy = new SwitchTabCommand(firstTab);
        assertTrue(firstSwitchTab.equals(firstSwitchTabCopy));

        // different types -> returns false
        assertFalse(firstSwitchTab.equals(1));

        // null -> returns false
        assertFalse(firstSwitchTab.equals(null));

        // different person -> returns false
        assertFalse(firstSwitchTab.equals(secondSwitchTab));
    }
}
