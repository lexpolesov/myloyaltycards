package ru.polesov.myloyaltycards.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.polesov.myloyaltycards.DB.CardCursorWrapper;
import ru.polesov.myloyaltycards.DB.DbHelper;
import ru.polesov.myloyaltycards.DB.DbSchema;

public class CardsItems
{
    private static CardsItems sCardsItems;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CardsItems get(Context context) {
        if (sCardsItems == null) {
            sCardsItems = new CardsItems(context);
        }
        return sCardsItems;
    }

    private CardsItems(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }

    public List<Card> getCards(int idsort) {
        List<Card> cards = new ArrayList<>();
        String orderby = null;
        if (idsort == 0)
            orderby = DbSchema.CardTable.Cols.DATE + " DESC";
        else
            orderby = DbSchema.CardTable.Cols.TITLE + " ASC";
        CardCursorWrapper cursor = queryCards(null, null, orderby);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cards.add(cursor.getCard());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cards;
    }

    public Card getCard(UUID id){
        CardCursorWrapper cursor = queryCards(
                DbSchema.CardTable.Cols.UUID + " = ?",
                new String[] { id.toString() }, null
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCard();
        } finally {
            cursor.close();
        }
    }

    public void addCard(Card c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(DbSchema.CardTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Card c) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.CardTable.Cols.UUID, c.getID().toString());
        values.put(DbSchema.CardTable.Cols.TITLE, c.getTitle());
        values.put(DbSchema.CardTable.Cols.BARCODE, c.getBarCode());
        values.put(DbSchema.CardTable.Cols.COLOR, String.valueOf(c.getColor()));
        values.put(DbSchema.CardTable.Cols.COMMENT, c.getComment());
        values.put(DbSchema.CardTable.Cols.DATE, c.getDate().getTime());
        return values;
    }

    public void updateCrime(Card c) {
        String uuidString = c.getID().toString();
        ContentValues values = getContentValues(c);
        mDatabase.update(DbSchema.CardTable.NAME, values,
                DbSchema.CardTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteCard(String uuidString) {
        mDatabase.delete(DbSchema.CardTable.NAME,
                DbSchema.CardTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CardCursorWrapper queryCards(String whereClause, String[] whereArgs, String order) {
        Cursor cursor = mDatabase.query(
                DbSchema.CardTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                order // orderBy
        );
        return new CardCursorWrapper(cursor);
    }
}

