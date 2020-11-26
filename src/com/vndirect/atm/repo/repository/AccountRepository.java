package com.vndirect.atm.repo.repository;

import com.vndirect.atm.repo.entity.Account;

public interface AccountRepository {

    Account findAccountByNumber(String accountNumber);
    boolean updateInfoAccount(Account updateAccount);
}
