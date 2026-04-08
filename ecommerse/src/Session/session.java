package Session;

import Models.Users;

public class session {
    private static Users currentUser = null;

    public static void login(Users user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static Users getUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
