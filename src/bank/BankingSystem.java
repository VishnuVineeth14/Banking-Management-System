package bank;

import accounts.*;
import database.DataStorage;
import java.util.*;
import user.Customer;
import user.CustomerConsole;

public class BankingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static DataStorage dataStorage;
    private static BankConsole bankConsole;
    private static CustomerConsole customerConsole;

    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM STARTING ===");
        System.out.println("Loading data from files...");
        
        // Initialize data storage and load data
        dataStorage = new DataStorage();
        bankConsole = new BankConsole(dataStorage);
        customerConsole = new CustomerConsole(dataStorage);
        
        // Create sample data if none exists
        if (dataStorage.getAllCustomers().isEmpty()) {
            createSampleData();
        }
        int choice = 0;
        // Main menu loop
        while(true){
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Bank Console (Staff)");
            System.out.println("2. Customer Console");
            System.out.println("3. Exit System");
            System.out.print("Choose option: ");
            try{
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        bankConsole.start();
                        break;
                    case 2:
                        customerConsole.start();
                        break;
                    case 3:
                        dataStorage.saveAllData();
                        System.out.println("Data saved. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Please Enter choice from 1 to 3");
            }
        }
    }

    private static void createSampleData() {
        try {
            System.out.println("Creating sample data...");
            
            Bank bank = dataStorage.getBank();
            Branch mainBranch = bank.getBranches().get(0);
            
            // Create clerk and manager
            Clerk clerk = new Clerk("CLK001", "John Doe");
            // Create sample customers (pass branch)
            Customer customer1 = clerk.createCustomer("CUST001", "Alice Johnson", "123 Elm St", mainBranch);
            Customer customer2 = clerk.createCustomer("CUST002", "Bob Brown", "456 Oak St", mainBranch);
            
            // Create sample accounts
            SavingsAccount acc1 = new SavingsAccount("ACC001", 1000.0, 2.5);
            CurrentAccount acc2 = new CurrentAccount("ACC002", 500.0, 200.0);
            SavingsAccount acc3 = new SavingsAccount("ACC003", 2000.0, 3.0);
            
            // Assign accounts to customers
            clerk.openAccountForCustomer(customer1, acc1, mainBranch);
            clerk.openAccountForCustomer(customer1, acc2, mainBranch);
            clerk.openAccountForCustomer(customer2, acc3, mainBranch);
            
            // Add to data storage
            dataStorage.addCustomer(customer1);
            dataStorage.addCustomer(customer2);
            dataStorage.addAccount(acc1);
            dataStorage.addAccount(acc2);
            dataStorage.addAccount(acc3);
            
            // Perform some sample transactions
            acc1.deposit(500.0);
            acc2.withdraw(100.0);
            acc1.transfer(acc3, 200.0);
            
            dataStorage.saveAllData();
            System.out.println("Sample data created successfully!");
            
        } catch (Exception e) {
            System.out.println("Error creating sample data ");
        }
    }
}