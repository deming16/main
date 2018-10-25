package seedu.modsuni.commons.events.ui;

import javafx.scene.layout.Region;
import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.ui.UiPart;

/**
 * Indicates that a new command event is available.
 */
public class NewCommandResultAvailableEvent extends BaseEvent {
    private UiPart<Region> toBeDisplayed;
    private BaseEvent baseEvent;


    public UiPart<Region> getToBeDisplayed() {
        return toBeDisplayed;
    }

    public void setToBeDisplayed(UiPart<Region> toBeDisplayed) {
        this.toBeDisplayed = toBeDisplayed;
    }

    public BaseEvent getBaseEvent() {
        return baseEvent;
    }

    public void setBaseEvent(BaseEvent baseEvent) {
        this.baseEvent = baseEvent;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
