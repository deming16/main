package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Module;
import seedu.address.model.ModuleList;
import seedu.address.model.ReadOnlyModuleList;

/**
 * An Immutable ModuleList that is serializable to XML format
 */
@XmlRootElement(name = "modulelist")
public class XmlSerializableModuleList {

    @XmlElement
    private List<XmlAdaptedModule> modules;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableModuleList() {
        modules = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableModuleList(ReadOnlyModuleList src) {
        this();
        modules.addAll(src.getModuleList().stream().map(XmlAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this modulelist into the model's {@code ModuleListt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public ModuleList toModelType() throws IllegalValueException {
        ModuleList moduleList = new ModuleList();
        for (XmlAdaptedModule m : modules) {
            Module module = m.toModelType();
        }
        return moduleList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableModuleList)) {
            return false;
        }
        return modules.equals(((XmlSerializableModuleList) other).modules);
    }


}
