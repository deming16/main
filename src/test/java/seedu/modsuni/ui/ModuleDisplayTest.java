package seedu.modsuni.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.ModuleDisplayHandle;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.testutil.ModuleBuilder;

public class ModuleDisplayTest extends GuiUnitTest {

    @Test
    public void buildWithoutModule() {
        ModuleDisplay moduleDisplay = new ModuleDisplay();
        assertNull(moduleDisplay.getModule());
    }

    @Test
    public void display() {
        // module with all availability
        Module moduleWithAllAvailability = new ModuleBuilder().availableInAllSemesters().build();
        ModuleDisplay moduleWithAllAvailabilityDisplay = new ModuleDisplay(moduleWithAllAvailability);
        uiPartRule.setUiPart(moduleWithAllAvailabilityDisplay);
        assertModuleDisplay(moduleWithAllAvailabilityDisplay, moduleWithAllAvailability);

        // module without locked modules
        Module moduleWithoutLockedModules = new ModuleBuilder().build();
        ModuleDisplay moduleWithoutLockedModulesDisplay = new ModuleDisplay(moduleWithoutLockedModules);
        uiPartRule.setUiPart(moduleWithoutLockedModulesDisplay);
        assertModuleDisplay(moduleWithoutLockedModulesDisplay, moduleWithoutLockedModules);

        // module with locked modules
        Module moduleWithLockedModules = new ModuleBuilder().withLockedModules
                (new ArrayList<>(Arrays.asList(new Code("CS2103T"), new Code("CS2101")))).build();
        ModuleDisplay moduleWithLockedModulesDisplay = new ModuleDisplay(moduleWithLockedModules);
        uiPartRule.setUiPart(moduleWithLockedModulesDisplay);
        assertModuleDisplay(moduleWithLockedModulesDisplay, moduleWithLockedModules);
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        ModuleDisplay moduleDisplay = new ModuleDisplay(module);

        // same module -> returns true
        ModuleDisplay copy = new ModuleDisplay(module);
        assertTrue(moduleDisplay.equals(copy));

        // same object -> returns true
        assertTrue(moduleDisplay.equals(moduleDisplay));

        // null -> returns false
        assertFalse(moduleDisplay.equals(null));

        // different types -> returns false
        assertFalse(moduleDisplay.equals(0));

        // different module -> returns false
        Module differentModule = new ModuleBuilder().withCode(new Code("CS2103T")).build();
        assertFalse(moduleDisplay.equals(new ModuleDisplay(differentModule)));
    }

    /**
     * Asserts that {@code moduleCard} displays the details of {@code expectedModule} correctly
     */
    private void assertModuleDisplay(ModuleDisplay moduleDisplay, Module expectedModule) {
        guiRobot.pauseForHuman();

        ModuleDisplayHandle moduleDisplayHandle = new ModuleDisplayHandle(moduleDisplay.getRoot());

        // Check format is correct
        assertTrue(moduleDisplayHandle.checkFormat());

        // verify module details are displayed correctly
        assertTrue(moduleDisplayHandle.equals(expectedModule));

        // verify they are same module
        assertEquals(expectedModule, moduleDisplay.getModule());
    }
}
