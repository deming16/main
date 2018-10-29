package seedu.modsuni.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.modsuni.model.credential.Username;
import seedu.modsuni.testutil.CredentialBuilder;

public class UsernameCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        Username username = new CredentialBuilder().build().getUsername();
        UsernameCard usernameCard = new UsernameCard(username, 0);

        // same username, same index -> returns true
        UsernameCard copy = new UsernameCard(username, 0);
        assertTrue(usernameCard.equals(copy));

        // same object -> returns true
        assertTrue(usernameCard.equals(usernameCard));

        // null -> returns false
        assertFalse(usernameCard.equals(null));

        // different types -> returns false
        assertFalse(usernameCard.equals(0));

        // different username, same index -> returns false
        Username differentUsername = new CredentialBuilder().withUsername("diffferent").build().getUsername();
        assertFalse(usernameCard.equals(new UsernameCard(differentUsername, 0)));

        // same username, different index -> returns false
        assertFalse(usernameCard.equals(new UsernameCard(username, 1)));
    }
}
