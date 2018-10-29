package seedu.modsuni.model.module;

import org.junit.Test;

import seedu.modsuni.testutil.Assert;

public class CodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

}
