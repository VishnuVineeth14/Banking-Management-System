package accounts;

import java.time.LocalDateTime;

public class Transaction{
    private String transactionID = null;
    private double amount = 0;
    private LocalDateTime transactionTime = null;
    private String type = null;
    private Account account;
    public Transaction(String transactionID, double amount, LocalDateTime transactionTime, String type, Account account){
        this.transactionID = transactionID;
        this.transactionTime = transactionTime;
        this.type = type;
        this.amount = amount;
    }
    public String toString(){
        return "TransactionID: "+transactionID+", Type: "+type+", Amount: "+amount+", Date: "+transactionTime;
    }    
}