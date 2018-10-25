package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.model.user.User;

/**
 * Indicates that a new result is available.
 */
public class NewSaveResultAvailableEvent  extends BaseEvent {

    public final User currentUser;

    public NewSaveResultAvailableEvent(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return getClass().toString();
    }
}
