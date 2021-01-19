package com.vndirect.atm.repo.impl.inmemory;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.entity.Card;
import com.vndirect.atm.repo.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepositoryImpl implements CardRepository {

    private static final CardRepositoryImpl INSTANCE = new CardRepositoryImpl();

    private static final List<Card> CARDS = new ArrayList<>();

    static {
        // initialized list card
        String password = "123456";
        Card c1 = new Card("08001001", password, "VU THI HOA", "11111", true);
        Card c2 = new Card("08001002", password, "BUI VAN HUNG", "22222", false);
        Card c3 = new Card("08001003", password, "NGUYEN VAN ANH", "33333", true);
        Card c4 = new Card("08001004", password, "VU THI THU", "44444", true);
        Card c5 = new Card("08001005", password, "HA VAN HUY", "55555", true);
        Card c6 = new Card("08001006", password, "NGUYEN THI THU HA", "666666", true);
        CARDS.add(c1);
        CARDS.add(c2);
        CARDS.add(c3);
        CARDS.add(c4);
        CARDS.add(c5);
        CARDS.add(c6);
    }

    public static CardRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Card> findOneByNumber(String cardNumber) {
        return CARDS.stream()
                .filter(c -> c.getNumber().equals(cardNumber))
                .findFirst();
    }

    @Override
    public void update(Card updateCard) throws FailActionException {
        Optional<Card> card = findOneByNumber(updateCard.getNumber());
        if (card.isPresent()) {
            card.get().setPin(updateCard.getPin());
            card.get().setActive(updateCard.isActive());
        } else {
            throw new FailActionException();
        }
    }
}
