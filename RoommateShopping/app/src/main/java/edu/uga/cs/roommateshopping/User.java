package edu.uga.cs.roommateshopping;

/**
 * This class represents a single user, including the name of the
 * user, email, user, and password.
 */
public class User {

    String name;
    String email;
    String username;
    String password;

    /**
     * Required empty constructor for a user.
     */
    public User() {

    }

    /**
     * Constructor of a User that initializes a user's name, email, username,
     * and password.
     * @param name
     * @param email
     * @param username
     * @param password
     */
    public User(String name, String email, String username, String password) {
       this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the name of the user.
     * @return Returns the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name Represents the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     * @return Returns the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email Represents the email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username of the user.
     * @return Returns the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username Represents the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * @return Returns the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password Represents the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
