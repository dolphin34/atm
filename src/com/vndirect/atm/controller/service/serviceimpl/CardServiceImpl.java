package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.repository.CardRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryCardRepositoryImpl;
import com.vndirect.atm.controller.repo.entity.Card;
import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.userinterface.consoleviewImpl.ConsoleViewImpl;
import com.vndirect.atm.userinterface.View;

public class CardServiceImpl implements CardService {

    public static CardModel currentCard;

    private static final View VIEW = new ConsoleViewImpl();
    private static final CardRepository CARD_REPOSITORY = new InMemoryCardRepositoryImpl();
    private static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl();

    @Override
    public CardModel findCardByNumber(String cardNumber) throws LockCardException {
        CardModel cardModel = null;
        Card card = CARD_REPOSITORY.findCardByNumber(cardNumber);
        if (card != null) {
            if (!card.isActive()) {
                throw new LockCardException();
            }
            cardModel = new CardModel(card.getNumber(), card.getName());
        }
        currentCard = cardModel;
        return cardModel;
    }

    @Override
    public boolean lockCard(String cardNumber) {
        return CARD_REPOSITORY.lockCard(cardNumber);
    }

    @Override
    public boolean checkPin(String pin) {
        return CARD_REPOSITORY.checkPin(currentCard.getNumber(), pin);
    }






    private boolean checkLinkedAccount(String accountNumber) {
        return ACCOUNT_SERVICE.isExistAccountNumber(accountNumber);
    }

    private void getLinkedAccount(String accountNumber) {
        ACCOUNT_SERVICE.getLinkedAccountWithCard(accountNumber);
    }


    @Override
    public void pinChange(String newPin) {
//        String message;
//        if (!newPin.equals(currentCard.getPin())) {
//            if (CARD_REPOSITORY.pinChange(currentCard.getNumber(), newPin))
//                message = "Pin change success!";
//            else
//                message = "Pin change fails!";
//
//            VIEW.enterPin(currentCard.getName(), 1, message);
//        } else {
//            message = "New pin can not the same old pin!";
//            VIEW.pinChange(message);
//        }
    }

    @Override
    public void logout() {
        currentCard = null;
        removeLinkedAccount();
        VIEW.insertCard();
    }

    private void removeLinkedAccount() {
        ACCOUNT_SERVICE.removeLinkedAccountWithCard();
    }
}
