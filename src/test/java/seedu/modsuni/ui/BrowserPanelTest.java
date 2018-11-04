package seedu.modsuni.ui;

import static org.junit.Assert.assertEquals;
import static seedu.modsuni.ui.BrowserPanel.LOADING_PAGE;
import static seedu.modsuni.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.modsuni.MainApp;

public class BrowserPanelTest extends GuiUnitTest {

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display_customPage() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(LOADING_PAGE));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());

        // Loading web page
        URL expectedCustomPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + LOADING_PAGE);
        assertEquals(expectedCustomPageUrl, browserPanelHandle.getLoadedUrl());

    }
}
