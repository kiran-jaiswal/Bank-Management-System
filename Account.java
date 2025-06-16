public class Account {
    private String name;
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String name, String accountNumber, String pin, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String toFileString() {
        return name + "," + accountNumber + "," + pin + "," + balance;
    }

    public static Account fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.out.println("Skipping invalid account line: " + line);
            return null;
        }
        try {
            String name = parts[0];
            String accNum = parts[1];
            String pin = parts[2];
            double balance = Double.parseDouble(parts[3]);
            return new Account(name, accNum, pin, balance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in line: " + line);
            return null;
        }
    }
}
