package interfaces;
import java.util.List;
import accounts.*;
import bankexceptions.AccountNotFoundException;

public interface AccountViewer<T extends Account> {
    List<T> viewAccounts() throws AccountNotFoundException;

}
