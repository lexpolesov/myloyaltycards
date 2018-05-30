package ru.polesov.myloyaltycards.presenters;

import java.io.File;

import ru.polesov.myloyaltycards.model.Card;

public interface CardPresenter {
    public void clickScanCode();
    public void clickCreateFoto1();
    public void clickCreateFoto2();
    public void clickSave(Card card);
    public String getId();
    public Card getCard();
    public File getFacePhotoFile();
    public File getBackPhotoFile();
    public void setFotoAction(int action);
    public int getFotoAction();

}
