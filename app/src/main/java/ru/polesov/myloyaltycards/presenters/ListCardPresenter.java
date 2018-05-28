package ru.polesov.myloyaltycards.presenters;

import java.util.UUID;

public interface ListCardPresenter {

    public void updateUI();
    public void OnItemClick(UUID id);
    public void OnLongItemClick(UUID id);
    public UUID getId();
    public void setId(UUID mId);
    public void selectedItemLongMenu(int which);
    public void selectedItemDelete();
    public void clickMenuSorted();
    public void selectedMenuSorted(int id);
    public void clickFab();
}
