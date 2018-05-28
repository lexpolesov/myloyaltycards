package ru.polesov.myloyaltycards.presenters;

import java.util.UUID;

public interface CardPresenter {
    public void clickScanCode();
    public void clickCreateFoto1();
    public void clickCreateFoto2();
    public void clickSave();
    public void clickViewFoto1();
    public void clickViewFoto2();
    public String getId();
    public void setId(String mId);

}
