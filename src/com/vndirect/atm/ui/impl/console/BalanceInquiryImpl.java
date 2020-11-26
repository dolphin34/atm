package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.ui.BalanceInquiry;
import com.vndirect.atm.util.StringUtils;

import java.util.Date;

public class BalanceInquiryImpl extends AtmView implements BalanceInquiry {

    private static final BalanceInquiry INSTANCE = new BalanceInquiryImpl();

    public static BalanceInquiry getView() {
        return INSTANCE;
    }

    @Override
    public void displayBalanceInquiry() {
        AccountModel currentAccount = SESSION.getCurrentAccount();
        System.out.println(MULTI_DASH);
        System.out.println("Account number: " + currentAccount.getNumber());
        System.out.println("User: " + currentAccount.getName());
        System.out.println("Balance: " + StringUtils.amountToString(currentAccount.getAmount()));
        System.out.println("Time: " + StringUtils.dateToString(new Date()));

        showTwoNextAction(STRING_MENU, STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
    }
}
