package data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AdityaLadwa on 19-03-2015.
 */
public class ChatDatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.ladwa.aditya.chatapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CHAT = "chat";

    public static final class TableEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHAT).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CHAT;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CHAT;

        public static final String TABLE_NAME = "chat";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MSG = "msg";
        public static final String COLUMN_TIME_STAMP = "timestamp";




    }
}
