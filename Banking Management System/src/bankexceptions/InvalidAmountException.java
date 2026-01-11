package bankexceptions;

public class InvalidAmountException extends Exception{
    String message;
    public InvalidAmountException(String message){
        this.message = message;
    }
    public String toString(){
        return "InvalidAmountException: "+message;
    }
}
