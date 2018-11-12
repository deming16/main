package seedu.modsuni.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.modsuni.testutil.Assert;

public class CodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

    @Test
    public void constructor_invalidCode_throwsIllegalArgumentException() {
        String invalidCode = "#cs1010";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Code(invalidCode));
    }

    @Test
    public void isValidSalary() {
        // null code
        Assert.assertThrows(NullPointerException.class, () -> Code.isValidCode(null));

        //invalid code
        assertFalse(Code.isValidCode("#CS1010")); // special character
        assertFalse(Code.isValidCode("")); // empty string

        //valid code
        assertTrue(Code.isValidCode("CS1010"));
    }

}
