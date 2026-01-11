package accounts;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import bankexceptions.AccountFrozenException;
import bankexceptions.AccountNotFoundException;
import bankexceptions.InsufficientFundsException;
import bankexceptions.InvalidAmountException;

interface Freezable{
    boolean frozen = false;
    public void freeze();
    public void unfreeze();
    public boolean isFrozen();
}

interface Transactable{
    public void deposit(double amount) throws AccountFrozenException, InvalidAmountException;
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException, InsufficientFundsException, AccountFrozenException;
    public boolean transfer(Account target, double amount) throws InvalidAmountException, AccountFrozenException, InsufficientFundsException, AccountNotFoundException;
}

abstract public class Account implements Freezable, Transactable{
    private String accountNo;
    protected double balance;
    private boolean frozen;
    private String accountType;
    public List<Transaction> transactionList = new ArrayList<>(); 

    public Account(String accountNumber, double balance, String accountType) throws InvalidAmountException{
        if(balance < 0) throw new InvalidAmountException("Initial balance cannot be negative!!");
        this.accountNo = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.frozen = false;
    }
    public void deposit(double amount) throws AccountFrozenException, InvalidAmountException{
        if(frozen) throw new AccountFrozenException();
        if(amount <= 0) throw new InvalidAmountException("Deposit amount should be POSITIVE!!");
        balance += amount;
        transactionList.add(new Transaction(UUID.randomUUID().toString(), amount, LocalDateTime.now(), accountType, this));
    };
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException, AccountFrozenException{
        if(frozen) throw new AccountFrozenException();
        if(amount <= 0) throw new InvalidAmountException("Withdrawal amount must be POSITIVE!!");
        if(balance < amount) throw new InsufficientFundsException(balance, amount);
        balance -= amount;
        transactionList.add(new Transaction(UUID.randomUUID().toString(), amount, LocalDateTime.now(), accountType, this));
    }
    public boolean transfer(Account toAccount, double amount) throws InvalidAmountException, AccountFrozenException, InsufficientFundsException{
        if(toAccount == null) throw new IllegalArgumentException("Target Account cannot be null");
        try{
            this.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        }
        catch(InsufficientFundsException e){
            return false;
        }
        catch(InvalidAmountException e){
            System.out.println(e);
            return false;
        }
    }
    public double getBalance(){
        return balance;
    }
    public String getAccountNo(){
        return accountNo;
    }
    public String getAccountType(){
        return accountType;
    }
    public List<Transaction> getTransactions(){
        return transactionList;
    }
    public String getAccountInfo(){
        return "AccountNo: "+accountNo+" , Type: "+accountNo+" , Balance: "+balance+", Frozen: "+frozen;
    }
    public void freeze(){
        frozen = true;
    }
    public void unfreeze(){
        frozen = false;
    }
    public boolean isFrozen(){
        return frozen;
    }
}


