package dal;

import dto.Card;

public interface CardDAL {
    Card findByCardNumber(String cardNumber);
    boolean pinChange(String cardNumber, String newPIN);
}
