package seedu.modsuni.model.credential;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a credential store
 *
 */
public interface ReadOnlyCredentialStore {
    /**
     * Returns an list of credentials.
     * This list will not contain any duplicate credentials.
     */
    List<Credential> getCredentials();

    /**
     * Returns a list of username.
     * This list will not contain any duplicate usernames.
     */
    ObservableList<Username> getUsernames();

    Password getCredentialPassword(Username username);
}
