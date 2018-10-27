package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;

public class SaveDisplayRequestEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
