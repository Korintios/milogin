package milo.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {

    // Method for encrypt the password.
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Method for verify the password.
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
