package ru.polesov.myloyaltycards.fragments;

import android.content.Context;

import java.util.List;

import ru.polesov.myloyaltycards.model.Card;

public interface ListCardFragment {
    public void updateUI (List<Card> cards);
    public Context getActivity();
    public void showDialogLongClickMenu();
    public void showDialogCardDelete();
    public void showCardDetailed(String id);
    public void showDialogSort(int idsort);
    public void showBarCode(String str,String id);
}
