package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;

/**
 * An event indicating a user has logged out.
 */
public class UserTabLogoutEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
