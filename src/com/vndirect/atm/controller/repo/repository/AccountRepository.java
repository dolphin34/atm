package com.vndirect.atm.controller.repo.repository;

import com.vndirect.atm.controller.repo.entity.Account;
import java.util.List;

public interface AccountRepository {
    Account findByAccountNumber(String accountNumber);
    boolean updateInfoAccount(String accountNumber, long newAmount, int newTransactionId);
    List<Integer> getListTransactionId(String accountNumber);
}
