package ru.polesov.myloyaltycards.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.polesov.myloyaltycards.DB.DbSchema.CardTable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "cardDB.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CardTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                CardTable.Cols.UUID + ", " +
                CardTable.Cols.TITLE + ", " +
                CardTable.Cols.BARCODE + ", " +
                CardTable.Cols.COLOR + ", " +
                CardTable.Cols.COMMENT + ", " +
                CardTable.Cols.DATE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
