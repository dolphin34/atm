package com.vndirect.atm.service.serviceimpl;

import com.vndirect.atm.repo.entity.Account;
import com.vndirect.atm.repo.entity.Transaction;
import com.vndirect.atm.repo.repository.AccountRepository;
import com.vndirect.atm.repo.repository.TransactionRepository;
import com.vndirect.atm.repo.repository.impl.inmemory.AccountRepositoryImpl;
import com.vndirect.atm.repo.repository.impl.inmemory.TransactionRepositoryImpl;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private static final AccountRepository ACCOUNT_REPOSITORY = AccountRepositoryImpl.getInstance();
    private static final TransactionRepository TRANSACTION_REPOSITORY = TransactionRepositoryImpl.getInstance();

    @Override
    public AccountModel findAccountModelByNumber(String accountNumber) {
        AccountModel accountModel = null;
        Account account = ACCOUNT_REPOSITORY.findAccountByNumber(accountNumber);
        if (account != null) {
            List<Transaction> listTransaction = TRANSACTION_REPOSITORY.getListTransactionByListId(account.getListTransactionsId());
            List<TransactionModel> listTransactionModel = new ArrayList<>();
            for (Transaction tran : listTransaction) {
                TransactionModel.TransactionModelType transferType = null;
                if (tran.getTransType().getCode() == 1) {
                    transferType = TransactionModel.TransactionModelType.TRANSFER;
                }
                if (tran.getTransType().getCode() == 2) {
                    transferType = TransactionModel.TransactionModelType.CASH_WITHDRAWAL;
                }

                TransactionModel temp = new TransactionModel(transferType, tran.getAmount(), tran.getFee(), tran.getDate(), tran.getAccountNumberPerform(), tran.getAccountNumberTarget());
                listTransactionModel.add(temp);
            }
            accountModel = new AccountModel(accountNumber, account.getName(), account.getAmount(), listTransactionModel);
        }
        return accountModel;
    }

    @Override
    public AccountModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal){
        Transaction newTransaction = new Transaction(TRANSACTION_REPOSITORY.getSizeOfListTransaction() + 1, Transaction.TransactionType.CASH_WITHDRAWAL,
                amountWithdrawal, Constants.CASH_WITHDRAWAL_FEE, new Date(), accountModel.getNumber(), null);

        Account account = ACCOUNT_REPOSITORY.findAccountByNumber(accountModel.getNumber());
        account.addTransactionsId(newTransaction.getId());
        account.setAmount(account.getAmount() - (amountWithdrawal + Constants.CASH_WITHDRAWAL_FEE));

        boolean withdrawalSuccess = ACCOUNT_REPOSITORY.updateInfoAccount(account) && TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
        if (withdrawalSuccess) {
            accountModel = findAccountModelByNumber(account.getNumber());
        }
        return accountModel;
    }

    @Override
    public TransactionModel processTransfer(AccountModel transferAccount, AccountModel receivedAccount, long amountTransfer) {
        Transaction newTransaction = new Transaction(TRANSACTION_REPOSITORY.getSizeOfListTransaction() + 1, Transaction.TransactionType.TRANSFER,
                                        amountTransfer, Constants.TRANSFER_FEE, new Date(), transferAccount.getNumber(), receivedAccount.getNumber());

        Account sendAccount = ACCOUNT_REPOSITORY.findAccountByNumber(transferAccount.getNumber());
        sendAccount.setAmount(sendAccount.getAmount() - ( amountTransfer + Constants.TRANSFER_FEE));
        sendAccount.addTransactionsId(newTransaction.getId());

        Account targetAccount = ACCOUNT_REPOSITORY.findAccountByNumber(receivedAccount.getNumber());
        targetAccount.setAmount(targetAccount.getAmount() + amountTransfer);
        targetAccount.addTransactionsId(newTransaction.getId());

        boolean transferSuccess = ACCOUNT_REPOSITORY.updateInfoAccount(sendAccount)
                                  && ACCOUNT_REPOSITORY.updateInfoAccount(targetAccount)
                                  && TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
        if (!transferSuccess) {
            return null;
        }

        return new TransactionModel(TransactionModel.TransactionModelType.TRANSFER, newTransaction.getAmount(),
                newTransaction.getFee(), newTransaction.getDate(), newTransaction.getAccountNumberPerform(), newTransaction.getAccountNumberTarget());
    }
}
