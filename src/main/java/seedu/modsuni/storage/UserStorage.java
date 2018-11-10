package seedu.modsuni.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.model.user.User;

/**
 * Represents a storage for {@link seedu.modsuni.model.user}.
 */
public interface UserStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserSavedFilePath();

    /**
     * @see #getUserSavedFilePath()
     */
    Optional<User> readUser(Path filePath, String password) throws DataConversionException,
            IOException, CorruptedFileException, NoSuchPaddingException, InvalidPasswordException,
            NoSuchAlgorithmException, InvalidKeyException;

    /**
     * Saves the given {@link User} to the storage.
     * @param user cannot be null
     * @param filePath the path to save the user data
     * @param password the password for encryption
     * @throws IOException
     */
    void saveUser(User user, Path filePath, String password) throws IOException;

}
