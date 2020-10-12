package bll;

public interface CardController {
    void checkCard(String cardNumber);
    void checkPin(String Pin, int time);
    void pinChange(String newPin);
    void logout();
}
