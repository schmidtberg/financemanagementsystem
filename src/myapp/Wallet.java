package myapp;
import java.util.*;

public class Wallet {
    private final String name;
    private final List<Transaction> incomes = new ArrayList<>();
    private final List<Transaction> expenses = new ArrayList<>();
    private final Map<String, Double> budgets = new HashMap<>();

    public Wallet(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void addIncome(Transaction t) { incomes.add(t); }
    public void addExpense(Transaction t) { expenses.add(t); }
    public void addBudget(String category, double amount) { budgets.put(category, amount); }

    public double totalIncome() {
        return incomes.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public double totalExpense() {
        return expenses.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public Map<String, Double> incomesByCategory() {
        Map<String, Double> res = new HashMap<>();
        for (Transaction t : incomes) {
            res.put(t.getCategory(), res.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        return res;
    }

    public Map<String, Double> expensesByCategory() {
        Map<String, Double> res = new HashMap<>();
        for (Transaction t : expenses) {
            res.put(t.getCategory(), res.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        return res;
    }

    public Map<String, Double> budgetsState() {
        Map<String, Double> state = new HashMap<>();
        Map<String, Double> byCat = expensesByCategory();
        for (String cat : budgets.keySet()) {
            state.put(cat, budgets.get(cat) - byCat.getOrDefault(cat, 0.0));
        }
        return state;
    }

    public void printReport() {
        System.out.println("Кошелек: " + name);
        System.out.println("Общий доход: " + totalIncome());
        System.out.println("Доход по категориям: " + incomesByCategory());
        System.out.println("Общий расход: " + totalExpense());
        System.out.println("Расход по категориям: " + expensesByCategory());
        System.out.println("Бюджеты по категориям: " + budgets);
        System.out.println("Остатки бюджетов: " + budgetsState());
    }
}
