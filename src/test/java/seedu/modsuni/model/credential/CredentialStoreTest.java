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
import seedu.modsuni.testutil.CredentialStoreBuilder;

public class CredentialStoreTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasCredential() {

        CredentialStore credentialStore =
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .withCredentials(CREDENTIAL_STUDENT_SEB)
                .build();

        // credential exist in CredentialStore
        assertTrue(credentialStore.hasCredential(CREDENTIAL_STUDENT_MAX));

        // credential does not exist in CredentialStore
        Credential newCredential = new CredentialBuilder()
            .withUsername(VALID_USERNAME)
            .withPassword(VALID_PASSWORD)
            .build();

        assertFalse(credentialStore.hasCredential(newCredential));
    }

    @Test
    public void isVerifiedCredential() {

        CredentialStore credentialStore =
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .withCredentials(CREDENTIAL_STUDENT_SEB)
                .build();

        // verifiable credential in CredentialStore
        assertTrue(credentialStore.isVerifiedCredential(CREDENTIAL_STUDENT_MAX));

        // wrong password credential  in CredentialStore
        Credential newCredential = new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
            .withUsername("wrongUsername")
            .build();
        assertFalse(credentialStore.isVerifiedCredential(newCredential));

        // wrong password credential  in CredentialStore
        newCredential = new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
            .withPassword("WRONG_PASSWORD")
            .build();
        assertFalse(credentialStore.isVerifiedCredential(newCredential));
    }

    @Test
    public void getUsernames() {
        CredentialStore credentialStore =
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .withCredentials(CREDENTIAL_STUDENT_SEB)
                .build();


        // same Usernames
        CredentialStore credentialStoreCopy =
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .withCredentials(CREDENTIAL_STUDENT_SEB)
                .build();
        assertTrue(credentialStore.getUsernames()
            .equals(credentialStoreCopy.getUsernames()));

        // diff Usernames
        CredentialStore diffCredentialStore =
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .build();
        assertFalse(credentialStore.getUsernames()
            .equals(diffCredentialStore.getUsernames()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CredentialStore credentialStore = new CredentialStoreBuilder()
            .withCredentials(CREDENTIAL_STUDENT_MAX)
            .withCredentials(CREDENTIAL_STUDENT_SEB)
            .build();

        CredentialStore credentialStoreCopy = new CredentialStoreBuilder(credentialStore)
            .build();
        assertTrue(credentialStore.equals(credentialStoreCopy));

        // same object -> returns true
        assertTrue(credentialStore.equals(credentialStore));

        // null -> returns false
        assertFalse(credentialStore.equals(null));

        // different type -> returns false
        assertFalse(credentialStore.equals(5));

        // different credential store -> returns false
        CredentialStore diffCredentialStore = new CredentialStoreBuilder()
            .withCredentials(CREDENTIAL_STUDENT_MAX)
            .build();
        assertFalse(credentialStore.equals(diffCredentialStore));

    }
}
