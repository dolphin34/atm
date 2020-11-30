package com.vndirect.atm.repo.impl.inmemory;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.entity.Account;
import com.vndirect.atm.repo.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    private static final AccountRepositoryImpl INSTANCE = new AccountRepositoryImpl();

    private static final List<Account> ACCOUNTS = new ArrayList<>();

    static {
        // initialized list account
        Account a1 = new Account("11111", "VU THI HOA", 50_000_000, new ArrayList<>());
        Account a2 = new Account("22222", "BUI VAN HUNG", 3_000_000, new ArrayList<>());
        Account a3 = new Account("33333", "NGUYEN VAN ANH", 30_000_000, new ArrayList<>());
        Account a4 = new Account("44444", "VU THI THU", 100_000_000, new ArrayList<>());
        Account a5 = new Account("55555", "HA VAN TU", 10_000_000, new ArrayList<>());
        ACCOUNTS.add(a1);
        ACCOUNTS.add(a2);
        ACCOUNTS.add(a3);
        ACCOUNTS.add(a4);
        ACCOUNTS.add(a5);
    }

    public static AccountRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Account> findByNumber(String accountNumber) {
        return ACCOUNTS.stream()
                .filter(a -> a.getNumber().equals(accountNumber))
                .findFirst();
    }

    @Override
    public void updateInfo(Account updateAccount) throws FailActionException {
        Optional<Account> account = findByNumber(updateAccount.getNumber());
        if (account.isPresent()) {
            account.get().setAmount(updateAccount.getAmount());
            account.get().setTransactionIds(updateAccount.getTransactionIds());
        } else {
            throw new FailActionException();
        }
    }
}
