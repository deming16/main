package seedu.modsuni.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;

public class SaveCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager();

    @Test
    public void constructorNullCredentialThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SaveCommand(null);
    }

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(SaveCommand.MESSAGE_ERROR);
        SaveCommand sc = new SaveCommand(Paths.get("path1.xml"));
        sc.execute(model, commandHistory);
    }

    @Test
    public void equals() {

        Path firstPath = Paths.get("/path1.xml");
        Path secondPath = Paths.get("/path2.xml");

        SaveCommand firstSave = new SaveCommand(firstPath);
        SaveCommand secondSave = new SaveCommand(secondPath);

        // same object -> returns true
        assertTrue(firstSave.equals(firstSave));

        // same values -> returns true
        SaveCommand firstSaveCopy = new SaveCommand(firstPath);
        assertTrue(firstSave.equals(firstSaveCopy));

        // different types -> returns false
        assertFalse(firstSave.equals(1));

        // null -> returns false
        assertFalse(firstSave.equals(null));

        // different person -> returns false
        assertFalse(firstSave.equals(secondSave));
    }
}
