package ru.polesov.myloyaltycards.presenters;

import android.util.Log;

import junit.framework.Test;

import java.util.UUID;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.fragments.CardFragment;
import ru.polesov.myloyaltycards.model.CardsItems;

public class CardPresenterImpl implements CardPresenter {
    private CardFragment mCardFragment;
    private Card mCard;

    public CardPresenterImpl (CardFragment fragment){
        this.mCardFragment = fragment;
    }

    @Override
    public void clickScanCode() {

    }

    @Override
    public void clickCreateFoto1() {

    }

    @Override
    public void clickCreateFoto2() {

    }

    @Override
    public void clickSave() {

    }

    @Override
    public void clickViewFoto1() {

    }

    @Override
    public void clickViewFoto2() {

    }

    public String getId() {
        return mCard.getID().toString();
    }

    public void setId(String Id) {
        if (!Id.equals("")) {
            mCard = CardsItems.get(mCardFragment.getActivity()).getCard(UUID.fromString(Id));
             sendDataCard();
            Log.d("Test", "mCard.getTitle() " + mCard.getTitle() + "mCard.getmNumberCard() " + mCard.getmNumberCard() + "mCard.getComment( )" + mCard.getComment());
        }
        else
            mCard = new Card();
    }

    private void sendDataCard(){
        mCardFragment.setName(mCard.getTitle());
        mCardFragment.setCode(mCard.getmNumberCard());
        mCardFragment.setComment(mCard.getComment());
    }
}
