package com.vndirect.atm.ui;

import com.vndirect.atm.model.TransactionModel;

import java.util.List;
import java.util.Map;

public interface CashWithdrawal {

    void showOptionsOfCash();

    void enterOtherAmount();

    void processCashWithdrawal(long amount);

    void displayResult(TransactionModel transaction, List<Map.Entry<Integer, Integer>> result);
}

