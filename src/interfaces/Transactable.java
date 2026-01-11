package interfaces;
import bankexceptions.*;
import accounts.Account;

public interface Transactable {
    void deposit(double amount) throws InvalidAmountException, AccountFrozenException;
    void withdraw(double amount) throws InvalidAmountException, AccountFrozenException, InsufficientFundsException;
    void transfer(Account target, double amount) throws InvalidAmountException, AccountFrozenException, InsufficientFundsException;
}
