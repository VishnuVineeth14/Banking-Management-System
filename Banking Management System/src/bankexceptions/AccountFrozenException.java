package bankexceptions;

public class AccountFrozenException extends Exception{
    public String toString(){
        return "AccountFrozenException: Your account is frozen!!\nCan't do any Transactions with Frozen Account";
    }
}
