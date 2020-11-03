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
    public boolean updateInfoCard(Card updateCard) {
        boolean success = false;
        for (Card c : Data.listCard) {
            if (c.getNumber().equals(updateCard.getNumber())) {
                c.setPin(updateCard.getPin());
                c.setActive(updateCard.isActive());
                success = true;
            }
        }
        return success;
    }
}
