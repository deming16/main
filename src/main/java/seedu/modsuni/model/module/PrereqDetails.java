package seedu.modsuni.model.module;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Encapsulates the details of a prereq.
 */
public class PrereqDetails {
    private Optional<List<PrereqDetails>> or;
    private Optional<List<PrereqDetails>> and;
    private Optional<Code> code;

    public PrereqDetails() {
        or = Optional.empty();
        and = Optional.empty();
        code = Optional.empty();
    }

    public Optional<List<PrereqDetails>> getOr() {
        return or;
    }

    /**
     * Only or can exist, clears the others.
     */
    public void setOr(Optional<List<PrereqDetails>> or) {
        if (and.isPresent()) {
            and = Optional.empty();
        }
        if (code.isPresent()) {
            code = Optional.empty();
        }
        this.or = or;
    }

    public Optional<List<PrereqDetails>> getAnd() {
        return and;
    }

    /**
     * Only and can exist, clears the others.
     */
    public void setAnd(Optional<List<PrereqDetails>> and) {
        if (or.isPresent()) {
            or = Optional.empty();
        }
        if (code.isPresent()) {
            code = Optional.empty();
        }
        this.and = and;
    }

    public Optional<Code> getCode() {
        return code;
    }

    /**
     * Only code can exist, clears the others.
     */
    public void setCode(Optional<Code> code) {
        if (and.isPresent()) {
            and = Optional.empty();
        }
        if (or.isPresent()) {
            or = Optional.empty();
        }
        this.code = code;
    }

    /**
     * Checks if this prereq can be satisfied using the list of codes.
     * @param codeChecklist Codes that will be taken or has taken, to be checked against prereq.
     * @return true if this prereq can be satisfied, false otherwise.
     */
    public boolean checkPrereq(List<Code> codeChecklist) {
        if (code.isPresent()) {
            return codeChecklist.contains(code.get());
        } else if (and.isPresent()) {
            List<PrereqDetails> andPrereq = and.get();
            for (PrereqDetails element : andPrereq) {
                if (!element.checkPrereq(codeChecklist)) {
                    return false;
                }
            }
            return true;
        } else if (or.isPresent()) {
            List<PrereqDetails> orPrereq = or.get();
            for (PrereqDetails element : orPrereq) {
                if (element.checkPrereq(codeChecklist)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        if (code.isPresent()) {
            return code.get().code;
        } else if (or.isPresent()) {
            return "Or: " + Arrays.toString(or.get().toArray());
        } else if (and.isPresent()) {
            return "And: " + Arrays.toString(and.get().toArray());
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PrereqDetails)) {
            return false;
        }

        PrereqDetails otherPrereqDetails = (PrereqDetails) other;
        return otherPrereqDetails.getAnd().equals(getAnd())
                && otherPrereqDetails.getOr().equals(getOr())
                && otherPrereqDetails.getCode().equals(getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(or, and, code);
    }
}
