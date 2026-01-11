package accounts;

import bankexceptions.*;

public class SavingsAccount extends Account {
    private double interestRate;
    
    public SavingsAccount(String accountNo, double balance, double interestRate) throws InvalidAmountException {
        super(accountNo, balance, "Savings");
        this.interestRate = interestRate;
    }
    
    public void calculateInterest() throws InvalidAmountException, AccountFrozenException {
        double interest = balance * interestRate / 100;
        deposit(interest);
    }
    
    public double getInterestRate() { return interestRate; }
}