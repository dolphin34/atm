package dal;

import dto.Account;
import java.util.List;

public interface AccountDAL {
    Account findByAccountNumber(String accountNumber);
    boolean updateInfoAccount(String accountNumber, long newAmount, int newTransactionId);
    List<Integer> getListTransactionId(String accountNumber);
}
