package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.modsuni.model.credential.Username;

/**
 * Provides a handle to a username card in the username display.
 */
public class UsernameCardHandle extends NodeHandle<Node> {
    public static final String ID_FIELD_ID = "#id";
    public static final String USERNAME_FIELD_ID = "#username";

    private final Label idLabel;
    private final Label usernameLabel;

    public UsernameCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        usernameLabel = getChildNode(USERNAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getUsername() {
        return usernameLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code username}.
     */
    public boolean equals(Username username) {
        return getUsername().equals(username.getUsername());
    }
}
