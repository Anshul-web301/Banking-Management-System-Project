package Modal;

public class User {
    private final int userId;
    private final String username;
    private final String password;
    public int getaccount_id;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getaccount_Id() {
        return "";
    }
}
