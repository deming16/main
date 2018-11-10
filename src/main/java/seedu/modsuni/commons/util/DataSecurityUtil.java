package seedu.modsuni.commons.util;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;

import seedu.modsuni.MainApp;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;

/**
 * Encrypts and decrypts data
 */
public class DataSecurityUtil {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";
    private static final String INVALID_PASSWORD_MESSAGE = "Invalid Password";
    private static final String CORRUPTED_FILE_MESSAGE = "The encrypted file may be corrupted. Decryption failed.";
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String SECURE_RANDOM_INSTANCE = "SHA1PRNG";
    private static final int SEED_BYTE_COUNT = 5;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Encrypts the given data using a password
     *
     * @param data     The data to be encrypted
     * @param password Used to encryptData data
     * @return byte[] of the encrypted data
     */
    public static byte[] encryptData(byte[] data, String password) {
        requireNonNull(data);
        requireNonNull(password);

        try {
            Key secretKey = generateSecretKey(password);
            Cipher aesCipher = Cipher.getInstance(CIPHER_INSTANCE);
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            logger.info("Data encrypted");
            return aesCipher.doFinal(data);
        } catch (Exception e) {
            logger.severe("Unable to encryptData data: " + e);
        }

        return new byte[0];
    }

    /**
     * Decrypts the data using a given password
     *
     * @param data     The data to be decrypted
     * @param password Used to decryptData data
     * @return byte[] of the decrypted data
     * @throws InvalidPasswordException if an invalid password is supplied
     */
    public static byte[] decryptData(byte[] data, String password) throws InvalidPasswordException,
        CorruptedFileException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        requireNonNull(data);
        requireNonNull(password);

        try {
            Key secretKey = generateSecretKey(password);
            Cipher aesCipher = Cipher.getInstance(CIPHER_INSTANCE);
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey); // set Cipher to decryption mode
            logger.info("Data decrypted");
            return aesCipher.doFinal(data);
        } catch (BadPaddingException e) {
            logger.warning("Invalid password");
            handleBadPaddingException();
        } catch (IllegalBlockSizeException e) {
            logger.warning("Corrupted file");
            handleIllegalBlockSizeException();
        }

        return new byte[0];
    }

    /**
     * Generates a key
     *
     * @param password The password to generate a key
     * @return A secret key
     */
    private static Key generateSecretKey(String password) {
        requireNonNull(password);
        return new SecretKeySpec(getFirst16Bytes(generateSha1Hash(password).getBytes()), ALGORITHM);
    }

    /**
     * Handles BadPaddingException
     *
     * @throws InvalidPasswordException if an invalid password is supplied
     */
    private static void handleBadPaddingException() throws InvalidPasswordException {
        throw new InvalidPasswordException(INVALID_PASSWORD_MESSAGE);
    }

    /**
     * Handles IllegalBlockSizeException
     *
     * @throws CorruptedFileException if an encrypted file is corrupted
     */
    private static void handleIllegalBlockSizeException() throws CorruptedFileException {
        throw new CorruptedFileException(CORRUPTED_FILE_MESSAGE);
    }

    /**
     * Generates a SHA-1 hash using a string
     *
     * @param password The string to be hashed
     * @return a string of SHA-1 hash
     */
    private static String generateSha1Hash(String password) {
        requireNonNull(password);
        return Hashing.sha1().hashString(password, CHARSET).toString();
    }

    /**
     * Gets the first 16 bytes of a byte array
     *
     * @param password The string to be hashed
     * @return first 16 bytes of a byte array
     */
    private static byte[] getFirst16Bytes(byte[] password) {
        requireNonNull(password);
        return Arrays.copyOf(password, 16);
    }

    /**
     * Generates a random string of length 7
     */
    public static String randomSha1() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_INSTANCE);
        byte[] seed = secureRandom.generateSeed(SEED_BYTE_COUNT);

        SecureRandom sha1Random = SecureRandom.getInstance(SECURE_RANDOM_INSTANCE);
        sha1Random.setSeed(seed);

        byte[] randomBytes = new byte[128];
        sha1Random.nextBytes(randomBytes);

        return bytesToHex(randomBytes).substring(0, 7);
    }

    /**
     * Converts bytes to a hex string.
     * DatatypeConverter is not used since it not longer included in default path of Java SE 9 and is completely removed in Java 11.
     * Source: https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j
     *
     * @param bytes
     * @return String of hex
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Converts bytes to a base64 string
     *
     * @param bytes data to convert to base 64
     * @return String of base64
     */
    public static String bytesToBase64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * Converts a string from base64 to a byte[]
     *
     * @param base64 data to convert to base 64
     * @return String of base64
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64.getBytes());
    }
}
