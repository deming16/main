package seedu.modsuni.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqDetails;

public class XmlAdaptedPrereqDetailsTest {
    private Prereq validAndPrereq;
    private Prereq validOrPrereq;
    private PrereqDetails validCodePrereqDetails;
    private PrereqDetails validAndPrereqDetails;
    private PrereqDetails validOrPrereqDetails;

    @Before
    public void setup() {
        validCodePrereqDetails = new PrereqDetails();
        validCodePrereqDetails.setCode(Optional.of(new Code("CS1010")));

        List<PrereqDetails> validPrereqDetails = new ArrayList<>();
        validPrereqDetails.add(validCodePrereqDetails);

        validAndPrereqDetails = new PrereqDetails();
        validAndPrereqDetails.setAnd(Optional.of(validPrereqDetails));

        validOrPrereqDetails = new PrereqDetails();
        validOrPrereqDetails.setOr(Optional.of(validPrereqDetails));

        validAndPrereq = new Prereq();
        validAndPrereq.setAnd(Optional.of(validPrereqDetails));

        validOrPrereq = new Prereq();
        validOrPrereq.setOr(Optional.of(validPrereqDetails));
    }

    @Test
    public void toModelType_xmlAdaptedOr_prereqCodeDetails() throws Exception {
        XmlAdaptedOr actualOutput = new XmlAdaptedOr(validCodePrereqDetails);
        assertEquals(validCodePrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedOr_prereqAndDetails() throws Exception {
        XmlAdaptedOr actualOutput = new XmlAdaptedOr(validAndPrereqDetails);
        assertEquals(validAndPrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedOr_prereqOrDetails() throws Exception {
        XmlAdaptedOr actualOutput = new XmlAdaptedOr(validOrPrereqDetails);
        assertEquals(validOrPrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedAnd_prereqCodeDetails() throws Exception {
        XmlAdaptedAnd actualOutput = new XmlAdaptedAnd(validCodePrereqDetails);
        assertEquals(validCodePrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedAnd_prereqAndDetails() throws Exception {
        XmlAdaptedAnd actualOutput = new XmlAdaptedAnd(validAndPrereqDetails);
        assertEquals(validAndPrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedAnd_prereqOrDetails() throws Exception {
        XmlAdaptedAnd actualOutput = new XmlAdaptedAnd(validOrPrereqDetails);
        assertEquals(validOrPrereqDetails, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedPrereq_prereqAnd() throws Exception {
        XmlAdaptedPrereq actualOutput = new XmlAdaptedPrereq(validAndPrereq);
        assertEquals(validAndPrereq, actualOutput.toModelType());
    }

    @Test
    public void toModelType_xmlAdaptedPrereq_prereqOr() throws Exception {
        XmlAdaptedPrereq actualOutput = new XmlAdaptedPrereq(validOrPrereq);
        assertEquals(validOrPrereq, actualOutput.toModelType());
    }


}
