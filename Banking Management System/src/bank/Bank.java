package bank;

import bankexceptions.*;
import java.io.Serializable;
import java.util.*;

public class Bank implements Serializable {
    private String bankName;
    private String bankAddress;
    private List<Branch> branches = new ArrayList<>();

    public Bank(String bankName, String bankAddress) {
        this.bankName = bankName;
        this.bankAddress = bankAddress;
    }

    public void addBranch(Branch branch) { branches.add(branch); }
    public List<Branch> getBranches() { return branches; }

    public Branch selectBranch(String branchName) throws AccountNotFoundException {
        for (Branch branch : branches)
            if (branch.getBranchName().equalsIgnoreCase(branchName)) return branch;
        throw new AccountNotFoundException("Branch not found: " + branchName);
    }

    public user.Customer selectCustomer(Branch branch, String customerId) throws CustomerNotFoundException {
        for (user.Customer customer : branch.getCustomers())
            if (customer.getCustomerId().equals(customerId)) return customer;
        throw new CustomerNotFoundException("Customer not found: " + customerId);
    }

    public List<accounts.Transaction> viewBranchTransactions(Branch branch) { 
        return branch.getAllTransactions(); 
    }
    
    public String getBankName() { return bankName; }
    public String getBankAddress() { return bankAddress; }
}