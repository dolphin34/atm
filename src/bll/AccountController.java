package bll;

public interface AccountController {
    boolean findByAccountNumber(String accountNumber);
    void getLinkedAccountWithCard(String accountNumber);
    void removeLinkedAccountWithCard();
    void balanceInquiry();
    void cashWithdrawal(long amount);
    boolean checkReceiveAccount(String accountNumberReceice);
    void transfer(String accountNumberReceive, long amountTransfer);
    void printStatement();
}
