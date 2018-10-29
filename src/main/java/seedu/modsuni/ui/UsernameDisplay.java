package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.NewShowUsernameResultAvailableEvent;
import seedu.modsuni.model.credential.Username;

/**
 * Panel containing the list of modules.
 */
public class UsernameDisplay extends UiPart<Region> {
    private static final String FXML = "UsernameDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(UsernameDisplay.class);

    @FXML
    private ListView<Username> usernameListView;

    public UsernameDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewShowUsernameResultAvailableEvent(NewShowUsernameResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            usernameListView.setItems(event.usernameList);
            usernameListView.setCellFactory(listView -> new UsernameListViewCell());
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class UsernameListViewCell extends ListCell<Username> {
        @Override
        protected void updateItem(Username username, boolean empty) {
            super.updateItem(username, empty);

            if (empty || username == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new UsernameCard(username, getIndex() + 1).getRoot());
            }
        }
    }
}
