package accounts;
import java.time.LocalDateTime;
import java.util.UUID;

import bankexceptions.*;
public class CurrentAccount extends Account{
    private double overdraftLimit;
    public CurrentAccount(String accountNo, double balance, double overdraftLimit) throws InvalidAmountException{
        super(accountNo, balance, "Current Account");
        this.overdraftLimit = overdraftLimit;
    }

    public void withdraw(double amount) throws InvalidAmountException, AccountFrozenException, InsufficientFundsException {
        if (isFrozen()) throw new AccountFrozenException();
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (balance + overdraftLimit < amount) throw new InsufficientFundsException("Overdraft limit exceeded");
        balance -= amount;
        transactionList.add(new Transaction(UUID.randomUUID().toString(), amount, LocalDateTime.now(), "Current Account", this));
    }

    public boolean checkOverdraft(double amount){
        if(amount > overdraftLimit)
            return false;
        return true;
    }
    public double getOverdraftLimit(){
        return overdraftLimit;
    }
}
