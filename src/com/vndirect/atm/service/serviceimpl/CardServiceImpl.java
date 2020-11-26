package com.vndirect.atm.service.serviceimpl;

import com.vndirect.atm.repo.entity.Card;
import com.vndirect.atm.repo.repository.CardRepository;
import com.vndirect.atm.repo.repository.inmemoryimpl.InMemoryCardRepositoryImpl;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.model.CardModel;

public class CardServiceImpl implements CardService {

    private static final CardRepository CARD_REPOSITORY = new InMemoryCardRepositoryImpl();

    @Override
    public CardModel findCardByNumber(String cardNumber) {
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        if (card != null) {
            return new CardModel(card.getNumber(), card.getName(), card.getAccountNumber(), card.isActive());
        }
        return null;
    }

    @Override
    public boolean checkPin(CardModel cardModel, String pin) {
        return CARD_REPOSITORY.findCardByNumber(cardModel.getNumber()).getPin().equals(pin);
    }

    @Override
    public boolean lockCard(CardModel cardModel){
        Card card = CARD_REPOSITORY.findCardByNumber(cardModel.getNumber());
        card.setActive(false);
        return CARD_REPOSITORY.updateInfoCard(card);
    }

    @Override
    public boolean changePin(CardModel cardModel, String newPin) {
        Card card = CARD_REPOSITORY.findCardByNumber(cardModel.getNumber());
        card.setPin(newPin);
        return CARD_REPOSITORY.updateInfoCard(card);
    }
}
