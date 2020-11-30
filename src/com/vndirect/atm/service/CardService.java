package com.vndirect.atm.service;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.model.CardModel;

import java.util.Optional;

public interface CardService {

    Optional<CardModel> findByNumber(String cardNumber);

    void checkPin(CardModel cardModel, String pin) throws PinWrongException;

    void lockCard(CardModel cardModel) throws FailActionException;

    void changePin(CardModel cardModel, String newPin) throws FailActionException;
}
