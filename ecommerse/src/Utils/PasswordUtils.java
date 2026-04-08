package Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hash the plain password before saving to Database
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Compare plain text password with stored hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}