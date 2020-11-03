package com.vndirect.atm.controller.repo.data;

import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Card;
import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Data {
    public static List<Card> listCard = new ArrayList<>();
    public static List<Account> listAccount = new ArrayList<>();
    public static List<Transaction> listTransaction = new ArrayList<>();

    static {
        // initialized list card
        Card c1 = new Card("08001001", "123456", "VU THI HOA", "11111", true);
        Card c2 = new Card("08001002", "123456", "BUI VAN HUNG", "22222", false);
        Card c3 = new Card("08001003", "123456", "NGUYEN VAN ANH", "33333", true);
        Card c4 = new Card("08001004", "123456", "VU THI THU", "44444", true);
        Card c5 = new Card("08001005", "123456", "HA VAN TU", "55555", true);
        listCard.add(c1);
        listCard.add(c2);
        listCard.add(c3);
        listCard.add(c4);
        listCard.add(c5);

        // initialized list account
        Account a1 = new Account("11111", "VU THI HOA", 50_000_000, new ArrayList<>(Arrays.asList(1,2,3,4)));
        Account a2 = new Account("22222", "BUI VAN HUNG", 30_000_000, new ArrayList<>());
        Account a3 = new Account("33333", "NGUYEN VAN ANH", 30_000_000, new ArrayList<>(Arrays.asList(3,4)));
        Account a4 = new Account("44444", "VU THI THU", 100_000_000, new ArrayList<>());
        Account a5 = new Account("00000", "HA VAN TU", 10_000_000, new ArrayList<>());
        listAccount.add(a1);
        listAccount.add(a2);
        listAccount.add(a3);
        listAccount.add(a4);
        listAccount.add(a5);

        // initialized list transaction
        Transaction t1 = new Transaction(1, Transaction.TransactionType.CASH_WITHDRAWAL, 3_000_000, 1000, new Date(), "11111", null);
        Transaction t2 = new Transaction(2, Transaction.TransactionType.CASH_WITHDRAWAL, 5_000_000, 1000, new Date(), "11111", null);
        Transaction t3 = new Transaction(3, Transaction.TransactionType.TRANSFER, 10_000_000, 2000, new Date(), "11111", "33333");
        Transaction t4 = new Transaction(4, Transaction.TransactionType.TRANSFER, 10000000000L, 2000, new Date(), "11111", "33333");
        listTransaction.add(t1);
        listTransaction.add(t2);
        listTransaction.add(t3);
        listTransaction.add(t4);
    }

    public enum CashInAtm {
        //initialized cash in atm, sum cash: 20,000,000 VND
        CASH_IN_ATM_50(60, 50_000),
        CASH_IN_ATM_100(100, 100_000),
        CASH_IN_ATM_200(10, 200_000),
        CASH_IN_ATM_500(10, 500_000);

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

        public static int[][] getCashInATM() {
            int[][] temp = new int[3][4];
            temp[0][0] = Data.CashInAtm.CASH_IN_ATM_500.getValue();
            temp[0][1] = Data.CashInAtm.CASH_IN_ATM_200.getValue();
            temp[0][2] = Data.CashInAtm.CASH_IN_ATM_100.getValue();
            temp[0][3] = Data.CashInAtm.CASH_IN_ATM_50.getValue();

            temp[1][0] = Data.CashInAtm.CASH_IN_ATM_500.getQuantity();
            temp[1][1] = Data.CashInAtm.CASH_IN_ATM_200.getQuantity();
            temp[1][2] = Data.CashInAtm.CASH_IN_ATM_100.getQuantity();
            temp[1][3] = Data.CashInAtm.CASH_IN_ATM_50.getQuantity();
            return temp;
        }

        public static void updateQuantity(int[][] quantity) {
            for (int i = 0; i < 4; i++) {
                for (CashInAtm x: CashInAtm.values()) {
                    if (x.getValue() == quantity[0][i]) {
                        x.setQuantity(quantity[1][i]);
                    }
                }
            }
        }

    }
}
