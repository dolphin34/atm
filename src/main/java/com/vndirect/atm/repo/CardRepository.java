package com.vndirect.atm.repo;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.entity.Card;

import java.util.Optional;

public interface CardRepository {

    Optional<Card> findOneByNumber(String cardNumber);

    void update(Card updateCard) throws FailActionException;
}
