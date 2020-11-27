package com.vndirect.atm.repo.repository.impl.inmemory;

import com.vndirect.atm.repo.entity.Card;
import com.vndirect.atm.repo.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private static final CardRepositoryImpl INSTANCE = new CardRepositoryImpl();
    private static final List<Card> CARD_LIST = new ArrayList<>();

    static {
        // initialized list card
        String password = "123456";
        Card c1 = new Card("08001001", password, "VU THI HOA", "11111", true);
        Card c2 = new Card("08001002", password, "BUI VAN HUNG", "22222", false);
        Card c3 = new Card("08001003", password, "NGUYEN VAN ANH", "33333", true);
        Card c4 = new Card("08001004", password, "VU THI THU", "44444", true);
        Card c5 = new Card("08001005", password, "HA VAN HUY", "55555", true);
        Card c6 = new Card("08001006", password, "NGUYEN THI THU HA", "666666", true);
        CARD_LIST.add(c1);
        CARD_LIST.add(c2);
        CARD_LIST.add(c3);
        CARD_LIST.add(c4);
        CARD_LIST.add(c5);
        CARD_LIST.add(c6);
    }

    public static CardRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Card findCardByNumber(String cardNumber) {
        Card card = null;
        for (Card c : CARD_LIST) {
            if (c.getNumber().equals(cardNumber)) {
                card = c;
                break;
            }
        }
        return card;
    }

    @Override
    public boolean updateInfoCard(Card updateCard) {
        boolean success = false;
        for (Card c : CARD_LIST) {
            if (c.getNumber().equals(updateCard.getNumber())) {
                c.setPin(updateCard.getPin());
                c.setActive(updateCard.isActive());
                success = true;
            }
        }
        return success;
    }
}
