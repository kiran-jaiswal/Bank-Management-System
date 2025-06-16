import java.util.*;
import java.io.*;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private static final String FILE_NAME = "accounts.txt";
    private static final String TXN_FILE = "transactions.txt";

    public Bank() {
        loadAccountsFromFile();
    }

    public String generateAccountNumber() {
        return "ACC" + (new Random().nextInt(90000) + 10000);
    }

    public void createAccount(String name, String pin, double initialBalance) {
        String accNum = generateAccountNumber();
        Account acc = new Account(name, accNum, pin, initialBalance);
        accounts.put(accNum, acc);
        saveAccountsToFile();
        logTransaction(accNum, "Account Created. Initial Balance: " + initialBalance);
        System.out.println("Account created! Your Account Number is: " + accNum);
    }

    public boolean deposit(String accNum, double amount) {
        Account acc = accounts.get(accNum);
        if (acc != null) {
            acc.deposit(amount);
            saveAccountsToFile();
            logTransaction(accNum, "Deposited: " + amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accNum, double amount) {
        Account acc = accounts.get(accNum);
        if (acc != null && acc.withdraw(amount)) {
            saveAccountsToFile();
            logTransaction(accNum, "Withdrawn: " + amount);
            return true;
        }
        return false;
    }

    public Account getAccount(String accNum) {
        return accounts.get(accNum);
    }

    public boolean validatePin(String accNum, String inputPin) {
        Account acc = accounts.get(accNum);
        return acc != null && acc.getPin().equals(inputPin);
    }

    public boolean transfer(String fromAcc, String toAcc, double amount) {
        Account sender = accounts.get(fromAcc);
        Account receiver = accounts.get(toAcc);
        if (sender != null && receiver != null && sender.withdraw(amount)) {
            receiver.deposit(amount);
            saveAccountsToFile();
            logTransaction(fromAcc, "Transferred ₹" + amount + " to " + toAcc);
            logTransaction(toAcc, "Received ₹" + amount + " from " + fromAcc);
            return true;
        }
        return false;
    }

    public void showTransactionHistory(String accNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(TXN_FILE))) {
            String line;
            System.out.println("Transaction History for " + accNum + ":");
            while ((line = br.readLine()) != null) {
                if (line.startsWith(accNum)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transaction file.");
        }
    }

    private void logTransaction(String accNum, String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXN_FILE, true))) {
            bw.write(accNum + ": " + message);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to transaction file.");
        }
    }

    private void loadAccountsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
          while ((line = br.readLine()) != null) {
    Account acc = Account.fromFileString(line);
    if (acc != null) {
        accounts.put(acc.getAccountNumber(), acc);
    }
          }
        } catch (IOException e) {
            System.out.println("Error loading accounts.");
        }
    }

    private void saveAccountsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accounts.values()) {
                bw.write(acc.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }
}
