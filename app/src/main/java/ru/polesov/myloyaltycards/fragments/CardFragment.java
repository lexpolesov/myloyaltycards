package ru.polesov.myloyaltycards.fragments;

import android.content.Context;

public interface CardFragment {
    public Context getActivity();
    public void setName(String name);
    public void setCode(String code);
    public void setComment(String comment);
    public void showBarcodeView();
    public void showFotoView(int FaceOrBack);
    public void finish();
}
