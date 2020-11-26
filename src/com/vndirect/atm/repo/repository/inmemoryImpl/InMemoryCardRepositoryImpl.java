package com.vndirect.atm.repo.repository.inmemoryimpl;

import com.vndirect.atm.repo.entity.Card;
import com.vndirect.atm.repo.repository.CardRepository;
import com.vndirect.atm.util.Constants;

public class InMemoryCardRepositoryImpl implements CardRepository {

    @Override
    public Card findCardByNumber(String cardNumber) {
        Card card = null;
        for (Card c : Constants.DATA.getListCard()) {
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
        for (Card c : Constants.DATA.getListCard()) {
            if (c.getNumber().equals(updateCard.getNumber())) {
                c.setPin(updateCard.getPin());
                c.setActive(updateCard.isActive());
                success = true;
            }
        }
        return success;
    }
}
