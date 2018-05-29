package ru.polesov.myloyaltycards.presenters;

import android.util.Log;

import java.util.List;
import java.util.UUID;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.fragments.ListCardFragment;
import ru.polesov.myloyaltycards.model.CardsItems;

public class ListCardPresenterImpl implements ListCardPresenter {

    private ListCardFragment mListCardFragment;
    private UUID mId;
    private int idsort; //0-по дате ввода, 1- по алфавиту

    public ListCardPresenterImpl (ListCardFragment fragment){
        this.mListCardFragment = fragment;
        this.idsort = 0;
    }

    @Override
    public void updateUI() {
        CardsItems cardsItems = CardsItems.get(mListCardFragment.getActivity());
        List<Card> cards = cardsItems.getCards(idsort);
        mListCardFragment.updateUI(cards);
    }

    @Override
    public void OnItemClick(UUID id) {
        this.mId = id;
        CardsItems cardsItems = CardsItems.get(mListCardFragment.getActivity());
        String barc = cardsItems.getCard(mId).getBarCode();
        mListCardFragment.showBarCode(barc, mId.toString());
    }

    @Override
    public void OnLongItemClick(UUID id) {
        this.mId = id;
        mListCardFragment.showDialogLongClickMenu();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    @Override
    public void selectedItemLongMenu(int which) {
        switch (which){
            case 0:
                mListCardFragment.showCardDetailed(mId.toString());
                break;
            case 1:
                mListCardFragment.showDialogCardDelete();
                break;
        }
    }

    @Override
    public void selectedItemDelete() {
        CardsItems cardsItems = CardsItems.get(mListCardFragment.getActivity());
        cardsItems.deleteCard(mId.toString());
        updateUI();
    }

    @Override
    public void clickMenuSorted() {
        mListCardFragment.showDialogSort(idsort);
    }

    @Override
    public void selectedMenuSorted(int id) {
        idsort = id;
        updateUI();
    }

    @Override
    public void clickFab() {
        mListCardFragment.showCardDetailed("");
    }

}
