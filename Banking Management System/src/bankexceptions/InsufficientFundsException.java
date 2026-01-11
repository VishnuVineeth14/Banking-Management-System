package bankexceptions;
public class InsufficientFundsException extends Exception{
    double balance;
    double withdrawAmount;
    String message;
    public InsufficientFundsException(double balance, double withdrawAmount){
        this.balance = balance;
    }
    public InsufficientFundsException(String message) {
        this.message = message;
    }

    public String toString(){
        return "InsufficientFundsException: Sorry Unable to withdraw Rs."+withdrawAmount+" as your balance is: Rs."+balance+"\n Need Rs."+(withdrawAmount-balance)+" more to withdraw";
    }
}
