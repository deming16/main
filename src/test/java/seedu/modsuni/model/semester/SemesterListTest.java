package seedu.modsuni.model.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import seedu.modsuni.model.module.Module;
import seedu.modsuni.testutil.Assert;
import seedu.modsuni.testutil.TypicalModules;

public class SemesterListTest {

    private static final Module ACC1002 = TypicalModules.ACC1002;
    private static final Module CS1010 = TypicalModules.CS1010;

    @Test
    public void addSemester_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SemesterList().addSemester(null));
    }

    @Test
    public void reverseOrder() {
        Semester semester1 = new Semester();
        semester1.addModule(ACC1002);
        Semester semester2 = new Semester();
        semester2.addModule(CS1010);

        SemesterList semesterList = new SemesterList();
        semesterList.addSemester(semester2);
        semesterList.addSemester(semester1);

        SemesterList semesterListReverseOrder = new SemesterList();
        semesterListReverseOrder.addSemester(semester1);
        semesterListReverseOrder.addSemester(semester2);
        semesterListReverseOrder.reverseOrder();

        Iterator<Semester> list = semesterList.iterator();
        Iterator<Semester> reversedList = semesterListReverseOrder.iterator();

        while (list.hasNext() && reversedList.hasNext()) {
            Semester semester = list.next();
            Semester semesterFromReversed = reversedList.next();
            assertEquals(semester, semesterFromReversed);
        }
    }
}
