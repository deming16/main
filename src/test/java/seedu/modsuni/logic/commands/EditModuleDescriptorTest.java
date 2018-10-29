package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_ACC1002;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_CS1010;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_CODE_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_CREDIT_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DEPARTMENT_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SEMS_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_TITLE_CS2109;

import org.junit.Test;

import seedu.modsuni.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.modsuni.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditModuleDescriptor descriptorWithSameValues = new EditModuleDescriptor(DESC_CS1010);
        assertTrue(DESC_CS1010.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS1010.equals(DESC_CS1010));

        // null -> returns false
        assertFalse(DESC_CS1010.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS1010.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS1010.equals(DESC_ACC1002));

        // different code -> returns false
        EditModuleDescriptor editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withCode(VALID_CODE_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different title -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withTitle(VALID_TITLE_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different department -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withDepartment(VALID_DEPARTMENT_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different description -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withDescription(VALID_DESCRIPTION_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different credit -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withCredit(Integer.parseInt(VALID_CREDIT_CS2109)).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different sems -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withSems(VALID_SEMS_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

        // different prereq -> returns false
        editedCS1010 = new EditModuleDescriptorBuilder(DESC_CS1010)
                .withCode(VALID_CODE_CS2109).build();
        assertFalse(DESC_CS1010.equals(editedCS1010));

    }
}
