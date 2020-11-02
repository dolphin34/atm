package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.entity.Card;
import com.vndirect.atm.controller.repo.repository.CardRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryCardRepositoryImpl;
import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullException;

public class CardServiceImpl implements CardService {

    private static final CardRepository CARD_REPOSITORY = new InMemoryCardRepositoryImpl();

    @Override
    public CardModel findCardByNumber(String cardNumber) throws LockCardException, NullException {
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        if (card == null) {
            throw new NullException("Card does not exist!");
        }
        if (!card.isActive()) {
            throw new LockCardException();
        }
        return new CardModel(card.getNumber(), card.getName(), card.getAccountNumber());
    }

    @Override
    public boolean lockCard(String cardNumber) {
        return CARD_REPOSITORY.lockCard(cardNumber);
    }

    @Override
    public boolean checkPin(String cardNumber, String pin) {
        return CARD_REPOSITORY.checkPin(cardNumber, pin);
    }

    @Override
    public boolean pinChange(String cardNumber, String newPin) {
       return CARD_REPOSITORY.pinChange(cardNumber, newPin);
    }
}
