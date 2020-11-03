package com.vndirect.atm.controller.repo.repository;

import com.vndirect.atm.controller.repo.entity.Card;

public interface CardRepository {

    Card findCardByNumber(String cardNumber);
    boolean updateInfoCard(Card updateCard);
}
