package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
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
    private TextArea generateDisplay;

    @FXML
    private ListView semesterListView;

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
            semesterListView = new ListView();
            int count = 1;
            for (Semester semester : event.semesterList) {
                semesterListView.getItems().add("Semester: " + count);
                for (Module module : semester.getModuleList()) {
                    System.out.println("added");
                    semesterListView.getItems().add(module.getCode());
                }
                count++;
            }
        });
    }

}
