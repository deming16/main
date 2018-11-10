package seedu.modsuni.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.crypto.NoSuchPaddingException;

import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.commons.util.FileUtil;
import seedu.modsuni.model.user.User;

/**
 * A class to access User data stored as an xml file on the hard disk.
 */
public class XmlUserStorage implements UserStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserStorage.class);

    private Path filePath;

    public XmlUserStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserSavedFilePath() {
        return filePath;
    }

    @Override
    public Optional<User> readUser() throws DataConversionException, IOException {
        return readUser();
    }

    /**
     * Reads user data from the file and decrypts using the given password
     * @param filePath location of the data. Cannot be null
     * @param password to decrypt the encrypted files
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
     * @throws CorruptedFileException
     * @throws NoSuchPaddingException
     * @throws InvalidPasswordException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Override
    public Optional<User> readUser(Path filePath, String password)
            throws DataConversionException, FileNotFoundException, CorruptedFileException,
            NoSuchPaddingException, InvalidPasswordException, NoSuchAlgorithmException, InvalidKeyException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("User saved file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUser xmlUser = XmlFileStorage.loadUserDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlUser.toModelType(password));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the current object to the file path
     * @param user cannot be null
     * @param filePath the path to save the user data
     * @param password the password for encryption
     * @throws IOException
     */
    public void saveUser(User user, Path filePath, String password) throws IOException {
        requireNonNull(user);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableUser(user, password));
    }

}
