package ru.polesov.myloyaltycards.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardsItems
{
    private static CardsItems sCardsItems;
    private List<Card> mCards;


    public static CardsItems get(Context context) {
        if (sCardsItems == null) {
            sCardsItems = new CardsItems(context);
        }
        return sCardsItems;
    }

    private CardsItems(Context context) {
        mCards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Card card = new Card();
            card.setTitle("Название #" + i);
            card.setNumberCard("515" + i);
            card.setComment("Комментарий" + i);
            mCards.add(card);
        }
    }

    public List<Card> getCards() {
        return mCards;
    }

    public Card getCard(UUID id){
        for (Card card : mCards) {
            if (card.getID().equals(id)) {
                return card;
            }
        }
        return null;
    }
}
