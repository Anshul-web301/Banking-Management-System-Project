package Service;

import Dao.UserDao;
import Modal.User;


public class UserService {
    private final UserDao userDAO = new UserDao();

    // Register a new user
    public boolean register(String username, String password) {
        return UserDao.registerUser(username, password);
    }

    // Login user
    public User login(String username, String password) {
        return UserDao.loginUser(username, password);
    }

    // Update password
    public boolean updatePassword(String username, String newPassword) {
        return UserDao.updatePassword(username, newPassword);
    }

    // Check if a user exists
    public boolean doesUserExist(String username) {
        return UserDao.isUsernameExists(username);
    }
}
