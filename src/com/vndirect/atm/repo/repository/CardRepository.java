package com.vndirect.atm.repo.repository;

import com.vndirect.atm.repo.entity.Card;

public interface CardRepository {

    Card findCardByNumber(String cardNumber);
    boolean updateInfoCard(Card updateCard);
}
