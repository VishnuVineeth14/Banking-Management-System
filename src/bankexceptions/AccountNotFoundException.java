package bankexceptions;

public class AccountNotFoundException extends Exception{
    String message;
    public AccountNotFoundException(String message){
        this.message = message;
    }
    public String toString(){
        return "AccountNotFoundException: "+message;
    }
}
