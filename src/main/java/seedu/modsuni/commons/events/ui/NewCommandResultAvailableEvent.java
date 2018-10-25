package seedu.modsuni.commons.events.ui;

import javafx.scene.layout.Region;
import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.ui.UiPart;

public class NewCommandResultAvailableEvent extends BaseEvent {
    public UiPart<Region> toBeDisplayed;
    public BaseEvent baseEvent;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
