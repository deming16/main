package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.NewGenerateResultAvailableEvent;
import seedu.modsuni.model.semester.Semester;

/**
 * A ui for the output display that is displayed in the main display box.
 */
public class GenerateDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(GenerateDisplay.class);
    private static final String FXML = "GenerateDisplay.fxml";

    @FXML
    private FlowPane semesters;

    public GenerateDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewGenerateResultAvailableEvent(NewGenerateResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            semesters.getChildren().clear();
            int year = 1;
            int semesterNo = 1;
            for (Semester semester : event.semesterList) {
                SemesterCard semesterCard = new SemesterCard(semester,
                        "Year " + year + " Semester " + semesterNo++);
                semesters.getChildren().add(semesterCard.getRoot());
                if (semesterNo % 2 == 1) {
                    semesterNo = 1;
                    year++;
                } else {
                    semesterNo = 2;
                }
            }
        });
    }

}
