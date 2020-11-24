package com.vndirect.atm.controller.repo.data;

import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Card;
import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Data {
    private final List<Card> listCard = new ArrayList<>();
    private final List<Account> listAccount = new ArrayList<>();
    private final List<Transaction> listTransaction = new ArrayList<>();
    private final Map<ValueOfCash, Integer> cashInAtm = new EnumMap<>(ValueOfCash.class);

    public Data() {
        // initialized list card
        String password = "123456";
        Card c1 = new Card("08001001", password, "VU THI HOA", "11111", true);
        Card c2 = new Card("08001002", password, "BUI VAN HUNG", "22222", true);
        Card c3 = new Card("08001003", password, "NGUYEN VAN ANH", "33333", true);
        Card c4 = new Card("08001004", password, "VU THI THU", "44444", true);
        Card c5 = new Card("08001005", password, "HA VAN HUY", "55555", true);
        Card c6 = new Card("08001006", password, "NGUYEN THI THU HA", "666666", true);
        listCard.add(c1);
        listCard.add(c2);
        listCard.add(c3);
        listCard.add(c4);
        listCard.add(c5);
        listCard.add(c6);

        // initialized list account
        Account a1 = new Account("11111", "VU THI HOA", 50_000_000, new ArrayList<>());
        Account a2 = new Account("22222", "BUI VAN HUNG", 3_000_000, new ArrayList<>());
        Account a3 = new Account("33333", "NGUYEN VAN ANH", 30_000_000, new ArrayList<>());
        Account a4 = new Account("44444", "VU THI THU", 100_000_000, new ArrayList<>());
        Account a5 = new Account("55555", "HA VAN TU", 10_000_000, new ArrayList<>());
        listAccount.add(a1);
        listAccount.add(a2);
        listAccount.add(a3);
        listAccount.add(a4);
        listAccount.add(a5);

        // initialized cash in atm
        cashInAtm.put(ValueOfCash.FIFTY_THOUSAND, 200);
        cashInAtm.put(ValueOfCash.HUNDRED_THOUSAND, 100);
        cashInAtm.put(ValueOfCash.TWO_HUNDRED_THOUSAND, 50);
        cashInAtm.put(ValueOfCash.FIVE_HUNDRED_THOUSAND, 20);
    }

    public List<Card> getListCard() {
        return this.listCard;
    }

    public List<Account> getListAccount() {
        return this.listAccount;
    }

    public List<Transaction> getListTransaction() {
        return this.listTransaction;
    }

    public Map<ValueOfCash, Integer> getCashInAtm() {
        return this.cashInAtm;
    }

    public void updateCashInAtm(List<Map.Entry<Integer, Integer>> outCash) {
        ValueOfCash valueOfCash;
        for (Map.Entry<Integer, Integer> e : outCash) {
            valueOfCash = null;
            for (ValueOfCash v : ValueOfCash.values()) {
                if (e.getKey().equals(v.getValue())) {
                    valueOfCash = v;
                    break;
                }
            }
            int oldQuantity = cashInAtm.get(valueOfCash);
            cashInAtm.put(valueOfCash, oldQuantity - e.getValue());
        }
    }

    public enum ValueOfCash {
        FIFTY_THOUSAND(50_000),
        HUNDRED_THOUSAND(100_000),
        TWO_HUNDRED_THOUSAND(200_000),
        FIVE_HUNDRED_THOUSAND(500_000);

        private final int value;

        ValueOfCash(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
