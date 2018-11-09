package seedu.modsuni.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;
import seedu.modsuni.model.semester.Semester;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.student.Student;

/**
 * Responsible for generating a student schedule based on the modules that has been staged.
 */
public class Generate {

    private List<Code> codesToTake;

    private UniqueModuleList modulesStaged;

    public Generate(Student student) {
        codesToTake = new ArrayList<>();
        modulesStaged = student.getModulesStaged();

        codesToTake.addAll(modulesStaged.getAllCode());
    }

    /**
     * Checks if a schedule can be generated or not.
     * @return
     */
    public static Optional<List<Code>> canGenerate(Student student) {
        List<Code> cannotTakeCode = new ArrayList<>();
        List<Code> codeChecklist = student.getTakenAndStageCode();
        UniqueModuleList modulesStaged = student.getModulesStaged();
        for (Module module : modulesStaged) {
            if (!module.checkPrereq(codeChecklist)) {
                cannotTakeCode.add(module.getCode());
            }
        }
        if (cannotTakeCode.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(cannotTakeCode);
        }
    }

    /**
     * Creates a schedule of semesters containing the modules to take for each semester.
     */
    public SemesterList generateSchedule() {
        SemesterList semesterList = new SemesterList();
        Semester newSemester = new Semester();

        List<Code> taken = new ArrayList<>();
        List<Module> toBeRemoved = new ArrayList<>();

        while (modulesStaged.size() > 0) {
            toBeRemoved.clear();
            for (Module element : modulesStaged) {
                if (element.checkPrereq(taken)) {
                    if (newSemester.getTotalCredits() + element.getCredit() > 20) {
                        semesterList.addSemester(newSemester);
                        newSemester = new Semester();
                    }
                    newSemester.addModule(element);
                    toBeRemoved.add(element);
                }
            }
            for (Module module : toBeRemoved) {
                taken.add(module.getCode());
                modulesStaged.remove(module);
            }
            semesterList.addSemester(newSemester);
            newSemester = new Semester();
        }
        return semesterList;
    }

}
