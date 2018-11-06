package seedu.modsuni.storage;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.model.user.User;


/**
 * An Immutable User that is serializable to XML format
 */
@XmlRootElement(name = "user")
public class XmlSerializableUser {

    @XmlElement
    private XmlAdaptedUser user;

    /**
     * Creates an empty XmlSerializableUser.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUser() {

    }

    /**
     * Conversion
     */
    public XmlSerializableUser(User user) {
        this.user = new XmlAdaptedUser(user);
    }

    /**
     * Conversion
     */
    public XmlSerializableUser(User user, String password) {
        this.user = new XmlAdaptedUser(user, password);
    }

    /**
     * Converts this XmlSerializableUser into the model's {@code User}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedUser}.
     */
    public User toModelType(String password) throws IllegalValueException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidPasswordException, CorruptedFileException, NoSuchPaddingException {
        return this.user.toModelType(password);
    }
}
