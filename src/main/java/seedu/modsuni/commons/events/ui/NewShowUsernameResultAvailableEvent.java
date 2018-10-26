package seedu.modsuni.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.model.credential.Username;

/**
 * Indicates that a new show username result is available.
 */
public class NewShowUsernameResultAvailableEvent extends BaseEvent {

    public final ObservableList<Username> usernameList;

    public NewShowUsernameResultAvailableEvent(ObservableList<Username> usernameList) {
        this.usernameList = usernameList;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
