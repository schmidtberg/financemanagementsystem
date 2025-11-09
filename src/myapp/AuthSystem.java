package myapp;
import java.util.*;

public class AuthSystem {
    private final Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password));
            return true;
        }
        return false;
    }

    public User login(String username, String password) {
        User u = users.get(username);
        if (u != null && u.checkPassword(password)) return u;
        return null;
    }
}
