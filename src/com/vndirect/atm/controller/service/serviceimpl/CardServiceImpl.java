package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.entity.Card;
import com.vndirect.atm.controller.repo.repository.CardRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryCardRepositoryImpl;
import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.model.CardModel;

public class CardServiceImpl implements CardService {

    private static final CardRepository CARD_REPOSITORY = new InMemoryCardRepositoryImpl();

    @Override
    public CardModel findCardByNumber(String cardNumber) {
        CardModel cardModel = null;
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        if (card != null) {
            cardModel = new CardModel(card.getNumber(), card.getName(), card.getAccountNumber(), card.isActive());
        }
        return cardModel;
    }

    @Override
    public boolean checkPin(String cardNumber, String pin) {
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        return card.getPin().equals(pin);
    }

    @Override
    public boolean lockCard(String cardNumber){
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        card.setActive(false);
        return CARD_REPOSITORY.updateInfoCard(card);
    }

    @Override
    public boolean pinChange(String cardNumber, String newPin) {
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        card.setPin(newPin);
        return CARD_REPOSITORY.updateInfoCard(card);
    }
}
