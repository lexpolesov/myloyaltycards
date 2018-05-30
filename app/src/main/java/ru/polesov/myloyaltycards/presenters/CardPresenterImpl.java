package ru.polesov.myloyaltycards.presenters;

import java.io.File;
import java.util.UUID;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.fragments.CardFragment;
import ru.polesov.myloyaltycards.model.CardsItems;

public class CardPresenterImpl implements CardPresenter {
    private CardFragment mCardFragment;
    private Card mCard;
    private boolean newcard = true;
    private int idaction;

    public CardPresenterImpl (CardFragment fragment, String id){
        this.mCardFragment = fragment;
        setId(id);
    }

    @Override
    public void clickScanCode() {
        mCardFragment.showBarcodeView();
    }

    @Override
    public void clickCreateFoto1() {
        mCardFragment.showFotoView(0);
    }

    @Override
    public void clickCreateFoto2() {
        mCardFragment.showFotoView(1);
    }

    @Override
    public void clickSave(Card card) {
        this.mCard = card;
        CardsItems cardsItems = CardsItems.get(mCardFragment.getActivity());
        if (newcard)
            cardsItems.addCard(mCard);
        else
            cardsItems.updateCrime(mCard);
        mCardFragment.finish();
    }

    @Override
    public String getId() {
        return mCard.getID().toString();
    }

    @Override
    public Card getCard() {
        return mCard;
    }

    private void setId(String Id) {
        if (!Id.equals("")) {
            mCard = CardsItems.get(mCardFragment.getActivity()).getCard(UUID.fromString(Id));
            newcard = false;
        }
        else {
            mCard = new Card();
            newcard = true;
        }
    }

    public File getFacePhotoFile() {
        File filesDir = mCardFragment.getActivity().getFilesDir();
        return new File(filesDir, mCard.getFacePhotoFilename());
    }

    public File getBackPhotoFile() {
        File filesDir = mCardFragment.getActivity().getFilesDir();
        return new File(filesDir, mCard.getBackPhotoFilename());
    }

    public void setFotoAction(int action){
        this.idaction = action;
    }

    public int getFotoAction(){
        return this.idaction;
    }
}
