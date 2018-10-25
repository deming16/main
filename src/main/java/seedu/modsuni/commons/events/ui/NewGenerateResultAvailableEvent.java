package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.model.semester.SemesterList;

/**
 * Indicates that a new result is available.
 */
public class NewGenerateResultAvailableEvent extends BaseEvent {

    public final SemesterList semesterList;

    public NewGenerateResultAvailableEvent(SemesterList semesterList) {
        this.semesterList = semesterList;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
