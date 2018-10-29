package seedu.modsuni.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;

/**
 * A utility class containing a list of {@code Credential} objects to be used
 * in tests.
 */
public class TypicalCredentials {

    public static final Credential CREDENTIAL_STUDENT_MAX =
        new CredentialBuilder()
            .withUsername("max33")
            .withPassword("7ecccb4114345326ec385a43f6a56ed525cdd2e4eeb9d06fbd9f9d7a8d718495")
            .build();


    public static final Credential CREDENTIAL_STUDENT_SEB =
        new CredentialBuilder()
            .withUsername("vettel5")
            .withPassword("6284cd40cd25dc42e03dca6bd45c2cfe5463d82920ac231f29034f4424a3349c")
            .build();

    public static final Credential CREDENTIAL_ADMIN =
            new CredentialBuilder()
                    .withUsername("Admin123")
                    .withPassword("6284cd40cd25dc42e03dca6bd45c2cfe5463d82920ac231f29034f4424a3349c")
                    .build();

    private TypicalCredentials() {
    }

    /**
     * Returns a {@code CredentialStore} with all the typical credentials.
     */
    public static CredentialStore getTypicalCredentialStore() {
        CredentialStore cs = new CredentialStore();
        for (Credential credential : getTypicalCredentials()) {
            cs.addCredential(credential);
        }
        return cs;
    }

    private static List<Credential> getTypicalCredentials() {
        return new ArrayList<>(Arrays.asList(CREDENTIAL_STUDENT_MAX, CREDENTIAL_STUDENT_SEB, CREDENTIAL_ADMIN));
    }
}
