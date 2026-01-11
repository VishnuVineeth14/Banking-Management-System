package user;

import accounts.Account;
import bankexceptions.AccountNotFoundException;
import interfaces.AccountViewer;
import java.io.Serializable;
import java.util.*;

public class Customer implements AccountViewer<Account>, Serializable {
    private String customerId;
    private String name;
    private String address;
    private String branch; // Added branch field
    private List<Account> accounts = new ArrayList<>();

    public Customer(String customerId, String name, String address, String branch) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.branch = branch;
    }

    // For backward compatibility (if needed)
    public Customer(String customerId, String name, String address) {
        this(customerId, name, address, ""); // default branch empty
    }

    public void openAccount(Account account) { 
        accounts.add(account); 
    }
    
    @Override
    public List<Account> viewAccounts() throws AccountNotFoundException {
        if (accounts.isEmpty()) throw new AccountNotFoundException("No accounts found for customer: " + name);
        return accounts;
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getBranch() { return branch; }

    public List<accounts.Transaction> viewTransactionHistory(Account account) throws AccountNotFoundException {
        if (!accounts.contains(account)) throw new AccountNotFoundException("Account not owned by this customer.");
        return account.getTransactions();
    }
    
    public String getAccountDetails(Account account) throws AccountNotFoundException {
        if (!accounts.contains(account)) throw new AccountNotFoundException("Account not owned by this customer.");
        return account.getAccountInfo();
    }
    
    public Account findAccount(String accountNo) throws AccountNotFoundException {
        for (Account acc : accounts) {
            if (acc.getAccountNo().equals(accountNo)) {
                return acc;
            }
        }
        throw new AccountNotFoundException("Account not found: " + accountNo);
    }
    
    @Override
    public String toString() {
        return "CustomerID: " + customerId + ", Name: " + name + ", Address: " + address + ", Branch: " + branch + ", Accounts: " + accounts.size();
    }
}