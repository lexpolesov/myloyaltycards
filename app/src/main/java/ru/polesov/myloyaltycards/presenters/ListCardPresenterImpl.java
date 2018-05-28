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
        List<Card> cards = cardsItems.getCards();
        mListCardFragment.updateUI(cards);
    }

    @Override
    public void OnItemClick(UUID id) {
        this.mId = id;
    }

    @Override
    public void OnLongItemClick(UUID id) {
        this.mId = id;
        mListCardFragment.showDialogLongClickMenu();
    }

    public UUID getId() {
        Log.d("Test", "getId" + mId.toString());
        return mId;

    }

    public void setId(UUID mId) {
        this.mId = mId;
        Log.d("Test", "setId" + mId.toString());
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
        //TODO удаление элемента
        Log.d("Test", "selectedItemDelete" + mId.toString());
    }

    @Override
    public void clickMenuSorted() {
        mListCardFragment.showDialogSort(idsort);
    }

    @Override
    public void selectedMenuSorted(int id) {
        idsort = id;
        Log.d("Test", "selectedMenuSorted " + idsort);
        //TODO Сортировать, обновить список
    }

    @Override
    public void clickFab() {
        mListCardFragment.showCardDetailed("");
        Log.d("Test", "clickFab createnew card ");
    }

}
