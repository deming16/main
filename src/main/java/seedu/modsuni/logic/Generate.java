package seedu.modsuni.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

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

    private int noOfModules; // No. of vertices
    private LinkedList<Integer>[] adj; // Adjacency List
    private List<Code> codesToTake;
    private List<Code> codesWithNoPrereq;

    private UniqueModuleList modulesStaged;

    public Generate(Student student) {
        codesToTake = new ArrayList<>();
        modulesStaged = student.getModulesStaged();

        modulesStaged.sortMajorThenPrereq();
        codesToTake.addAll(modulesStaged.getAllCode());

        codesWithNoPrereq = modulesStaged.getModuleCodesWithNoPrereq();

        noOfModules = modulesStaged.size();

        adj = new LinkedList[noOfModules];
        for (int i = 0; i < noOfModules; ++i) {
            adj[i] = new LinkedList();
        }

        for (Module moduleToTake : modulesStaged) {
            for (Code code : moduleToTake.getLockedModules()) {
                if (codesToTake.contains(code)) {
                    addEdge(moduleToTake.getCode(), code);
                }
            }
        }
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
     * Creates an edge from a module code to another module code.
     */
    public void addEdge(Code fromCode, Code toCode) {
        int v = codesToTake.indexOf(fromCode);
        int code = codesToTake.indexOf(toCode);
        adj[v].add(code);
    }

    /**
     * Topological sort recursive function to arrange the modules.
     */
    public void topologicalSortUtil(int v, boolean[] visited, Stack stack) {
        visited[v] = true;
        int i;

        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(new Integer(v));
    }

    /**
     * Creates a linear arrangement of modules to take.
     */
    private ArrayList<Code> getLinearSchedule() {
        ArrayList<Code> linearSchedule = new ArrayList<>();
        Stack stack = new Stack();

        boolean[] visited = new boolean[noOfModules];
        for (int i = 0; i < noOfModules; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < noOfModules; i++) {
            if (visited[i] == false) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        while (stack.empty() == false) {
            int position = (Integer) stack.pop();
            linearSchedule.add(codesToTake.get(position));
        }

        for (Code code : codesWithNoPrereq) {
            if (linearSchedule.contains(code)) {
                linearSchedule.remove(code);
                linearSchedule.add(0, code);
            }
        }

        return linearSchedule;
    }

    public SemesterList generateSchedule() {
        SemesterList semesterList = new SemesterList();
        Semester newSemester = new Semester();

        List<Code> taken = new ArrayList<>();
        List<Module> toBeRemoved = new ArrayList<>();

        while (modulesStaged.size() > 0) {
            modulesStaged.sortMajorThenLocked();
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

    /**
     * Creates a schedule of semesters containing the modules to take for each semester.
     */
    public SemesterList getSchedule() {
        ArrayList<Code> linearSchedule = getLinearSchedule();

        SemesterList semesterList = new SemesterList();

        Semester newSem = new Semester();

        System.out.println(linearSchedule.toString());

        for (Code code : linearSchedule) {
            Module module = modulesStaged.getModuleByCode(code);

            if (!module.hasPrereq()) {
                if (newSem.getTotalCredits() + module.getCredit() > 20) {
                    semesterList.addSemester(newSem);
                    newSem = new Semester();
                }
                newSem.addModule(module);
                continue;
            }

            if (module.checkPrereq(newSem.getCode())) {
                semesterList.addSemester(newSem);
                newSem = new Semester();
                newSem.addModule(module);
            } else {
                if (newSem.getTotalCredits() + module.getCredit() > 20) {
                    semesterList.addSemester(newSem);
                    newSem = new Semester();
                }
                newSem.addModule(module);
            }

        }
        semesterList.addSemester(newSem);

        return semesterList;


        /*
        Collections.reverse(linearSchedule);

        Optional<Code> previousCode = Optional.empty();

        for (Code code : linearSchedule) {
            Module module = modulesStaged.getModuleByCode(code);
            List<Code> lockedModules = module.getLockedModules();

            if (!previousCode.isPresent()) {
                newSem.addModule(module);
                previousCode = Optional.of(module.getCode());
                continue;
            }

            if (lockedModules.contains(previousCode.get())) {
                semesterList.addSemester(newSem);
                newSem = new Semester();
            }

            if (newSem.getTotalCredits() > 20) {
                semesterList.addSemester(newSem);
                newSem = new Semester();
            }
            newSem.addModule(module);

            previousCode = Optional.of(module.getCode());
        }
        semesterList.addSemester(newSem);
        semesterList.reverseOrder();
        return semesterList;*/
    }
}
