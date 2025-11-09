package myapp;

import java.util.Scanner;

public class FinanceApp {
    private final AuthSystem auth = new AuthSystem();
    private User currentUser;
    private final Scanner sc = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.print("register/login/exit: ");
            String cmd = sc.next().trim();
            switch (cmd) {
                case "register" -> {
                    System.out.print("Username: ");
                    String u = sc.next();
                    System.out.print("Password: ");
                    String p = sc.next();
                    System.out.println(auth.register(u, p) ? "Registered." : "User exists.");
                }
                case "login" -> {
                    System.out.print("Username: ");
                    String u = sc.next();
                    System.out.print("Password: ");
                    String p = sc.next();
                    User user = auth.login(u, p);
                    if (user != null) {
                        System.out.println("Hello, " + u);
                        currentUser = user;
                        userMenu();
                        currentUser = null;
                    } else {
                        System.out.println("Login failed.");
                    }
                }
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Unknown command. Please try again.");
            }
        }
    }

    private void userMenu() {
        while (true) {
            System.out.print("wallet/add_income/add_expense/add_budget/report/transfer/logout: ");
            String cmd = sc.next().trim();
            switch (cmd) {
                case "wallet" -> {
                    System.out.print("Wallet name: ");
                    String w = sc.next();
                    currentUser.addWallet(new Wallet(w));
                    System.out.println("Added wallet " + w);
                }
                case "add_income" -> {
                    Wallet w = currentUser.getWallet(input("To wallet: "));
                    if (w != null) {
                        double a = Double.parseDouble(input("Amount: "));
                        String cat = input("Category: ");
                        String desc = input("Desc: ");
                        w.addIncome(new Transaction(a, cat, desc));
                        System.out.println("Income added.");
                    } else System.out.println("Wallet not found.");
                }
                case "add_expense" -> {
                    Wallet w = currentUser.getWallet(input("From wallet: "));
                    if (w != null) {
                        double a = Double.parseDouble(input("Amount: "));
                        String cat = input("Category: ");
                        String desc = input("Desc: ");
                        w.addExpense(new Transaction(a, cat, desc));
                        System.out.println("Expense added.");
                    } else System.out.println("Wallet not found.");
                }
                case "add_budget" -> {
                    Wallet w = currentUser.getWallet(input("Wallet: "));
                    if (w != null) {
                        String cat = input("Category: ");
                        double amt = Double.parseDouble(input("Budget amount: "));
                        w.addBudget(cat, amt);
                        System.out.println("Budget added.");
                    } else System.out.println("Wallet not found.");
                }
                case "report" -> {
                    Wallet w = currentUser.getWallet(input("Wallet: "));
                    if (w != null) w.printReport();
                    else System.out.println("Wallet not found.");
                }
                case "transfer" -> {
                    Wallet from = currentUser.getWallet(input("From wallet: "));
                    Wallet to = currentUser.getWallet(input("To wallet: "));
                    if (from != null && to != null) {
                        double amt = Double.parseDouble(input("Amount: "));
                        String cat = input("Category: ");
                        String desc = input("Desc: ");
                        from.addExpense(new Transaction(amt, cat, "Transfer to '" + to.getName() + "': " + desc));
                        to.addIncome(new Transaction(amt, cat, "Transfer from '" + from.getName() + "': " + desc));
                        System.out.println("Transfer done.");
                    } else System.out.println("Some wallet not found.");
                }
                case "logout" -> { 
                    return; 
                }
                default -> System.out.println("Unknown command. Please try again.");
            }
        }
    }

    private String input(String msg) {
        System.out.print(msg);
        return sc.next();
    }

    public static void main(String[] args) {
        new FinanceApp().run();
    }
}
