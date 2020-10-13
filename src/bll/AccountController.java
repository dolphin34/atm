package bll;

public interface AccountController {
    boolean isExistAccountNumber(String accountNumber);
    void getLinkedAccountWithCard(String accountNumber);
    void removeLinkedAccountWithCard();
    void balanceInquiry();
    void cashWithdrawal(long amount);
    void checkReceiveAccount(String accountNumberReceive);
    void transfer(String accountNumberReceive, long amountTransfer);
    void printStatement();
}
