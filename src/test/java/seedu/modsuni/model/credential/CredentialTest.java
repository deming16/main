package seedu.modsuni.model.credential;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.CredentialBuilder;

public class CredentialTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameCredential() {
        // same object -> returns true
        assertTrue(CREDENTIAL_STUDENT_MAX.isSameCredential(CREDENTIAL_STUDENT_MAX));

        // null -> returns false
        assertFalse(CREDENTIAL_STUDENT_MAX.isSameCredential(null));

        // different username and password -> returns false
        Credential editedMax =
            new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
                .withUsername("editedMax")
                .withPassword("#editedM@x")
                .build();
        assertFalse(CREDENTIAL_STUDENT_MAX.isSameCredential(editedMax));

        // different username -> returns false
        editedMax = new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
            .withUsername("editedMax")
            .build();
        assertFalse(CREDENTIAL_STUDENT_MAX.isSameCredential(editedMax));

        // different password -> returns true
        editedMax =
            new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
                .withPassword("#editedM@x")
                .build();
        assertFalse(CREDENTIAL_STUDENT_MAX.isSameCredential(editedMax));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Credential maxCopy = new CredentialBuilder(CREDENTIAL_STUDENT_MAX).build();
        assertTrue(CREDENTIAL_STUDENT_MAX.equals(maxCopy));

        // same object -> returns true
        assertTrue(CREDENTIAL_STUDENT_MAX.equals(CREDENTIAL_STUDENT_MAX));

        // null -> returns false
        assertFalse(CREDENTIAL_STUDENT_MAX.equals(null));

        // different type -> returns false
        assertFalse(CREDENTIAL_STUDENT_MAX.equals(5));

        // different credential -> returns false
        assertFalse(CREDENTIAL_STUDENT_MAX.equals(CREDENTIAL_STUDENT_SEB));

        // different username -> returns false
        Credential editedMax =
            new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
                .withUsername(VALID_USERNAME)
                .build();
        assertFalse(CREDENTIAL_STUDENT_MAX.equals(editedMax));

        // different password -> returns false
        editedMax = new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
            .withPassword(VALID_PASSWORD)
            .build();
        assertFalse(CREDENTIAL_STUDENT_MAX.equals(editedMax));

    }

    @Test
    public void testToString() {
        // same credential
        Credential maxCopy = new CredentialBuilder(CREDENTIAL_STUDENT_MAX).build();
        assertTrue(CREDENTIAL_STUDENT_MAX.toString().equals(maxCopy.toString()));

        // diff credential
        assertFalse(CREDENTIAL_STUDENT_MAX.toString().equals(CREDENTIAL_STUDENT_SEB.toString()));
    }

    @Test
    public void testHashCode() {
        // same credential
        Credential maxCopy = new CredentialBuilder(CREDENTIAL_STUDENT_MAX).build();
        assertTrue(CREDENTIAL_STUDENT_MAX.hashCode() == (maxCopy.hashCode()));

        // diff credential
        assertFalse(
            CREDENTIAL_STUDENT_MAX.hashCode() == (CREDENTIAL_STUDENT_SEB.hashCode()));
    }
}
