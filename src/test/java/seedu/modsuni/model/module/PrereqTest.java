package seedu.modsuni.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modsuni.testutil.TypicalModules.CS1010;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class PrereqTest {

    private PrereqDetails prereqDetailsCode;
    private List<PrereqDetails> prereqDetailsList;
    private List<Code> codes;

    @Before
    public void setUp() throws Exception {
        prereqDetailsCode = new PrereqDetails();
        prereqDetailsCode.setCode(Optional.of(CS1010.getCode()));

        prereqDetailsList = new ArrayList<>();
        prereqDetailsList.add(prereqDetailsCode);

        codes = new ArrayList<>();
    }

    @Test
    public void setOr_valid_emptyAnd() {
        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));

        assertEquals(prereq.getOr(), Optional.of(prereqDetailsList));
        assertEquals(prereq.getAnd(), Optional.empty());
    }

    @Test
    public void setOr_valid_containsAnd() {
        Prereq prereq = new Prereq();
        prereq.setAnd(Optional.of(prereqDetailsList));
        prereq.setOr(Optional.of(prereqDetailsList));

        assertEquals(prereq.getOr(), Optional.of(prereqDetailsList));
        assertEquals(prereq.getAnd(), Optional.empty());
    }

    @Test
    public void setAnd_valid_emptyAnd() {
        Prereq prereq = new Prereq();
        prereq.setAnd(Optional.of(prereqDetailsList));

        assertEquals(prereq.getAnd(), Optional.of(prereqDetailsList));
        assertEquals(prereq.getOr(), Optional.empty());
    }

    @Test
    public void setAnd_valid_containsAnd() {
        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));
        prereq.setAnd(Optional.of(prereqDetailsList));

        assertEquals(prereq.getAnd(), Optional.of(prereqDetailsList));
        assertEquals(prereq.getOr(), Optional.empty());
    }

    @Test
    public void equals_valid_null() {
        Prereq prereq = new Prereq();
        assertFalse(prereq.equals(null));
    }

    @Test
    public void equals_valid_sameType() {
        Prereq prereq = new Prereq();
        assertEquals(prereq, new Prereq());
    }

    @Test
    public void equals_valid_sameInstance() {
        Prereq prereq = new Prereq();
        assertEquals(prereq, prereq);
    }

    @Test
    public void noOfOrPrereq_valid_onePrereq() {
        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));
        assertEquals(prereq.noOfOrPrereq(), 1);
    }

    @Test
    public void noOfOrPrereq_valid_zeroPrereq() {
        Prereq prereq = new Prereq();
        assertEquals(prereq.noOfOrPrereq(), 0);
    }

    @Test
    public void noOfAndPrereq_valid_onePrereq() {
        Prereq prereq = new Prereq();
        prereq.setAnd(Optional.of(prereqDetailsList));
        assertEquals(prereq.noOfAndPrereq(), 1);
    }

    @Test
    public void noOfAndPrereq_valid_zeroPrereq() {
        Prereq prereq = new Prereq();
        assertEquals(prereq.noOfAndPrereq(), 0);
    }

    @Test
    public void checkPrereq_valid_noOrAndWithoutCode() {
        Prereq prereq = new Prereq();
        assertTrue(prereq.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsOrWithCode() {
        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));
        codes.add(CS1010.getCode());
        assertTrue(prereq.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsOrWithoutCode() {
        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));
        assertFalse(prereq.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsAndWithoutCode() {
        Prereq prereq = new Prereq();
        prereq.setAnd(Optional.of(prereqDetailsList));
        assertFalse(prereq.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsAndWithCode() {
        Prereq prereq = new Prereq();
        prereq.setAnd(Optional.of(prereqDetailsList));
        codes.add(CS1010.getCode());
        assertTrue(prereq.checkPrereq(codes));
    }
}
