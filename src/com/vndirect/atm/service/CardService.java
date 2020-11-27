package com.vndirect.atm.service;

import com.vndirect.atm.service.model.CardModel;

public interface CardService {

    CardModel findCardByNumber(String cardNumber);
    boolean checkPin(CardModel cardModel, String pin);
    boolean lockCard(CardModel cardModel);
    boolean changePin(CardModel cardModel, String newPin);
}
