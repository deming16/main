package seedu.modsuni.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.base.Charsets;

import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;

public class DataSecurityUtilTest {

    private static final String PASSWORD = "p@ssW0Rd";
    private static final String WRONG_PASSWORD = "1";
    private static final String TEST_STRING = "TEST DATA";
    private static final byte[] TEST_DATA_ENCRYPTION = TEST_STRING.getBytes();
    private static final String EXPECTED_ENCRYPTION = "D91B9E39621EACE5877DCD6158393F0F";
    private static final byte[] TEST_DATA_DECRYPTION = convertStringToByteArray("D91B9E39621EACE5877DCD6158393F0F");
    private static final String EXPECTED_DECRYPTION = TEST_STRING;
    private static final String EXPECTED_ENCODED_BYTES_TO_HEX = "544553542044415441";
    private static final String EXPECTED_ENCODED_BYTES_TO_BASE64 = "VEVTVCBEQVRB";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    // Testing utility
    private static String convertByteArrayToHexString(byte[] data) {
        return DatatypeConverter.printHexBinary(data);
    }

    private static byte[] convertStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    // Test for supporting utility
    @Test
    public void testGenerateRandomSha1Random() throws NoSuchAlgorithmException {
        String baseSha1 = DataSecurityUtil.generateRandomSha1();
        String compareSha1 = DataSecurityUtil.generateRandomSha1();
        assertNotEquals(baseSha1, compareSha1);
    }

    @Test
    public void testBytesToHex() {
        String convertedData = DataSecurityUtil.bytesToHex(TEST_STRING.getBytes());
        assertEquals(EXPECTED_ENCODED_BYTES_TO_HEX, convertedData);
    }

    @Test
    public void testBytesToBase64() {
        String convertedData = DataSecurityUtil.bytesToBase64(TEST_STRING.getBytes());
        assertEquals(EXPECTED_ENCODED_BYTES_TO_BASE64, convertedData);
    }

    @Test
    public void testBase64ToBytes() {
        byte[] convertedData = DataSecurityUtil.base64ToBytes(EXPECTED_ENCODED_BYTES_TO_BASE64);
        assertEquals(TEST_STRING, new String(convertedData));
    }


}
