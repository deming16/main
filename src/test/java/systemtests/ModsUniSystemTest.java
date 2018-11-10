package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.modsuni.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.modsuni.ui.BrowserPanel.LOADING_PAGE;
import static seedu.modsuni.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertDatabaseListMatching;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertStagedListMatching;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertTakenListMatching;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DatabaseModuleListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.OutputDisplayHandle;
import guitests.guihandles.StagedModuleListPanelHandle;
import guitests.guihandles.TakenModuleListPanelHandle;
import seedu.modsuni.MainApp;
import seedu.modsuni.TestApp;
import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.testutil.TypicalCredentials;
import seedu.modsuni.ui.CommandBox;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class ModsUniSystemTest {

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp =
            setupHelper.setupApplication(this::getInitialData,
                getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected CredentialStore getInitialData() {
        return TypicalCredentials.getTypicalCredentialStore();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_CREDENTIAL_TESTING;

    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public StagedModuleListPanelHandle getStagedModuleListPanel() {
        return mainWindowHandle.getStagedModuleListPanel();
    }

    public TakenModuleListPanelHandle getTakenModuleListPanel() {
        return mainWindowHandle.getTakenModuleListPanel();
    }

    public DatabaseModuleListPanelHandle getDatabaseModuleListPanel() {
        return mainWindowHandle.getDatabaseModuleListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public OutputDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code OutputDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertStagedListMatching(getStagedModuleListPanel(), expectedModel.getFilteredStagedModuleList());
        assertTakenListMatching(getTakenModuleListPanel(), expectedModel.getFilteredTakenModuleList());
        assertDatabaseListMatching(getDatabaseModuleListPanel(), expectedModel.getFilteredDatabaseModuleList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code ModuleListPanelHandle}  to remember
     * their current state.
     */
    private void rememberStates() {
        getBrowserPanel().rememberUrl();
        getStagedModuleListPanel().rememberSelectedModuleCard();
        getTakenModuleListPanel().rememberSelectedModuleCard();
        getDatabaseModuleListPanel().rememberSelectedModuleCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected person.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
    }

    /**
     * Asserts that the browser's url and the selected card in the person list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see StagedModuleListPanelHandle#isSelectedModuleCardChanged()
     * @see TakenModuleListPanelHandle#isSelectedModuleCardChanged()
     * @see DatabaseModuleListPanelHandle#isSelectedModuleCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getStagedModuleListPanel().isSelectedModuleCardChanged());
        assertFalse(getTakenModuleListPanel().isSelectedModuleCardChanged());
        assertFalse(getDatabaseModuleListPanel().isSelectedModuleCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
        assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + LOADING_PAGE), getBrowserPanel().getLoadedUrl());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
