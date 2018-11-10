package seedu.modsuni.commons.util;

import static org.junit.Assert.assertEquals;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Charsets;

import org.junit.rules.ExpectedException;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;

public class DataSecurityUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String PASSWORD = "p@ssW0Rd";
    private static final String WRONG_PASSWORD = "1";

    private static final byte[] TEST_DATA_ENCRYPTION = "TEST DATA".getBytes();
    private static final String EXPECTED_ENCRYPTION = "D91B9E39621EACE5877DCD6158393F0F";

    private static final byte[] TEST_DATA_DECRYPTION = convertStringToByteArray("D91B9E39621EACE5877DCD6158393F0F");
    private static final String EXPECTED_DECRYPTION = "TEST DATA";

    // Encryption test case
    @Test
    public void testEncryptingBytes() {
        byte[] encryptedData = DataSecurityUtil.encryptData(TEST_DATA_ENCRYPTION, PASSWORD);
        String encryptedDataString = convertByteArrayToHexString(encryptedData);

        assertEquals(EXPECTED_ENCRYPTION, encryptedDataString);
    }

    @Test
    public void testEncryptingNullBytesValidPassword() {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.encryptData(null, PASSWORD);
    }

    @Test
    public void testEncryptingValidBytesAndNullPassword() {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.encryptData(TEST_DATA_ENCRYPTION, null);
    }

    @Test
    public void testEncryptingNullBytesAndNullPassword() {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.encryptData(null, null);
    }

    // Decryption test case
    @Test
    public void testDecryptingBytes() throws CorruptedFileException, InvalidPasswordException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] decryptedData = DataSecurityUtil.decryptData(TEST_DATA_DECRYPTION, PASSWORD);
        String decryptedDataString = new String(decryptedData, Charsets.UTF_8);

        assertEquals(EXPECTED_DECRYPTION, decryptedDataString);
    }

    @Test
    public void testDecryptingNullBytesValidPassword() throws CorruptedFileException,
            InvalidPasswordException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.decryptData(null, PASSWORD);
    }

    @Test
    public void testDecryptingValidBytesAndNullPassword() {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.encryptData(TEST_DATA_DECRYPTION, null);
    }

    @Test
    public void testDecryptingNullBytesAndNullPassword() {
        thrown.expect(NullPointerException.class);
        DataSecurityUtil.encryptData(null, null);
    }

    @Test
    public void testDecryptingBytesWrongPassword() throws CorruptedFileException, InvalidPasswordException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        thrown.expect(InvalidPasswordException.class);
        DataSecurityUtil.decryptData(TEST_DATA_DECRYPTION, WRONG_PASSWORD);
    }

    private static String convertByteArrayToHexString(byte[] data) {
        return DatatypeConverter.printHexBinary(data);
    }

    private static byte[] convertStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}
