package com.vndirect.atm.repo.repository.impl.inmemory;

import com.vndirect.atm.repo.entity.Account;
import com.vndirect.atm.repo.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private static final AccountRepositoryImpl INSTANCE = new AccountRepositoryImpl();
    private static final List<Account> ACCOUNT_LIST = new ArrayList<>();

    static {
        // initialized list account
        Account a1 = new Account("11111", "VU THI HOA", 50_000_000, new ArrayList<>());
        Account a2 = new Account("22222", "BUI VAN HUNG", 3_000_000, new ArrayList<>());
        Account a3 = new Account("33333", "NGUYEN VAN ANH", 30_000_000, new ArrayList<>());
        Account a4 = new Account("44444", "VU THI THU", 100_000_000, new ArrayList<>());
        Account a5 = new Account("55555", "HA VAN TU", 10_000_000, new ArrayList<>());
        ACCOUNT_LIST.add(a1);
        ACCOUNT_LIST.add(a2);
        ACCOUNT_LIST.add(a3);
        ACCOUNT_LIST.add(a4);
        ACCOUNT_LIST.add(a5);
    }

    public static AccountRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        Account account = null;
        for (Account a : ACCOUNT_LIST) {
            if (a.getNumber().equals(accountNumber)) {
                account = a;
                break;
            }
        }
        return account;
    }

    @Override
    public boolean updateInfoAccount(Account updateAccount) {
        boolean success = false;
        for (Account a : ACCOUNT_LIST) {
            if (a.getNumber().equals(updateAccount.getNumber())) {
                a.setAmount(updateAccount.getAmount());
                a.setListTransactionId(updateAccount.getListTransactionsId());
                success = true;
                break;
            }
        }
        return success;
    }
}
