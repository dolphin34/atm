package bll;

import dal.CardDAL;
import dal.CardDALImpl;
import dto.Card;
import ui.ViewsImpl;
import ui.Views;

public class CardControllerImpl implements CardController {
    private static Card currentCard;

    private static final Views views = new ViewsImpl();
    private static final CardDAL cardDAL = new CardDALImpl();
    private static final AccountController accountController = new AccountControllerImpl();

    @Override
    public void checkCard(String cardNumber) {
        currentCard = cardDAL.findByCardNumber(cardNumber);
        String message;
        if (currentCard == null) {
            message = "Card does not exist!";
            views.insertCard(message);
        } else if (currentCard.isActive()) {
            if (checkLinkedAccount(currentCard.getAccountNumber())) {
                views.enterPin(currentCard.getName(), 1);
            } else {
                message = "Do not have a account link with card!";
                views.insertCard(message);
            }
        } else {
            message = "Your card is locked!";
            views.insertCard(message);
        }
    }

    private boolean checkLinkedAccount(String accountNumber) {
        return accountController.findByAccountNumber(accountNumber);
    }

    @Override
    public void checkPin(String Pin, int time) {
        String message;
        if (currentCard.getPin().equals(Pin)) {
            getLinkedAccount(currentCard.getAccountNumber());
            views.home();
        } else if (time == 3) {
            currentCard.setActive(false);
            message = "Your card has been locked!";
            views.insertCard(message);
        } else {
            message = "Wrong Pin (" + time + ")!";
            views.enterPin(currentCard.getName(), time + 1, message);
        }
    }

    private void getLinkedAccount(String accountNumber) {
        accountController.getLinkedAccountWithCard(accountNumber);
    }


    @Override
    public void pinChange(String newPin) {
        String message;
        if (!newPin.equals(currentCard.getPin()) && cardDAL.pinChange(currentCard.getNumber(), newPin)) {
            message = "Pin Change Success";
            views.enterPin(currentCard.getName(), 1, message);
        } else {
            message = "New pin can not the same old pin!";
            views.pinChange(message);
        }
    }

    @Override
    public void logout() {
        currentCard = null;
        removeLinkedAccount();
        views.insertCard();
    }

    private void removeLinkedAccount() {
        accountController.removeLinkedAccountWithCard();
    }
}
