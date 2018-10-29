package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_ALICE;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_BRAD;

import org.junit.Test;

import seedu.modsuni.logic.commands.EditAdminCommand.EditAdminDescriptor;
import seedu.modsuni.testutil.EditAdminDescriptorBuilder;

public class EditAdminDescriptorTest {

    @Test
    public void equals() {
        EditAdminDescriptor descriptorWithSameValue =
                new EditAdminDescriptor(DESC_ALICE);

        // same values -> returns true
        assertTrue(DESC_ALICE.equals(descriptorWithSameValue));

        // same object -> returns true
        assertTrue(DESC_ALICE.equals(DESC_ALICE));

        // null -> returns false
        assertFalse(DESC_ALICE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALICE.equals(1));

        // different values -> returns false
        assertFalse(DESC_ALICE.equals(DESC_BRAD));

        // different name -> returns false
        EditAdminDescriptor differentNameDescriptor =
                new EditAdminDescriptorBuilder()
                        .withName("ALICE")
                        .build();
        assertFalse(DESC_ALICE.equals(differentNameDescriptor));

        // different Employment Date -> returns false
        EditAdminDescriptor differentEnrollmentDateDescriptor =
                new EditAdminDescriptorBuilder(DESC_ALICE)
                        .withEmploymentDate("11/11/1111")
                        .build();
        assertFalse(DESC_ALICE.equals(differentEnrollmentDateDescriptor));

    }
}
