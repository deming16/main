package seedu.modsuni.model.semester;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates data required for a semester.
 */
public class Semester {

    private UniqueModuleList toBeTaken;

    public Semester() {
        this.toBeTaken = new UniqueModuleList();
    }

    /**
     * Adds a module to the semester.
     */
    public void addModule(Module module) {
        requireNonNull(module);
        toBeTaken.add(module);
    }

    /**
     * Get the total no of module credits in the semester.
     */
    public int getTotalCredits() {
        int total = 0;
        for (Module module : toBeTaken) {
            total += module.getCredit();
        }
        return total;
    }

    public ObservableList<Module> getModuleList() {
        return toBeTaken.asUnmodifiableObservableList();
    }

    public ObservableList<String> getModuleCodeList() {
        ObservableList<String> codesString = FXCollections.observableArrayList();
        List<Code> codes = getCode();
        for (Code code : codes) {
            codesString.add(code.code);
        }
        return codesString;
    }

    public List<Code> getCode() {
        toBeTaken.sortByModuleCode();
        List<Code> codes = new ArrayList<>();
        for (Module module : toBeTaken) {
            codes.add(module.getCode());
        }
        return codes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Semester // instanceof handles nulls
                && toBeTaken.equals(((Semester) other).toBeTaken)); // state check
    }

    @Override
    public int hashCode() {
        return toBeTaken.hashCode();
    }

    @Override
    public String toString() {
        return toBeTaken.getAllCode().toString();
    }
}
