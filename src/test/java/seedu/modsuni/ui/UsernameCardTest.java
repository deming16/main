package seedu.modsuni.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertCardDisplaysUsername;

import guitests.guihandles.UsernameCardHandle;
import org.junit.Assert;
import org.junit.Test;

import seedu.modsuni.model.credential.Username;
import seedu.modsuni.testutil.CredentialBuilder;

public class UsernameCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Username username = new CredentialBuilder().build().getUsername();
        UsernameCard usernameCard = new UsernameCard(username, 1);
        uiPartRule.setUiPart(usernameCard);
        assertCardDisplay(usernameCard, username, 1);
    }

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

    /**
     * Asserts that {@code usernameCard} displays the details of {@code expectedUsername} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(UsernameCard usernameCard, Username expectedusername, int expectedId) {
        guiRobot.pauseForHuman();

        UsernameCardHandle usernameCardHandle = new UsernameCardHandle(usernameCard.getRoot());

        // verify id is displayed correctly
        Assert.assertEquals(Integer.toString(expectedId) + ". ", usernameCardHandle.getId());

        // verify module details are displayed correctly
        assertCardDisplaysUsername(expectedusername, usernameCardHandle);
    }
}
