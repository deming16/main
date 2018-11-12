package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final StagedModuleListPanelHandle stagedModuleListPanel;
    private final TakenModuleListPanelHandle takenModuleListPanel;
    private final DatabaseModuleListPanelHandle databaseModuleListPanel;
    private final OutputDisplayHandle outputDisplay;
    private final CommandBoxHandle commandBox;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        stagedModuleListPanel = new StagedModuleListPanelHandle(
                getChildNode(StagedModuleListPanelHandle.MODULE_LIST_VIEW_ID));
        takenModuleListPanel = new TakenModuleListPanelHandle(
                getChildNode(TakenModuleListPanelHandle.MODULE_LIST_VIEW_ID));
        databaseModuleListPanel = new DatabaseModuleListPanelHandle(
                getChildNode(DatabaseModuleListPanelHandle.MODULE_LIST_VIEW_ID));
        outputDisplay =
            new OutputDisplayHandle(getChildNode(OutputDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public StagedModuleListPanelHandle getStagedModuleListPanel() {
        return stagedModuleListPanel;
    }

    public TakenModuleListPanelHandle getTakenModuleListPanel() {
        return takenModuleListPanel;
    }

    public DatabaseModuleListPanelHandle getDatabaseModuleListPanel() {
        return databaseModuleListPanel;
    }

    public OutputDisplayHandle getResultDisplay() {
        return outputDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
