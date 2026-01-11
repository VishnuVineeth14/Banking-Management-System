package database;

import accounts.Account;
import bank.Bank;
import bank.Branch;
import bankexceptions.*;
import java.io.*;
import java.util.*;
import user.Customer;

public class DataStorage {
    private Bank bank;
    private List<Customer> allCustomers;
    private List<Account> allAccounts;
    private static final String CUSTOMER_CSV = "customers.csv";

    public DataStorage() {
        loadAllData();
    }

    public void loadAllData() {
        // No file I/O, just initialize in-memory data
        bank = new Bank("Global Bank", "123 Main Street");
        Branch mainBranch = new Branch("Main Branch", "123 Main Street");
        bank.addBranch(mainBranch);
        allCustomers = new ArrayList<>();
        allAccounts = new ArrayList<>();
        loadCustomersFromCSV();
        System.out.println("Data loaded: " + allCustomers.size() + " customers, " + allAccounts.size() + " accounts");
    }

    public void saveAllData() {
        saveCustomersToCSV();
        // No-op for accounts/bank
    }

    private void loadCustomersFromCSV() {
        File file = new File(CUSTOMER_CSV);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    Customer c = new Customer(parts[0], parts[1], parts[2], parts[3]);
                    allCustomers.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers from CSV: " + e.getMessage());
        }
    }

    private void saveCustomersToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CUSTOMER_CSV))) {
            for (Customer c : allCustomers) {
                pw.println(String.join(",", c.getCustomerId(), c.getName(), c.getAddress(), c.getBranch()));
            }
        } catch (IOException e) {
            System.out.println("Error saving customers to CSV: " + e.getMessage());
        }
    }

    public Customer findCustomer(String customerId) throws CustomerNotFoundException {
        return allCustomers.stream()
            .filter(c -> c.getCustomerId().equals(customerId))
            .findFirst()
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    public Account findAccount(String accountNo) throws AccountNotFoundException {
        return allAccounts.stream()
            .filter(a -> a.getAccountNo().equals(accountNo))
            .findFirst()
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNo));
    }

    public void addCustomer(Customer customer) {
        if (!allCustomers.contains(customer)) {
            allCustomers.add(customer);
            saveCustomersToCSV();
        }
    }

    public void addAccount(Account account) {
        if (!allAccounts.contains(account)) {
            allAccounts.add(account);
        }
    }

    public List<Customer> getAllCustomers() { return new ArrayList<>(allCustomers); }
    public List<Account> getAllAccounts() { return new ArrayList<>(allAccounts); }
    public Bank getBank() { return bank; }
}