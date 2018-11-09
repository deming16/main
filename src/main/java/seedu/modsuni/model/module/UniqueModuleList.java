package seedu.modsuni.model.module;
import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.model.module.exceptions.DuplicateModuleException;
import seedu.modsuni.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and
 * updating of modules uses Module#isSameModule(Module) for equality so as to ensure that the module being
 * added or updated is unique in terms of identity in the UniqueModuleList. However, the removal of a module
 * uses Module#equals(Object) so as to ensure that the module with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Module#isSameModule(Module)
 * @see Module#isPrefixModule(Module)
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    public Module get(int index) {
        return internalList.get(index);
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    public int size() {
        return internalList.size();
    }

    public List<Code> getAllCode() {
        List<Code> codes = new ArrayList<>();
        for (Module module : internalList) {
            codes.add(module.getCode());
        }
        return codes;
    }

    /**
     * Returns true if the student has added modules to take and false if otherwise.
     */
    public boolean hasModules() {
        return !internalList.isEmpty();
    }

    /**
     * Returns the Optional of the Module.
     */
    public Optional<Module> search(Code toSearch) {
        requireNonNull(toSearch);
        for (Module module : internalList) {
            if (module.getCode().equals(toSearch)) {
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    /**
     * Searches the Index of {@Code toSearch} in the {@Code internalList}
     * Returns the Index of {@Code toSearch}
     */
    public Index searchForIndex(Module toSearch) {
        requireNonNull(toSearch);
        int index = internalList.indexOf(toSearch);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }
        return Index.fromZeroBased(index);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the
     * list.
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }
        internalList.set(index, editedModule);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    public Module getModuleByCode(Code code) {
        requireNonNull(code);
        for (Module module : internalList) {
            if (module.getCode().equals(code)) {
                return module;
            }
        }
        throw new ModuleNotFoundException();
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }
        internalList.setAll(modules);
    }

    /**
     * Get a list of module codes without prereqs.
     */
    public List<Code> getModuleCodesWithNoPrereq() {
        List<Code> modulesWithNoPrereq = new ArrayList<>();
        for (Module module : internalList) {
            if (!module.hasPrereq()) {
                modulesWithNoPrereq.add(module.getCode());
            }
        }
        return modulesWithNoPrereq;
    }

    public void sortMajorThenPrereq() {
        internalList.sort(new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {
                int result = o1.getCode().compareTo(o2.getCode());
                if (result == 0) {
                    int noOfDependentModuleO1 = 0;
                    int noOfDependentModuleO2 = 0;
                    for (Module module : internalList) {
                        if (o1.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO1++;
                        }
                        if (o2.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO2++;
                        }
                    }
                    return Integer.compare(noOfDependentModuleO1, noOfDependentModuleO2);
                } else {
                    return result;
                }
            }
        });
    }

    public void sortMajorThenLocked() {
        internalList.sort(new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {
                int result = o1.getCode().compareTo(o2.getCode());
                if (result == 0) {
                    int noOfDependentModuleO1 = 0;
                    int noOfDependentModuleO2 = 0;
                    for (Module module : internalList) {
                        if (o1.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO1++;
                        }
                        if (o2.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO2++;
                        }
                    }
                    return Integer.compare(noOfDependentModuleO1, noOfDependentModuleO2);
                } else {
                    return result;
                }
            }
        });
    }

    public void sortPrereqThenMajor() {
        internalList.sort(new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {
                if (o1.hasPrereq() && !o2.hasPrereq()) {
                    return -1;
                } else if (o1.hasPrereq() && o2.hasPrereq()) {
                    return o1.getCode().compareTo(o2.getCode());
                } else {
                    return 1;
                }
                /*
                int result = o1.getCode().compareTo(o2.getCode());
                if (result == 0) {
                    int noOfDependentModuleO1 = 0;
                    int noOfDependentModuleO2 = 0;
                    for (Module module : internalList) {
                        if (o1.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO1++;
                        }
                        if (o2.getLockedModules().contains(module.getCode())) {
                            noOfDependentModuleO2++;
                        }
                    }
                    return Integer.compare(noOfDependentModuleO1, noOfDependentModuleO2);
                } else {
                    return result;
                }*/
            }
        });
    }

    /**
     * Rearranges the list of modules by placing those core modules in front.
     */
    public void sortMajorModuleFirst() {
        internalList.sort(new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {
                return o1.getCode().compareTo(o2.getCode());
            }
        });
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).isSameModule(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
