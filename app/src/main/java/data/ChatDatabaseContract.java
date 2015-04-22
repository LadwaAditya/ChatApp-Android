package data;

import android.provider.BaseColumns;

/**
 * Created by AdityaLadwa on 19-03-2015.
 */
public class ChatDatabaseContract {


    public static final class TableEntry implements BaseColumns {


        public static final String TABLE_NAME = "chat";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MSG = "msg";
        public static final String COLUMN_TIME_STAMP = "timestamp";


    }
}
