package seedu.modsuni.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.modsuni.model.credential.Username;

/**
 * An UI component that displays information of a username.
 */
public class UsernameCard extends UiPart<Region> {

    private static final String FXML = "UsernameCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    @FXML
    private HBox cardPane;
    @FXML
    private Label username;
    @FXML
    private Label id;
    public UsernameCard(Username username, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        this.username.setText(username.getUsername());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UsernameCard)) {
            return false;
        }

        // state check
        UsernameCard card = (UsernameCard) other;
        return id.getText().equals(card.id.getText())
                && username.getText().equals(card.username.getText());
    }
}
