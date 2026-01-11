package bank;

import accounts.*;
import bankexceptions.*;
import database.DataStorage;
import java.util.*;
import java.util.stream.*;
import user.Customer;

public class BankConsole {
    private Scanner scanner;
    private DataStorage dataStorage;
    private Bank bank;
    private Clerk currentClerk;
    private Manager currentManager;

    public BankConsole(DataStorage dataStorage) {
        this.scanner = new Scanner(System.in);
        this.dataStorage = dataStorage;
        this.bank = dataStorage.getBank();
        this.currentClerk = new Clerk("CLK001", "John Doe");
        this.currentManager = new Manager("MGR001", "Jane Smith", bank);
    }

    public void start() {
        System.out.println("\n=== BANK CONSOLE ===");
        while (true) {
            System.out.println("\n1. Clerk Operations");
            System.out.println("2. Manager Operations");
            System.out.println("3. View Bank Summary");
            System.out.println("4. Search By");
            System.out.println("5. Exit Bank Console");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    clerkMenu();
                    break;
                case 2:
                    managerMenu();
                    break;
                case 3:
                    viewBankSummary();
                    break;
                case 4:
                    searchByMenu();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private Branch selectBranchPrompt() {
        System.out.print("Enter Branch Name: ");
        String branchName = scanner.nextLine();
        Optional<Branch> branchOpt = bank.getBranches().stream()
            .filter(b -> b.getBranchName().equalsIgnoreCase(branchName))
            .findFirst();
        if (branchOpt.isPresent()) {
            return branchOpt.get();
        } else {
            Branch newBranch = new Branch(branchName, "Unknown");
            bank.addBranch(newBranch);
            return newBranch;
        }
    }

    private void clerkMenu() {
        Branch branch = selectBranchPrompt();
        while (true) {
            System.out.println("\n=== CLERK OPERATIONS (" + branch.getBranchName() + ") ===");
            System.out.println("1. Create New Customer");
            System.out.println("2. Open New Account");
            System.out.println("3. View Customer Details");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    createCustomer(branch);
                    break;
                case 2:
                    openAccount(branch);
                    break;
                case 3:
                    viewCustomerDetails(branch);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void createCustomer(Branch branch) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();
            System.out.print("Enter Customer Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            Customer customer = currentClerk.createCustomer(customerId, name, address, branch);
            dataStorage.addCustomer(customer);
            dataStorage.saveAllData();

            System.out.println("Customer created successfully: " + customer);
        } catch (Exception e) {
            System.out.println("Sorry, Failed to create Customer!!");
        }
    }

    private void openAccount(Branch branch) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();
            
            Customer customer = dataStorage.findCustomer(customerId);
            
            System.out.println("Select Account Type:");
            System.out.println("1. Savings Account");
            System.out.println("2. Current Account");
            int accType = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();
            System.out.print("Enter Initial Balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            
            Account account;
            if (accType == 1) {
                System.out.print("Enter Interest Rate: ");
                double interestRate = scanner.nextDouble();
                scanner.nextLine();
                account = new SavingsAccount(accNo, balance, interestRate);
            } else {
                System.out.print("Enter Overdraft Limit: ");
                double overdraft = scanner.nextDouble();
                scanner.nextLine();
                account = new CurrentAccount(accNo, balance, overdraft);
            }
            
            currentClerk.openAccountForCustomer(customer, account, branch);
            dataStorage.addAccount(account);
            dataStorage.saveAllData();
            
            System.out.println("Account opened successfully: " + account.getAccountInfo());
        } catch (Exception e) {
            System.out.println("Sorry, Failed to Create Account");
        }
    }

    private void viewCustomerDetails(Branch branch) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();

            // Only show customers in this branch
            Customer customer = branch.getCustomers().stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

            System.out.println("\n=== CUSTOMER DETAILS ===");
            System.out.println(customer);

            List<Account> accounts = customer.viewAccounts();
            for (Account acc : accounts) {
                System.out.println("  - " + acc.getAccountInfo());
            }
        } catch (CustomerNotFoundException | AccountNotFoundException e) {
            System.out.println(e);
        }
    }

    private void managerMenu() {
        Branch branch = selectBranchPrompt();
        while (true) {
            System.out.println("\n=== MANAGER OPERATIONS (" + branch.getBranchName() + ") ===");
            System.out.println("1. View All Accounts");
            System.out.println("2. Freeze Account");
            System.out.println("3. Unfreeze Account");
            System.out.println("4. View Branch Transactions");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllAccounts(branch);
                    break;
                case 2:
                    freezeAccount(branch);
                    break;
                case 3:
                    unfreezeAccount(branch);
                    break;
                case 4:
                    viewBranchTransactions(branch);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void viewAllAccounts(Branch branch) {
        try {
            List<Account> accounts = branch.viewAccounts();
            System.out.println("\n=== ALL ACCOUNTS in " + branch.getBranchName() + " ===");
            for (Account acc : accounts) {
                System.out.println(acc.getAccountInfo());
            }
        } catch (AccountNotFoundException e) {
            System.out.println(e);
        }
    }

    private void freezeAccount(Branch branch) {
        try {
            System.out.print("Enter Account Number to freeze: ");
            String accNo = scanner.nextLine();
            
            Account account = branch.findAccount(accNo);
            currentManager.freezeAccount(account);
            dataStorage.saveAllData();
        } catch (Exception e) {
            System.out.println("Failed to freeze the account");
        }
    }

    private void unfreezeAccount(Branch branch) {
        try {
            System.out.print("Enter Account Number to unfreeze: ");
            String accNo = scanner.nextLine();
            
            Account account = branch.findAccount(accNo);
            currentManager.unfreezeAccount(account);
            dataStorage.saveAllData();
        } catch (Exception e) {
            System.out.println("Failed to unfreeze the account");
        }
    }

    private void viewBranchTransactions(Branch branch) {
        try {
            List<accounts.Transaction> transactions = branch.getAllTransactions();
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for branch: " + branch.getBranchName());
            } else {
                System.out.println("\n=== TRANSACTIONS FOR " + branch.getBranchName() + " ===");
                for (accounts.Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("Sorry, Failed to fetch the data!!");
        }
    }

    private void viewBankSummary() {
        System.out.println("\n=== BANK SUMMARY ===");
        System.out.println("Bank: " + bank.getBankName());
        System.out.println("Branches: " + bank.getBranches().size());
        System.out.println("Total Customers: " + dataStorage.getAllCustomers().size());
        System.out.println("Total Accounts: " + dataStorage.getAllAccounts().size());
        
    }

    private void searchByMenu() {
        List<Customer> customers = dataStorage.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println("\n=== ALL CUSTOMERS ===");
        customers.forEach(System.out::println);

        System.out.println("\nSearch By:");
        System.out.println("1. Customer ID");
        System.out.println("2. Name");
        System.out.println("3. Branch");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Customer> result = new ArrayList<>();
        switch (choice) {
            case 1:
                System.out.print("Enter Customer ID: ");
                String id = scanner.nextLine();
                result = customers.stream()
                    .filter(c -> c.getCustomerId().equalsIgnoreCase(id))
                    .collect(Collectors.toList());
                break;
            case 2:
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                result = customers.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
                break;
            case 3:
                System.out.print("Enter Branch: ");
                String branch = scanner.nextLine();
                result = customers.stream()
                    .filter(c -> c.getBranch().equalsIgnoreCase(branch))
                    .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid option!");
                return;
        }
        if (result.isEmpty()) {
            System.out.println("No matching customers found.");
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            result.forEach(System.out::println);
        }
    }
}