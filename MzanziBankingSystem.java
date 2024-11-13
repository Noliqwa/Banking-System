import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Question 3
// Interface for banking operations
interface BankingOperations {
    double checkBalance();

    void deposit(double amount);

    boolean withdraw(double amount);

    boolean transferFunds(Account recipient, double amount);
}

// Base class representing an account
abstract class Account implements BankingOperations {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    @Override
    public double checkBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        balance = balance+amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance = balance -amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
            return true;
        } else {
            System.out.println("Insufficient funds.");
            return false;
        }
    }

    @Override
    public boolean transferFunds(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            return true;
        } else {
            return false;
        }
    }
}

// Question 2
// Concrete class representing a savings account
class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber) {
        super(accountNumber);
    }
}

// Question 2
// Concrete class representing a checking account
class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber) {
        super(accountNumber);
    }
}

// Class representing the banking system
public class MzanziBankingSystem {
    private Scanner scanner;
    private Map<String, Account> accounts;
    private Account loggedInAccount;

    public MzanziBankingSystem() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        this.loggedInAccount = null;
    }

    public void displayWelcomeMessage() {
        System.out.println("Tables created successfully. Welcome to Mzanzi Banking System");
    }

    public void displayOptions() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    public int askUserChoice() {
        System.out.print("Please choose an option: ");
        return scanner.nextInt();
    }

    public void displayChosenOption(int option) {
        System.out.println("Option chosen: " + option);
    }

    public void register() {
        System.out.println("Registration process loading.....");
        // Code to register a new account
        System.out.println("Account registered successfully.");
        performTransactions();
    }

    public void login() {
        System.out.println("Login process...");
        // Code to log in
        loggedInAccount = accounts.get("example_account"); // Placeholder for account lookup
        System.out.println("Login successful.");
        performTransactions();

    }

    public void createNewAccount() {
        System.out.println("Creating new account...");
        // Code to create a new account
        Account newAccount = new SavingsAccount("new_account_number"); // Placeholder for account creation
        accounts.put(newAccount.accountNumber, newAccount);
        System.out.println("Account created successfully.");
    }

    public void viewExistingAccounts() {
        System.out.println("Viewing existing accounts...");
        // Code to display existing accounts
        for (String accountNumber : accounts.keySet()) {
            System.out.println("Account Number: " + accountNumber);
        }
    }

    public void performTransactions() {
        System.out.println("-- Perform Transactions --");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Return to Main Menu");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Current Balance: " + loggedInAccount.checkBalance());
                break;
            case 2:
                System.out.print("Enter deposit amount: ");
                double depositAmount = scanner.nextDouble();
                loggedInAccount.deposit(depositAmount);
                break;
            case 3:
                System.out.print("Enter withdrawal amount: ");
                double withdrawalAmount = scanner.nextDouble();
                loggedInAccount.withdraw(withdrawalAmount);
                break;
            case 4:
                System.out.print("Enter recipient account number: ");
                String recipientAccountNumber = scanner.next();
                System.out.print("Enter transfer amount: ");
                double transferAmount = scanner.nextDouble();
                Account recipientAccount = accounts.get(recipientAccountNumber);
                if (recipientAccount != null) {
                    loggedInAccount.transferFunds(recipientAccount, transferAmount);
                } else {
                    System.out.println("Recipient account not found.");
                }
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public void logout() {
        System.out.println("Logging out...");
        loggedInAccount = null;
    }

    public void run() {
        displayWelcomeMessage();
        while (true) {
            displayOptions();
            int choice = askUserChoice();
            switch (choice) {
                case 1:
                    displayChosenOption(1);
                    register();
                    break;
                case 2:
                    displayChosenOption(2);
                    login();
                    if (loggedInAccount != null) {
                        while (true) {
                            // displayMainMenu();
                            performTransactions();
                            System.out.print("Return to main menu? (yes/no): ");
                            String returnToMainMenu = scanner.next();
                            if (!returnToMainMenu.equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting the Mzanzi Banking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        MzanziBankingSystem bankingSystem = new MzanziBankingSystem();
        bankingSystem.run();
    }
}
