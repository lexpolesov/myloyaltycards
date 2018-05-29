package ru.polesov.myloyaltycards.DB;

public class DbSchema {
    public static final class CardTable {
        public static final String NAME = "cards";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COLOR = "color";
            public static final String BARCODE = "barcode";
            public static final String COMMENT = "comment";
        }
    }
}
