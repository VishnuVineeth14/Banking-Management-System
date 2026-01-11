package bankexceptions;

public class CustomerNotFoundException extends Exception{
    String customerID;
    public CustomerNotFoundException(String customerID){
        this.customerID = customerID;
    }
    public String toString(){
        return "CustomerNotFoundException: The customer with ID ("+customerID+") not found!!";
    }
}

