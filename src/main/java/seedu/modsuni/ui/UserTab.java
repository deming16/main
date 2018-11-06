package seedu.modsuni.ui;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.UserTabChangedEvent;
import seedu.modsuni.commons.events.ui.UserTabLogoutEvent;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.student.Student;

/**
 * The User tab of the Application.
 */
public class UserTab extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UserTab.class);
    private static final String FXML = "UserTab.fxml";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final Clock clock = Clock.systemDefaultZone();

    @FXML
    private Label nameLabel;

    @FXML
    private Text userNameText;

    @FXML
    private Label dateLabel;

    @FXML
    private Text dateText;

    @FXML
    private Label userDetailLabel1;

    @FXML
    private Text userDetailText1;

    @FXML
    private Label userDetailLabel2;

    @FXML
    private Text userDetailText2;

    @FXML
    private Label lastSavedLabel;

    @FXML
    private Text lastSavedText;


    public UserTab() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Resets the respective UI fields in User Tab
     */
    private void resetUserTab() {
        // Resets all Text value
        userNameText.setText("");
        dateText.setText("");
        userDetailText1.setText("");
        userDetailText2.setText("");
        lastSavedText.setText("");

        // Resets Label value
        userDetailLabel1.setText("Major(s):");
        userDetailLabel2.setText("Minor(s):");
    }

    @Subscribe
    public void handleUserTabChangedEvent(UserTabChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        userNameText.setText(event.currentUser.getName().fullName);

        if (event.currentUser.getRole() == Role.STUDENT) {
            userDetailLabel1.setText("Major(s):");
            userDetailLabel2.setText("Minor(s):");
            dateText.setText(((Student) event.currentUser).getEnrollmentDate().toString());
            userDetailText1.setText(((Student) event.currentUser).getMajor().toString());
            userDetailText2.setText(((Student) event.currentUser).getMinor().toString());
        } else {
            userDetailLabel1.setText("Salary:");
            userDetailLabel2.setText("NOTE:");
            dateText.setText(((Admin) event.currentUser).getEmploymentDate().toString());
            userDetailText1.setText(((Admin) event.currentUser).getSalary().toString());
            userDetailText2.setText("This is an admin account"); // hides secondary detail
        }

        lastSavedText.setText(dateFormat.format(new Date(clock.millis())));
    }

    @Subscribe
    public void handleUserTabLogoutEvent(UserTabLogoutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetUserTab();
    }
}
