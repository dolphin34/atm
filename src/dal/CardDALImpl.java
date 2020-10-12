package dal;

import database.Data;
import dto.Card;

public class CardDALImpl implements CardDAL {
    @Override
    public Card findByCardNumber(String cardNumber) {
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
