package database;

import dto.Account;
import dto.Card;
import dto.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {
    public static List<Card> listCard = new ArrayList<>();
    public static List<Account> listAccount = new ArrayList<>();
    public static List<Transaction> listTransaction = new ArrayList<>();
    public static int[][] cashInAtm = new int[2][4];

    static {
        // initialized list card
        Card c1 = new Card("08001001","123456","VU THI HOA", "11111", true);
        Card c2 = new Card("08001002","123456","BUI VAN HUNG", "22222", false);
        Card c3 = new Card("08001003","123456","NGUYEN VAN ANH", "33333", true);
        Card c4 = new Card("08001004","123456","VU THI THU", "44444", true);
        Card c5 = new Card("08001005","123456","HA VAN TU", "55555", true);
        listCard.add(c1);
        listCard.add(c2);
        listCard.add(c3);
        listCard.add(c4);
        listCard.add(c5);

        // initialized list account
        Account a1 = new Account("11111", "VU THI HOA", 20_000_000, 1, 2, 3, 4);
        Account a2 = new Account("22222", "BUI VAN HUNG", 30_000_000);
        Account a3 = new Account("33333", "NGUYEN VAN ANH", 30_000_000, 3, 4);
        Account a4 = new Account("44444", "VU THI THU", 40_000_000);
        Account a5 = new Account("00000", "HA VAN TU", 10_000_000);
        listAccount.add(a1);
        listAccount.add(a2);
        listAccount.add(a3);
        listAccount.add(a4);
        listAccount.add(a5);

        // initialized list transaction
        Transaction t1 = new Transaction(1, Transaction.TransactionType.CASH_WITHDRAWAL, 3_000_000, new Date(), "11111", null);
        Transaction t2 = new Transaction(2, Transaction.TransactionType.CASH_WITHDRAWAL, 5_000_000, new Date(), "11111", null);
        Transaction t3 = new Transaction(3, Transaction.TransactionType.TRANSFER, 10_000_000, new Date(), "11111", "33333");
        Transaction t4 = new Transaction(4, Transaction.TransactionType.TRANSFER, 10000000000L, new Date(), "11111", "33333");
        listTransaction.add(t1);
        listTransaction.add(t2);
        listTransaction.add(t3);
        listTransaction.add(t4);

        // initialized cash in atm
        cashInAtm[0][0] = 50_000;
        cashInAtm[0][1] = 100_000;
        cashInAtm[0][2] = 200_000;
        cashInAtm[0][3] = 500_000;

        cashInAtm[1][0] = 10;
        cashInAtm[1][1] = 50;
        cashInAtm[1][2] = 100;
        cashInAtm[1][3] = 200;
    }
    public enum CashInAtm {
        CASH_IN_ATM_50(1,50_000),
        CASH_IN_ATM_100(0,100_000),
        CASH_IN_ATM_200(0,200_000),
        CASH_IN_ATM_500(10,500_000);

        private int quantity;
        private final int value;

        CashInAtm(int quantity, int value) {
            this.quantity = quantity;
            this.value = value;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getValue() {
            return value;
        }

        public static long sumOfCash() {
            long sum = 0;
            for (CashInAtm c : CashInAtm.values()) {
                sum += c.quantity * c.value;
            }
            return sum;
        }
    }
}
