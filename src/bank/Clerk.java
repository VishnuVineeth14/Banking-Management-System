package bank;

import accounts.Account;
import user.Customer;

public class Clerk {
    private String clerkId;
    private String name;
    
    public Clerk(String clerkId, String name) {
        this.clerkId = clerkId;
        this.name = name;
    }
    
    public Customer createCustomer(String customerId, String customerName, String address, Branch branch) {
        Customer newCustomer = new Customer(customerId, customerName, address, branch.getBranchName());
        branch.addCustomer(newCustomer);
        return newCustomer;
    }
    
    public void openAccountForCustomer(Customer customer, Account account, Branch branch) {
        customer.openAccount(account);
        branch.addAccount(account);
    }
    
    public String getClerkId() { return clerkId; }
    public String getName() { return name; }
}