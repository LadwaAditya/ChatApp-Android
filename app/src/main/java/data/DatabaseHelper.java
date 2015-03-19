package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AdityaLadwa on 19-03-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "chat.db";


    public static final String QUERY_CREATE_TABLE = "CREATE TABLE " + ChatDatabaseContract.TableEntry.TABLE_NAME +
            " (" + ChatDatabaseContract.TableEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChatDatabaseContract.TableEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            ChatDatabaseContract.TableEntry.COLUMN_MSG + " TEXT NOT NULL, " +
            ChatDatabaseContract.TableEntry.COLUMN_TIME_STAMP + "TEXT NOT NULL)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ChatDatabaseContract.TableEntry.TABLE_NAME);
        onCreate(db);
    }
}
