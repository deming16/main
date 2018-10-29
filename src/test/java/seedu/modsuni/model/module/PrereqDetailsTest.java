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

public class PrereqDetailsTest {
    private PrereqDetails prereqDetailsCode;
    private PrereqDetails prereqDetailsOr;
    private PrereqDetails prereqDetailsAnd;
    private List<PrereqDetails> prereqDetailsList;
    private List<Code> codes;

    @Before
    public void setUp() {
        prereqDetailsCode = new PrereqDetails();
        prereqDetailsCode.setCode(Optional.of(CS1010.getCode()));

        prereqDetailsList = new ArrayList<>();
        prereqDetailsList.add(prereqDetailsCode);

        prereqDetailsOr = new PrereqDetails();
        prereqDetailsOr.setOr(Optional.of(prereqDetailsList));

        prereqDetailsAnd = new PrereqDetails();
        prereqDetailsAnd.setAnd(Optional.of(prereqDetailsList));

        codes = new ArrayList<>();
    }

    @Test
    public void toModelType_validSetCode_emptyOrAnd() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        assertEquals(prereqDetails, prereqDetailsCode);
    }

    @Test
    public void toModelType_validSetCode_containsOrAnd() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));
        prereqDetails.setAnd(Optional.of(prereqDetailsList));
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        assertEquals(prereqDetails, prereqDetailsCode);
    }

    @Test
    public void toModelType_validSetOr_emptyAndCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));
        assertEquals(prereqDetails, prereqDetailsOr);
    }

    @Test
    public void toModelType_validSetOr_containsAndCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setAnd(Optional.of(prereqDetailsList));
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        prereqDetails.setOr(Optional.of(prereqDetailsList));

        assertEquals(prereqDetails, prereqDetailsOr);
    }

    @Test
    public void toModelType_validSetAnd_emptyOrCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setAnd(Optional.of(prereqDetailsList));
        assertEquals(prereqDetails, prereqDetailsAnd);
    }

    @Test
    public void toModelType_validSetAnd_containsOrCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        prereqDetails.setAnd(Optional.of(prereqDetailsList));

        assertEquals(prereqDetails, prereqDetailsAnd);
    }

    @Test
    public void getOr_valid() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));

        assertEquals(prereqDetails.getOr(), Optional.of(prereqDetailsList));
    }

    @Test
    public void getAnd_valid() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setAnd(Optional.of(prereqDetailsList));

        assertEquals(prereqDetails.getAnd(), Optional.of(prereqDetailsList));
    }

    @Test
    public void checkPrereq_valid_noCodeAndOr() {
        PrereqDetails prereqDetails = new PrereqDetails();
        assertFalse(prereqDetails.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsCodeWithCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        codes.add(CS1010.getCode());

        assertTrue(prereqDetails.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsAndWithCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setAnd(Optional.of(prereqDetailsList));
        codes.add(CS1010.getCode());

        assertTrue(prereqDetails.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsAndWithoutCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setAnd(Optional.of(prereqDetailsList));

        assertFalse(prereqDetails.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsOrWithCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));
        codes.add(CS1010.getCode());

        assertTrue(prereqDetails.checkPrereq(codes));
    }

    @Test
    public void checkPrereq_valid_containsOrWithoutCode() {
        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setOr(Optional.of(prereqDetailsList));

        assertFalse(prereqDetails.checkPrereq(codes));
    }
}
