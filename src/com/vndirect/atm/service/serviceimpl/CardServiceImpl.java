package com.vndirect.atm.service.serviceimpl;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.entity.Card;
import com.vndirect.atm.repo.CardRepository;
import com.vndirect.atm.repo.impl.inmemory.CardRepositoryImpl;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.model.CardModel;

import java.util.Optional;

public class CardServiceImpl implements CardService {

    private static final CardRepository CARD_REPOSITORY = CardRepositoryImpl.getInstance();

    @Override
    public Optional<CardModel> findByNumber(String cardNumber) {
        Optional<Card> card = CARD_REPOSITORY.findByNumber(cardNumber);
        return card.map(value -> new CardModel(value.getNumber(), value.getName(), value.getAccountNumber(), value.isActive()));

    }

    @Override
    public void checkPin(CardModel cardModel, String pin) throws PinWrongException {
        Optional<Card> card = CARD_REPOSITORY.findByNumber(cardModel.getNumber());
        if (card.isPresent() && !card.get().getPin().equals(pin)) {
            throw new PinWrongException();
        }
    }

    @Override
    public void lockCard(CardModel cardModel) throws FailActionException {
        Optional<Card> card = CARD_REPOSITORY.findByNumber(cardModel.getNumber());
        if (card.isPresent()) {
            card.get().setActive(false);
            CARD_REPOSITORY.updateInfo(card.get());
        } else {
            throw new FailActionException();
        }
    }

    @Override
    public void changePin(CardModel cardModel, String newPin) throws FailActionException {
        Optional<Card> card = CARD_REPOSITORY.findByNumber(cardModel.getNumber());
        if (card.isPresent()) {
            card.get().setPin(newPin);
            CARD_REPOSITORY.updateInfo(card.get());
        } else {
            throw new FailActionException();
        }
    }
}
