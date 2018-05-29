package ru.polesov.myloyaltycards.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import ru.polesov.myloyaltycards.model.Card;

public class CardCursorWrapper extends CursorWrapper {

    public CardCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Card getCard() {
        String uuidString = getString(getColumnIndex(DbSchema.CardTable.Cols.UUID));
        String title = getString(getColumnIndex(DbSchema.CardTable.Cols.TITLE));
        String barcode = getString(getColumnIndex(DbSchema.CardTable.Cols.BARCODE));
        String comment = getString(getColumnIndex(DbSchema.CardTable.Cols.COMMENT));
        int color = getInt(getColumnIndex(DbSchema.CardTable.Cols.COLOR));
        long date = getLong(getColumnIndex(DbSchema.CardTable.Cols.DATE));
        Card c = new Card(UUID.fromString(uuidString));
        c.setTitle(title);
        c.setDate(new Date(date));
        c.setBarCode(barcode);
        c.setComment(comment);
        c.setColor(color);
        return c;
    }
}


