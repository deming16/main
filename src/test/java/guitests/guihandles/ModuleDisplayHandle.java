package guitests.guihandles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;


/**
 * Provides a handle to a module card in the module list panel.
 */
public class ModuleDisplayHandle extends NodeHandle<Node> {
    private static final String CODE_LABEL_FIELD_ID = "#codeLabel";
    private static final String CODE_LABEL_TEXT = "Code: ";
    private static final String CODE_TEXT_FIELD_ID = "#codeText";
    private static final String DEPARTMENT_LABEL_FIELD_ID = "#departmentLabel";
    private static final String DEPARTMENT_LABEL_TEXT = "Department: ";
    private static final String DEPARTMENT_TEXT_FIELD_ID = "#departmentText";
    private static final String TITLE_LABEL_FIELD_ID = "#titleLabel";
    private static final String TITLE_LABEL_TEXT = "Title: ";
    private static final String TITLE_TEXT_FIELD_ID = "#titleText";
    private static final String DESCRIPTION_LABEL_FIELD_ID = "#descriptionLabel";
    private static final String DESCRIPTION_LABEL_TEXT = "Description: ";
    private static final String DESCRIPTION_TEXT_FIELD_ID = "#descriptionText";
    private static final String CREDIT_LABEL_FIELD_ID = "#creditLabel";
    private static final String CREDIT_LABEL_TEXT = "Credit: ";
    private static final String CREDIT_TEXT_FIELD_ID = "#creditText";
    private static final String AVAILABILITY_LABEL_FIELD_ID = "#availabilityLabel";
    private static final String AVAILABILITY_LABEL_TEXT = "Available in: ";
    private static final String AVAILABILITY_TEXT_FIELD_ID = "#availabilityText";
    private static final String LOCKED_MODULES_LABEL_FIELD_ID = "#lockedModulesLabel";
    private static final String LOCKED_MODULES_LABEL_TEXT = "Locked Modules: ";
    private static final String LOCKED_MODULES_TEXT_FIELD_ID = "#lockedModulesText";
    private static final String SEM1 = "Sem1";
    private static final String SEM2 = "Sem2";
    private static final String SPECIAL_TERM1 = "SpecialTerm1";
    private static final String SPECIAL_TERM2 = "SpecialTerm2";

    private final Label codeLabel;
    private final Text codeText;
    private final Label departmentLabel;
    private final Text departmentText;
    private final Label titleLabel;
    private final Text titleText;
    private final Label descriptionLabel;
    private final Text descriptionText;
    private final Label creditLabel;
    private final Text creditText;
    private final Label availabilityLabel;
    private final Text availabilityText;
    private final Label lockedModulesLabel;
    private final Text lockedModulesText;

    public ModuleDisplayHandle(Node cardNode) {
        super(cardNode);

        codeLabel = getChildNode(CODE_LABEL_FIELD_ID);
        codeText = getChildNode(CODE_TEXT_FIELD_ID);
        departmentLabel = getChildNode(DEPARTMENT_LABEL_FIELD_ID);
        departmentText = getChildNode(DEPARTMENT_TEXT_FIELD_ID);
        titleLabel = getChildNode(TITLE_LABEL_FIELD_ID);
        titleText = getChildNode(TITLE_TEXT_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_LABEL_FIELD_ID);
        descriptionText = getChildNode(DESCRIPTION_TEXT_FIELD_ID);
        creditLabel = getChildNode(CREDIT_LABEL_FIELD_ID);
        creditText = getChildNode(CREDIT_TEXT_FIELD_ID);
        availabilityLabel = getChildNode(AVAILABILITY_LABEL_FIELD_ID);
        availabilityText = getChildNode(AVAILABILITY_TEXT_FIELD_ID);
        lockedModulesLabel = getChildNode(LOCKED_MODULES_LABEL_FIELD_ID);
        lockedModulesText = getChildNode(LOCKED_MODULES_TEXT_FIELD_ID);
    }

    public String getCodeLabel() {
        return codeLabel.getText();
    }

    public String getCodeText() {
        return codeText.getText();
    }

    public String getDepartmentLabel() {
        return departmentLabel.getText();
    }

    public String getDepartmentText() {
        return departmentText.getText();
    }

    public String getTitleLabel() {
        return titleLabel.getText();
    }

    public String getTitleText() {
        return titleText.getText();
    }

    public String getDescriptionLabel() {
        return descriptionLabel.getText();
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public String getCreditLabel() {
        return creditLabel.getText();
    }

    public String getCreditText() {
        return creditText.getText();
    }

    public String getAvailabilityLabel() {
        return availabilityLabel.getText();
    }

    public String getAvailabilityText() {
        return availabilityText.getText();
    }

    public String getLockedModulesLabel() {
        return lockedModulesLabel.getText();
    }

    public String getLockedModulesText() {
        return lockedModulesText.getText();
    }

    /**
     * Returns true if this handle's format is correct.
     */
    public boolean checkFormat() {
        return getCodeLabel().equals(CODE_LABEL_TEXT)
                && getDepartmentLabel().equals(DEPARTMENT_LABEL_TEXT)
                && getTitleLabel().equals(TITLE_LABEL_TEXT)
                && getDescriptionLabel().equals(DESCRIPTION_LABEL_TEXT)
                && getCreditLabel().equals(CREDIT_LABEL_TEXT)
                && getAvailabilityLabel().equals(AVAILABILITY_LABEL_TEXT)
                && getLockedModulesLabel().equals(LOCKED_MODULES_LABEL_TEXT);
    }


    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return getCodeText().equals(module.getCode().code)
                && getDepartmentText().equals(module.getDepartment())
                && getTitleText().equals(module.getTitle())
                && getDescriptionText().equals(module.getDescription())
                && getCreditText().equals(String.valueOf(module.getCredit()))
                && module.isAvailableInSem1() == getAvailabilityText().contains(SEM1)
                && module.isAvailableInSem2() == getAvailabilityText().contains(SEM2)
                && module.isAvailableInSpecialTerm1() == getAvailabilityText().contains(SPECIAL_TERM1)
                && module.isAvailableInSpecialTerm2() == getAvailabilityText().contains(SPECIAL_TERM2)
                && isLockedModulesSame(module);
    }

    /**
     * Returns true if locked module text is equal to the locked module list in {@code module}.
     * Returns false otherwise.
     */
    public boolean isLockedModulesSame(Module module) {
        if (getLockedModulesText().equals("") && module.getLockedModules().size() == 0) {
            return true;
        }

        List<String> displayText = Arrays.asList(getLockedModulesText().trim().split(" "));
        List<Code> lockedCode = module.getLockedModules();
        if (displayText.size() != lockedCode.size()) {
            return false;
        }

        List<String> lockedString = lockedCode.stream().map(code -> code.toString()).collect(Collectors.toList());
        Collections.sort(displayText);
        Collections.sort(lockedString);
        return displayText.equals(lockedString);
    }
}
