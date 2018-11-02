package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;

import seedu.modsuni.model.user.User;

/**
 * An event indicating a user has logged in.
 */
public class UserTabChangedEvent extends BaseEvent {

    public final User currentUser;

    public UserTabChangedEvent(User user) {
        currentUser = user;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
