package com.vndirect.atm.ui;

import java.util.List;
import java.util.Map;

public interface CashWithdrawal {

    void showOptionsOfCash();
    void enterOtherAmount();
    void processCashWithdrawal(long amount);
    void displayResult(List<Map.Entry<Integer, Integer>> result);
}

