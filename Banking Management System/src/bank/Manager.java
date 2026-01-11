package bank;

import accounts.Account;
import interfaces.AccountViewer;
import bankexceptions.*;
import java.util.*;

public class Manager implements AccountViewer<Account> {
    private String managerId;
    private String name;
    private Bank bank;
    
    public Manager(String managerId, String name, Bank bank) {
        this.managerId = managerId;
        this.name = name;
        this.bank = bank;
    }
    
    @Override
    public List<Account> viewAccounts() throws AccountNotFoundException {
        List<Account> all = new ArrayList<>();
        for (Branch branch : bank.getBranches()) all.addAll(branch.viewAccounts());
        if (all.isEmpty()) throw new AccountNotFoundException("No accounts found in the bank.");
        return all;
    }
    
    public void freezeAccount(Account account) {
        account.freeze();
        System.out.println("Account " + account.getAccountNo() + " has been frozen.");
    }
    
    public void unfreezeAccount(Account account) {
        account.unfreeze();
        System.out.println("Account " + account.getAccountNo() + " has been unfrozen.");
    }
    
    public String getManagerId() { return managerId; }
    public String getName() { return name; }
}