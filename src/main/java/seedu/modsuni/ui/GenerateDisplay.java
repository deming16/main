package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.NewGenerateResultAvailableEvent;
import seedu.modsuni.model.module.Module;
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

    @FXML
    public void testClicked() {
        System.out.println("HELIOEFJOIFQWEJOIWEQJQOIWEJ");
    }

    @Subscribe
    private void handleNewGenerateResultAvailableEvent(NewGenerateResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            for (Semester semester : event.semesterList) {
                SemesterCard semesterCard = new SemesterCard(semester);
                semesters.getChildren().add(semesterCard.getRoot());
            }
        });
    }

}
