package seedu.modsuni.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqDetails;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Prereq.
 */
public class XmlAdaptedPrereq {
    @XmlElement
    private List<XmlAdaptedAnd> and;
    @XmlElement
    private List<XmlAdaptedOr> or;

    public XmlAdaptedPrereq() {
        and = new ArrayList<>();
        or = new ArrayList<>();
    }

    /**
     * Converts this jaxb-friendly adapted prereq object into the model's Module object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted prereq code
     */
    public Prereq toModelType() throws IllegalValueException {
        Prereq prereq = new Prereq();
        if (and.size() != 0) {
            List<PrereqDetails> prereqAnds = new ArrayList<>();
            for (XmlAdaptedAnd element : and) {
                prereqAnds.add(element.toModelType());
            }
            prereq.setAnd(Optional.of(prereqAnds));
        }
        if (or.size() != 0) {
            List<PrereqDetails> prereqOrs = new ArrayList<>();
            for (XmlAdaptedOr element : or) {
                prereqOrs.add(element.toModelType());
            }
            prereq.setOr(Optional.of(prereqOrs));
        }
        return prereq;
    }

    @Override
    public String toString() {
        String returnVal = "";
        if (and != null) {
            returnVal = "And: " + and.toString();
        }
        if (or != null) {
            returnVal += ", Or: " + or.toString();
        }
        return returnVal;
    }
}
