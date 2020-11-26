package com.vndirect.atm.session;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.CardModel;

public class Session {

    private  CardModel currentCard;
    private  AccountModel currentAccount;

    public CardModel getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CardModel currentCard) {
        this.currentCard = currentCard;
    }

    public AccountModel getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(AccountModel currentAccount) {
        this.currentAccount = currentAccount;
    }
}
