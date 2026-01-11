package bank;

import accounts.Account;
import bankexceptions.*;
import user.*;
import interfaces.AccountViewer;
import java.io.Serializable;
import java.util.*;

public class Branch implements AccountViewer<Account>, Serializable {
    private String branchName;
    private String location;
    private List<Account> accounts = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public Branch(String branchName, String location) {
        this.branchName = branchName;
        this.location = location;
    }

    public String getBranchName() { return branchName; }
    public String getLocation() { return location; }
    public void addAccount(Account account) { accounts.add(account); }
    public void addCustomer(Customer customer) { customers.add(customer); }

    public List<Customer> getCustomers() { return customers; }
    
    @Override
    public List<Account> viewAccounts() throws AccountNotFoundException {
        if (accounts.isEmpty()) throw new AccountNotFoundException("No accounts found in branch: " + branchName);
        return accounts;
    }

    public List<accounts.Transaction> getAllTransactions() {
        List<accounts.Transaction> allTransactions = new ArrayList<>();
        for (Account acc : accounts) allTransactions.addAll(acc.getTransactions());
        return allTransactions;
    }
    
    public Customer findCustomer(String customerId) throws CustomerNotFoundException {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException("Customer not found: " + customerId);
    }
    
    public Account findAccount(String accountNo) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getAccountNo().equals(accountNo)) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account not found: " + accountNo);
    }
}