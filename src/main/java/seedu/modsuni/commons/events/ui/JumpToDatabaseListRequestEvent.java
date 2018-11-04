package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToDatabaseListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToDatabaseListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
