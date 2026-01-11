package user;

import accounts.Account;
import accounts.SavingsAccount;
import bankexceptions.*;
import database.DataStorage;
import java.util.*;

public class CustomerConsole {
    private Scanner scanner;
    private DataStorage dataStorage;
    private Customer currentCustomer;

    public CustomerConsole(DataStorage dataStorage) {
        this.scanner = new Scanner(System.in);
        this.dataStorage = dataStorage;
    }

    public void start() {
        System.out.println("\n=== CUSTOMER CONSOLE ===");
        
        while (true) {
            System.out.println("\n1. Login as Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Exit Customer Console");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    loginCustomer();
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void loginCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        
        try {
            currentCustomer = dataStorage.findCustomer(customerId);
            customerMenu();
        } catch (CustomerNotFoundException e) {
            System.out.println(e);
        }
    }

    private void viewAllCustomers() {
        List<Customer> customers = dataStorage.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("\n=== ALL CUSTOMERS ===");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n=== Welcome " + currentCustomer.getName() + " ===");
            System.out.println("1. View Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Calculate Interest (Savings Only)");
            System.out.println("7. Logout");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (choice) {
                    case 1:
                        viewAccounts();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        viewTransactionHistory();
                        break;
                    case 6:
                        calculateInterest();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a valid choice from 1 to 7");
            }
        }
    }

    private void viewAccounts() {
        try {
            List<Account> accounts = currentCustomer.viewAccounts();
            System.out.println("\n=== YOUR ACCOUNTS ===");
            for (Account acc : accounts) {
                System.out.println(acc.getAccountInfo());
            }
        } catch (AccountNotFoundException e) {
            System.out.println(e);
        }
    }

    private void deposit() {
        try {
            System.out.print("Enter account number: ");
            String accNo = scanner.nextLine();
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            Account account = currentCustomer.findAccount(accNo);
            account.deposit(amount);
            dataStorage.saveAllData();
            System.out.println("Deposit successful! New balance: " + account.getBalance());
        } catch (InvalidAmountException | AccountNotFoundException | AccountFrozenException e) {
            System.out.println(e);
        }
    }

    private void withdraw() {
        try {
            System.out.print("Enter account number: ");
            String accNo = scanner.nextLine();
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            Account account = currentCustomer.findAccount(accNo);
            account.withdraw(amount);
            dataStorage.saveAllData();
            System.out.println("Withdrawal successful! New balance: " + account.getBalance());
        } catch (InsufficientFundsException | InvalidAmountException | AccountFrozenException | AccountNotFoundException e) {
            System.out.println(e);
        }
    }

    private void transfer() {
        try {
            System.out.print("Enter your account number: ");
            String fromAccNo = scanner.nextLine();
            System.out.print("Enter target account number: ");
            String toAccNo = scanner.nextLine();
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            Account fromAccount = currentCustomer.findAccount(fromAccNo);
            Account toAccount = dataStorage.findAccount(toAccNo);
            
            fromAccount.transfer(toAccount, amount);
            dataStorage.saveAllData();
            System.out.println("Transfer successful!");
        } catch (AccountFrozenException | AccountNotFoundException | InvalidAmountException | InsufficientFundsException  e) {
            System.out.println(e);
        }
    }

    private void viewTransactionHistory() {
        try {
            System.out.print("Enter account number: ");
            String accNo = scanner.nextLine();
            
            Account account = currentCustomer.findAccount(accNo);
            List<accounts.Transaction> transactions = account.getTransactions();
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.println("\n=== TRANSACTION HISTORY ===");
                for (accounts.Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.println(e);
        }
    }

    private void calculateInterest() {
        try {
            System.out.print("Enter savings account number: ");
            String accNo = scanner.nextLine();
            
            Account account = currentCustomer.findAccount(accNo);
            if (account instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) account;
                savings.calculateInterest();
                dataStorage.saveAllData();
                System.out.println("Interest calculated! New balance: " + savings.getBalance());
            } else {
                System.out.println("This is not a savings account.");
            }
        } catch (AccountNotFoundException | InvalidAmountException | AccountFrozenException e) {
            System.out.println(e);
        }
    }
}