package com.vndirect.atm.repo;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.entity.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findOneByNumber(String accountNumber);

    void update(Account updateAccount) throws FailActionException;
}
