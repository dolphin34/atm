package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.NotEnoughCashInAtmException;

import java.util.*;

public class CashInAtm {

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

    private static final Map<ValueOfCash, Integer> cashInAtm = new EnumMap<>(ValueOfCash.class);

    static {
        // initialized cash in atm
        cashInAtm.put(ValueOfCash.FIFTY_THOUSAND, 10000);
        cashInAtm.put(ValueOfCash.HUNDRED_THOUSAND, 100);
        cashInAtm.put(ValueOfCash.TWO_HUNDRED_THOUSAND, 50);
        cashInAtm.put(ValueOfCash.FIVE_HUNDRED_THOUSAND, 20);
    }

    public static void checkCashInAtm(long amount) throws NotEnoughCashInAtmException {
        if (cashOut(amount) == null) {
            throw new NotEnoughCashInAtmException();
        }
    }

    public static List<Map.Entry<Integer, Integer>> getCashOut(long amount) {
        List<Map.Entry<Integer, Integer>> result = cashOut(amount);
        if (result != null) {
            updateCashInAtm(result);
        }
        return result;
    }

    private static List<Map.Entry<Integer, Integer>> cashOut(long amount) {
        SortedSet<Map.Entry<ValueOfCash, Integer>> valueCashInAtm = new TreeSet<>((a, b) -> b.getKey().getValue() - a.getKey().getValue());
        valueCashInAtm.addAll(cashInAtm.entrySet());

        long sumOfCashInAtm = valueCashInAtm.stream().mapToLong(a -> a.getKey().getValue() * a.getValue()).sum();
        if (amount > sumOfCashInAtm) {
            return null;
        }

        List<Map.Entry<Integer, Integer>> result = new ArrayList<>();
        for (Map.Entry<ValueOfCash, Integer> e : valueCashInAtm) {
            int cashValue = e.getKey().getValue();
            int quantity = e.getValue();

            int piecesOfMoney;
            if (amount > 0 && quantity > 0) {
                piecesOfMoney = (int) amount / cashValue;
                if (piecesOfMoney > quantity) {
                    piecesOfMoney = quantity;

                }
                if (piecesOfMoney > 0) {
                    amount = amount - (piecesOfMoney * cashValue);
                    result.add(new AbstractMap.SimpleImmutableEntry<>(cashValue, piecesOfMoney));
                }
            }
        }
        if (amount != 0) {
            return null;
        }
        return result;
    }

    private static void updateCashInAtm(List<Map.Entry<Integer, Integer>> outCash) {
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
}
