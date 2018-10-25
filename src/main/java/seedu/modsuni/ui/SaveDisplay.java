package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.NewSaveResultAvailableEvent;

public class SaveDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(SaveDisplay.class);
    private static final String FXML = "SaveDisplay.fxml";

    @FXML
    private TextArea saveDisplay;

    public SaveDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewSaveResultAvailableEvent(NewSaveResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            saveDisplay.setText(event.currentUser.toDisplayUI());
        });
    }

}
