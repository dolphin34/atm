package com.vndirect.atm.controller.repo.repository.inmemoryImpl;

import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.repo.repository.AccountRepository;
import com.vndirect.atm.controller.repo.entity.Account;

import java.util.List;

public class InMemoryAccountRepositoryImpl implements AccountRepository {
    @Override
    public Account findByAccountNumber(String accountNumber) {
        Account account = null;
        for (Account a : Data.listAccount) {
            if (a.getNumber().equals(accountNumber)) {
                account = a;
                break;
            }
        }
        return account;
    }

    @Override
    public boolean updateInfoAccount(String accountNumber, long newAmount, int newTransactionId) {
        boolean success = false;
        for (Account a : Data.listAccount) {
            if (a.getNumber().equals(accountNumber)) {
                a.setAmount(newAmount);
                a.addTransaction(newTransactionId);
                success = true;
                break;
            }
        }
        return success;
    }

    @Override
    public List<Integer> getListTransactionId(String accountNumber) {
        List<Integer> listTransId = null;
        for (Account a : Data.listAccount) {
            if (a.getNumber().equals(accountNumber)) {
                listTransId = a.getListTransactionsId();
            }
        }
        return listTransId;
    }


}
