import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();
        int choice;

        do {
            System.out.println("\n=== Bank Management System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // flush newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Set a 4-digit PIN: ");
                    String pin = sc.nextLine();
                    System.out.print("Initial Deposit: ");
                    double initBalance = sc.nextDouble();
                    bank.createAccount(name, pin, initBalance);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    String accNumDep = sc.nextLine();
                    System.out.print("Enter PIN: ");
                    String pinDep = sc.nextLine();
                    if (!bank.validatePin(accNumDep, pinDep)) {
                        System.out.println("Invalid PIN.");
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    double amountDep = sc.nextDouble();
                    if (bank.deposit(accNumDep, amountDep))
                        System.out.println("Amount deposited.");
                    else
                        System.out.println("Invalid account number.");
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    String accNumW = sc.nextLine();
                    System.out.print("Enter PIN: ");
                    String pinW = sc.nextLine();
                    if (!bank.validatePin(accNumW, pinW)) {
                        System.out.println("Invalid PIN.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    double amountW = sc.nextDouble();
                    if (bank.withdraw(accNumW, amountW))
                        System.out.println("Amount withdrawn.");
                    else
                        System.out.println("Invalid account number or insufficient balance.");
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    String accNumB = sc.nextLine();
                    System.out.print("Enter PIN: ");
                    String pinB = sc.nextLine();
                    if (!bank.validatePin(accNumB, pinB)) {
                        System.out.println("Invalid PIN.");
                        break;
                    }
                    Account acc = bank.getAccount(accNumB);
                    if (acc != null)
                        System.out.println("Current Balance: ₹" + acc.getBalance());
                    else
                        System.out.println("Account not found.");
                    break;

                case 5:
                    System.out.print("Enter Sender Account Number: ");
                    String fromAcc = sc.nextLine();
                    System.out.print("Enter PIN: ");
                    String fromPin = sc.nextLine();
                    if (!bank.validatePin(fromAcc, fromPin)) {
                        System.out.println("Invalid PIN.");
                        break;
                    }
                    System.out.print("Enter Receiver Account Number: ");
                    String toAcc = sc.nextLine();
                    System.out.print("Enter Amount: ");
                    double amt = sc.nextDouble();
                    if (bank.transfer(fromAcc, toAcc, amt))
                        System.out.println("Transfer successful.");
                    else
                        System.out.println("Transfer failed.");
                    break;

                case 6:
                    System.out.print("Enter Account Number: ");
                    String accNumT = sc.nextLine();
                    System.out.print("Enter PIN: ");
                    String pinT = sc.nextLine();
                    if (!bank.validatePin(accNumT, pinT)) {
                        System.out.println("Invalid PIN.");
                        break;
                    }
                    bank.showTransactionHistory(accNumT);
                    break;

                case 7:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }
}
