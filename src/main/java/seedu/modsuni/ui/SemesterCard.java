package seedu.modsuni.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import seedu.modsuni.model.semester.Semester;

/**
 * An UI component that displays information of a {@code Semester}.
 */
public class SemesterCard extends UiPart<StackPane> {

    private static final String FXML = "SemesterListCard.fxml";

    public final Semester semester;

    @FXML
    private StackPane semesterCardPane;

    @FXML
    private ListView<String> semesterListView;

    @FXML
    private Label semesterNo;

    public SemesterCard(Semester semester, String semesterNo) {
        super(FXML);
        this.semester = semester;
        this.semesterNo.setText(semesterNo);
        ObservableList<String> items = semester.getModuleCodeList();
        semesterListView.setItems(items);
        semesterListView.setPrefHeight(100);
        semesterListView.setPrefWidth(200);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SemesterCard)) {
            return false;
        }

        // state check
        SemesterCard card = (SemesterCard) other;
        return semesterListView.getItems().equals(card.semesterListView.getItems());
    }

}
