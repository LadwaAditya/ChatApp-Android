package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

/**
 * Created by AdityaLadwa on 21-03-2015.
 */
public class ChatSource {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public ChatSource(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() throws SQLDataException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
}
