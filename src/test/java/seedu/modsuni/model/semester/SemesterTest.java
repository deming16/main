package seedu.modsuni.model.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;
import seedu.modsuni.testutil.Assert;
import seedu.modsuni.testutil.ModuleBuilder;

public class SemesterTest {

    private static final Module VALID_MODULE = new ModuleBuilder().build();

    @Test
    public void addModule_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Semester().addModule(null));
    }

    @Test
    public void getModuleList() {
        Semester semester = new Semester();
        semester.addModule(VALID_MODULE);
        UniqueModuleList uniqueModuleList = new UniqueModuleList();
        uniqueModuleList.add(VALID_MODULE);
        assertEquals(semester.getModuleList(), uniqueModuleList.asUnmodifiableObservableList());
    }

    @Test
    public void getModuleCodeList() {
        Semester semester = new Semester();
        semester.addModule(VALID_MODULE);
        ObservableList<String> moduleObservableList = FXCollections.observableArrayList();
        moduleObservableList.add(VALID_MODULE.getCode().code);
        assertEquals(semester.getModuleCodeList(), moduleObservableList);
    }

    @Test
    public void getTotalCredits() {
        Semester semester = new Semester();
        semester.addModule(VALID_MODULE);
        assertEquals(semester.getTotalCredits(), VALID_MODULE.getCredit());
    }

    @Test
    public void equals_invalid_newSemester() {
        Semester semesterBlank = new Semester();
        Semester semesterWithModule = new Semester();
        semesterWithModule.addModule(VALID_MODULE);
        assertNotEquals(semesterBlank, semesterWithModule);
    }

    @Test
    public void equals_invalid_null() {
        Semester semester = new Semester();
        assertNotEquals(semester, null);
    }

    @Test
    public void equals_valid() {
        Semester semester = new Semester();
        semester.addModule(VALID_MODULE);

        Semester semesterWithModule = new Semester();
        semesterWithModule.addModule(VALID_MODULE);
        assertEquals(semester, semesterWithModule);
    }


}
