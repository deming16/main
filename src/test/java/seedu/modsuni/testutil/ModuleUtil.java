package seedu.modsuni.testutil;

import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_AVAILABLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CREDIT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DEPARTMENT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DESCRIPTION;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_PREREQ;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import seedu.modsuni.logic.commands.AddModuleToDatabaseCommand;
import seedu.modsuni.model.module.Module;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an addModuleDB command string for adding the {@code module}.
     */
    public static String getAddModuleToStudentStagedCommand(Module module) {
        return AddModuleToDatabaseCommand.COMMAND_WORD + " "
                + module.getCode().toString();
    }

    /**
     * Returns an addModuleDB command string for adding the {@code module}.
     */
    public static String getAddModuleToDatabase(Module module) {
        return AddModuleToDatabaseCommand.COMMAND_WORD + " "
                + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code admin}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE + module.getCode().code + " ");
        sb.append(PREFIX_MODULE_DEPARTMENT + module.getDepartment() + " ");
        sb.append(PREFIX_MODULE_TITLE + module.getTitle() + " ");
        sb.append(PREFIX_MODULE_DESCRIPTION + module.getDescription() + " ");
        sb.append(PREFIX_MODULE_CREDIT + String.valueOf(module.getCredit()) + " ");
        sb.append(PREFIX_MODULE_AVAILABLE + "1100" + " ");
        sb.append(PREFIX_MODULE_PREREQ + "");
        return sb.toString();
    }
}
