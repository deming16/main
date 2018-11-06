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
     * Returns User data as a {@link User}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<User> readUser() throws DataConversionException, IOException;

    /**
     * @see #getUserSavedFilePath()
     */
    Optional<User> readUser(Path filePath) throws DataConversionException, IOException;

    /**
     * @see #getUserSavedFilePath()
     */
    Optional<User> readUser(Path filePath, String password) throws DataConversionException,
            IOException, CorruptedFileException, NoSuchPaddingException, InvalidPasswordException,
            NoSuchAlgorithmException, InvalidKeyException;

    /**
     * Saves the given {@link User} to the storage.
     * @param user cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUser(User user) throws IOException;

    /**
     * @see #saveUser(User)
     */
    void saveUser(User user, Path filePath) throws IOException;

    /**
     * @see #saveUser(User)
     */
    void saveUser(User user, Path filePath, String password) throws IOException;

}
