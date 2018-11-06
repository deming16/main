package seedu.modsuni.model.user;

import seedu.modsuni.model.credential.Username;

/**
 * General details of a User account.
 */
public abstract class User {
    protected Username username;
    protected Name name;
    protected Role role;

    /**
     * Constructor method of User
     *
     * @param name The name of the user.
     * @param role The role of the user.
     */
    public User(Username username, Name name, Role role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public Username getUsername() {
        return username;
    }

    public Name getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public abstract String toDisplayUi();


}
