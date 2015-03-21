package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by AdityaLadwa on 21-03-2015.
 */
public class ChatProvider extends ContentProvider {


    private DatabaseHelper mDatabaseHelper;
    public static final int MESSAGE = 100;
    public static final int NAME = 101;
    public static final UriMatcher sUriMatcher = buildUriMatcher();


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ChatDatabaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ChatDatabaseContract.PATH_CHAT, MESSAGE);
        matcher.addURI(authority, ChatDatabaseContract.PATH_CHAT + "/*", NAME);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
