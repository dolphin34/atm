package com.vndirect.atm.controller.repo.repository.inmemoryImpl;

import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.repo.repository.CardRepository;
import com.vndirect.atm.controller.repo.entity.Card;

public class InMemoryCardRepositoryImpl implements CardRepository {
    @Override
    public Card findCardByNumber(String cardNumber) {
        Card card = null;
        for (Card c : Data.listCard) {
            if (c.getNumber().equals(cardNumber)) {
                card = c;
                break;
            }
        }
        return card;
    }

    @Override
    public boolean checkPin(String cardNumber, String pin) {
        for (Card c : Data.listCard) {
            if (c.getNumber().equals(cardNumber) && c.getPin().equals(pin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean lockCard(String cardNumber) {
        for (Card c : Data.listCard) {
            if (c.getNumber().equals(cardNumber)) {
                c.setActive(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pinChange(String cardNumber, String newPIN) {
        boolean success = false;
        for (Card c : Data.listCard) {
            if (c.getNumber().equals(cardNumber)) {
                c.setPin(newPIN);
                success = true;
                break;
            }
        }
        return success;
    }
}
