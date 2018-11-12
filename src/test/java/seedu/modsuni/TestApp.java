package seedu.modsuni;

import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.modsuni.commons.core.Config;
import seedu.modsuni.commons.core.GuiSettings;
import seedu.modsuni.commons.util.FileUtil;
import seedu.modsuni.commons.util.XmlUtil;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.storage.UserPrefsStorage;
import seedu.modsuni.storage.XmlSerializableCredentialStore;
import seedu.modsuni.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_CREDENTIAL_TESTING =
        TestUtil.getFilePathInSandboxFolder("sampleCredentialStore.xml");
    public static final Path SAVE_LOCATION_FOR_MODULELIST_TESTING = TestUtil.getFilePathInSandboxFolder
            ("sampleModuleListData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<CredentialStore> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_CREDENTIAL_TESTING;
    protected Path saveModuleListFileLocation = SAVE_LOCATION_FOR_MODULELIST_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<CredentialStore> initialDataSupplier,
                   Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCredentialStore(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setCredentialStoreFilePath(saveFileLocation);
        userPrefs.setModuleFilePath(saveModuleListFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            model.getCredentialStore());
        ModelHelper.setFilteredList(copy, model.getFilteredDatabaseModuleList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
