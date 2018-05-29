package ru.polesov.myloyaltycards.model;

import java.util.Date;
import java.util.UUID;

public class Card {

    private UUID mID;
    private String mTitle;
    private Date mDate;
    private String mComment;
    private String mBarCode;
    private int mColor;

    public Card() {
        this(UUID.randomUUID());
    }

    public Card(UUID id) {
        mID = id;
        mDate = new Date();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public String getBarCode() {
        return mBarCode;
    }

    public void setBarCode(String mNumberCard) {
        this.mBarCode = mNumberCard;
    }

    public UUID getID() {
        return mID;
    }

    public String getFacePhotoFilename(){
        return "IMG_" + getID().toString() + ".jpg";
    }
    public String getBackPhotoFilename(){
        return "IMG_" + getID().toString() + "_2.jpg";
    }

}
