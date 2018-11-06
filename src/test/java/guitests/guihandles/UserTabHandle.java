package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * A handle to the {@code UserTab} in the GUI.
 */
public class UserTabHandle extends NodeHandle<Node> {


    public static final String TAB_PANE_PLACEHOLDER = "#tabPane";

    public static final String NAME_LABEL_ID = "#nameLabel";
    public static final String NAME_TEXT_ID = "#nameText";
    public static final String DATE_LABEL_ID = "#dateLabel";
    public static final String DATE_TEXT_ID = "#dateText";
    public static final String USER_DETAIL_1_LABEL = "#userDetailLabel1";
    public static final String USER_DETAIL_1_TEXT = "#userDetailText1";
    public static final String USER_DETAIL_2_LABEL = "#userDetailLabel2";
    public static final String USER_DETAIL_2_TEXT = "#userDetailText2";
    public static final String LAST_SAVE_LABEL = "#lastSavedLabel";
    public static final String LAST_SAVE_TEXT = "#lastSavedText";


    private Label nameLabel;
    private Text nameText;
    private Label dateLabel;
    private Text dateText;
    private Label userDetailLabel1;
    private Text userDetailText1;
    private Label userDetailLabel2;
    private Text userDetailText2;
    private Label lastSavedLabel;
    private Text lastSavedText;

    public UserTabHandle(Node rootNode) {
        super(rootNode);

        nameLabel = getChildNode(NAME_LABEL_ID);
        nameText = getChildNode(NAME_TEXT_ID);
        dateLabel = getChildNode(DATE_LABEL_ID);
        dateText = getChildNode(DATE_TEXT_ID);
        userDetailLabel1 = getChildNode(USER_DETAIL_1_LABEL);
        userDetailText1 = getChildNode(USER_DETAIL_1_TEXT);
        userDetailLabel2 = getChildNode(USER_DETAIL_2_LABEL);
        userDetailText2 = getChildNode(USER_DETAIL_2_TEXT);
        lastSavedLabel = getChildNode(LAST_SAVE_LABEL);
        lastSavedText = getChildNode(LAST_SAVE_TEXT);
    }

    /**
     * Returns the text of the name label.
     */
    public String getNameLabel() {
        return nameLabel.getText();
    }

    /**
     * Returns the text of the name text.
     */
    public String getNameText() {
        return nameText.getText();
    }

    /**
     * Returns the text of the date label.
     */
    public String getDateLabel() {
        return dateLabel.getText();
    }

    /**
     * Returns the text of the date text.
     */
    public String getDateText() {
        return dateText.getText();
    }

    /**
     * Returns the text of the user detail 1 label.
     */
    public String getUserDetail1Label() {
        return userDetailLabel1.getText();
    }

    /**
     * Returns the text of the user detail 1 text.
     */
    public String getUserDetail1Text() {
        return userDetailText1.getText();
    }

    /**
     * Returns the text of the user detail 2 label.
     */
    public String getUserDetail2Label() {
        return userDetailLabel2.getText();
    }

    /**
     * Returns the text of the user detail 2 text.
     */
    public String getUserDetail2Text() {
        return userDetailText2.getText();
    }

    /**
     * Returns the text of the last saved label.
     */
    public String getLastSaveLabel() {
        return lastSavedLabel.getText();
    }

    /**
     * Returns the text of the last saved text.
     */
    public String getLastSaveText() {
        return lastSavedText.getText();
    }


}
