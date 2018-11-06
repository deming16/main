package seedu.modsuni.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.commons.util.XmlUtil;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.testutil.CredentialStoreBuilder;
import seedu.modsuni.testutil.TypicalCredentials;

public class XmlSerializableCredentialStoreTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
        "data", "XmlSerializableCredentialStoreTest");
    private static final Path TYPICAL_CREDENTIALS_FILE =
        TEST_DATA_FOLDER.resolve("typicalCredentialStore.xml");
    private static final Path INVALID_CREDENTIALS_FILE =
        TEST_DATA_FOLDER.resolve(
            "invalidCredentialStore.xml");
    private static final Path DUPLICATE_CREDENTIALS_FILE =
        TEST_DATA_FOLDER.resolve("duplicateCredentialStore.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCredentialsFile_success() throws Exception {
        XmlSerializableCredentialStore dataFromFile =
            XmlUtil.getDataFromFile(TYPICAL_CREDENTIALS_FILE,
                XmlSerializableCredentialStore.class);
        CredentialStore credentialStoreFromFile = dataFromFile.toModelType();
        CredentialStore typicalCredentialStore =
            TypicalCredentials.getTypicalCredentialStore();
        assertEquals(credentialStoreFromFile, typicalCredentialStore);
    }

    @Test
    public void toModelType_invalidCredentialsFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCredentialStore dataFromFile =
            XmlUtil.getDataFromFile(INVALID_CREDENTIALS_FILE,
                XmlSerializableCredentialStore.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateCredentials_throwsIllegalValueException() throws Exception {
        XmlSerializableCredentialStore dataFromFile =
            XmlUtil.getDataFromFile(DUPLICATE_CREDENTIALS_FILE,
                XmlSerializableCredentialStore.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableCredentialStore.MESSAGE_DUPLICATE_CREDENTIAL);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() {

        XmlSerializableCredentialStore credentialStore = new XmlSerializableCredentialStore(
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .withCredentials(CREDENTIAL_STUDENT_SEB)
                .build()
        );
        // same object -> returns true
        assertEquals(credentialStore, credentialStore);

        // same values -> returns true
        XmlSerializableCredentialStore copyCredentialStore =
            new XmlSerializableCredentialStore(
                new CredentialStoreBuilder()
                    .withCredentials(CREDENTIAL_STUDENT_MAX)
                    .withCredentials(CREDENTIAL_STUDENT_SEB)
                    .build()
            );
        assertEquals(credentialStore, copyCredentialStore);

        // different types -> returns false
        assertFalse(credentialStore.equals(5));

        // null -> returns false
        assertFalse(credentialStore.equals(null));

        // different credential -> returns false
        XmlSerializableCredentialStore newCredentialStore =
            new XmlSerializableCredentialStore(
            new CredentialStoreBuilder()
                .withCredentials(CREDENTIAL_STUDENT_MAX)
                .build()
        );
        assertFalse(credentialStore.equals(newCredentialStore));
    }

}
