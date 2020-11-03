package com.vndirect.atm.controller.repo.repository;

import com.vndirect.atm.controller.repo.entity.Account;

public interface AccountRepository {

    Account findByAccountNumber(String accountNumber);
    boolean updateInfoAccount(Account updateAccount);
}
