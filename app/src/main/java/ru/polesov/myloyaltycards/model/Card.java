package ru.polesov.myloyaltycards.model;

import java.util.Date;
import java.util.UUID;

public class Card {

    private UUID mID;
    private String mTitle;
    private Date mDate;
    private String mComment;
    private String mNumberCard;
    // TODO
    //Фото лицевой стороны
    //Фото задней стороны
    //Цвет

    public Card() {
        mID = UUID.randomUUID();
        mDate = new Date();
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

    public String getmNumberCard() {
        return mNumberCard;
    }

    public void setNumberCard(String mNumberCard) {
        this.mNumberCard = mNumberCard;
    }

    public UUID getID() {
        return mID;
    }
}
