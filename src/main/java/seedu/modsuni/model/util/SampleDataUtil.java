package seedu.modsuni.model.util;

import java.util.ArrayList;

import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.Prereq;

/**
 * Contains utility methods for populating modsUni with sample data.
 */
public class SampleDataUtil {

    public static Module[] getSampleModules() {
        return new Module[]{
            new Module(new Code("ACC1002"), "Accounting", "Financial Accounting",
                "The course provides an introduction to financial accounting. It examines "
                    + "accounting from an external user's perspective: an external user being an "
                    + "investor or a creditor. Such users would need to understand financial "
                    + "accounting in order to make investing or lending decisions. However, to "
                    + "attain a good understanding, it is also necessary to be familiar with how "
                    + "the information is derived. Therefore, students would learn how to prepare "
                    + "the reports or statements resulting from financial accounting and how to use "
                    + "them for decision-making.",
                4, true, true, false, false, new ArrayList<Code>(), new Prereq()),
            new Module(new Code("CS1010"), "Computer Science", "Programming Methodology",
                "This module introduces the fundamental concepts of problem solving by "
                    + "computing and programming using an imperative programming language. It is the "
                    + "first and foremost introductory course to computing. It is also the first part"
                    + " of a three-part series on introductory programming and problem solving by "
                    + "computing, which also includes CS1020 and CS2010. Topics covered include "
                    + "problem solving by computing, writing pseudo-codes, basic problem formulation "
                    + "and problem solving, program development, coding, testing and debugging, "
                    + "fundamental programming constructs (variables, types, expressions, "
                    + "assignments, functions, control structures, etc.), fundamental data "
                    + "structures: arrays, strings and structures, simple file processing, and basic "
                    + "recursion. This module is appropriate for SoC students.",
                4, true, true, false, false, new ArrayList<Code>(), new Prereq()),
            new Module(new Code("ACC1002X"), "Accounting", "Financial Accounting",
                "The course provides an introduction to financial accounting. It examines "
                        + "accounting from an external user's perspective: an external user being an investor or "
                        + "a creditor. Such users would need to understand financial accounting in order to "
                        + "make investing or lending decisions. However, to attain a good understanding, it is"
                        + " also necessary to be familiar with how the information are derived. Therefore, "
                        + "students would learn how to prepare the reports or statements resulting from "
                        + "financial accounting and how to use them for decision-making.",
                4, true, true, false, false,
                new ArrayList<Code>(), new Prereq()),
            new Module(new Code("CS2103T"), "Computer Science", "Software Engineering",
                "This module introduces the necessary conceptual and analytical tools for systematic "
                        + "and rigorous development of software systems. It covers four main areas of software "
                        + "development, namely object-oriented system analysis, object-oriented system modelling and "
                        + "design, implementation, and testing, with emphasis on system modelling and design and "
                        + "implementation of software modules that work cooperatively to fulfill the requirements of "
                        + "the system. Tools and techniques for software development, such as Unified Modelling "
                        + "Language (UML), program specification, and testing methods, will be taught. Major software "
                        + "engineering issues such as modularisation criteria, program correctness, "
                        + "and software quality will also be covered.",
                4, true, true, false, false,
                new ArrayList<Code>(), new Prereq())
        };
    }

    public static ReadOnlyModuleList getSampleModuleList() {
        ModuleList sampleModuleList = new ModuleList();
        for (Module sampleModule : getSampleModules()) {
            sampleModuleList.addModule(sampleModule);
        }
        return sampleModuleList;
    }
}
