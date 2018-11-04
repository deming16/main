package seedu.modsuni.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;


/**
 * The User tab of the Application.
 */
public class ModuleDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ModuleDisplay.class);
    private static final String FXML = "ModuleDisplay.fxml";
    private static final String SEM1 = "Sem1";
    private static final String SEM2 = "Sem2";
    private static final String SPECIAL_TERM1 = "SpecialTerm1";
    private static final String SPECIAL_TERM2 = "SpecialTerm2";

    private Module module;

    @FXML
    private Label codeLabel;

    @FXML
    private Text codeText;

    @FXML
    private Label departmentLabel;

    @FXML
    private Text departmentText;

    @FXML
    private Label titleLabel;

    @FXML
    private Text titleText;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Text descriptionText;

    @FXML
    private Label creditLabel;

    @FXML
    private Text creditText;

    @FXML
    private Label availabilityLabel;

    @FXML
    private Text availabilityText;

    @FXML
    private Label lockedModulesLabel;

    @FXML
    private Text lockedModulesText;

    public ModuleDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Displays the information of a Module
     */
    public void display(Module module) {
        codeText.setText(module.getCode().toString());
        departmentText.setText(module.getDepartment());
        titleText.setText(module.getTitle());
        descriptionText.setText(module.getDescription());
        creditText.setText(String.valueOf(module.getCredit()));
        availabilityText.setText("");
        if (module.isAvailableInSem1()) {
            availabilityText.setText(SEM1);
        }
        if (module.isAvailableInSem2()) {
            availabilityText.setText(availabilityText.getText().concat(" ").concat(SEM2));
        }
        if (module.isAvailableInSpecialTerm1()) {
            availabilityText.setText(availabilityText.getText().concat(" ").concat(SPECIAL_TERM1));
        }
        if (module.isAvailableInSpecialTerm2()) {
            availabilityText.setText(availabilityText.getText().concat(" ").concat(SPECIAL_TERM2));
        }
        lockedModulesText.setText("");
        for (Code code : module.getLockedModules()) {
            lockedModulesText.setText(lockedModulesText.getText().concat(code.toString()).concat(" "));
        }
    }
}

