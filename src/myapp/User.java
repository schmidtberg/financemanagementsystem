package myapp;
import java.util.*;

public class User {
    private final String username;
    private final String password;
    private final List<Wallet> wallets = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addWallet(Wallet w) { wallets.add(w); }

    public Wallet getWallet(String name) {
        return wallets.stream().filter(w -> w.getName().equals(name)).findFirst().orElse(null);
    }

    public String getUsername() { return username; }

    public boolean checkPassword(String p) { return password.equals(p); }
}
