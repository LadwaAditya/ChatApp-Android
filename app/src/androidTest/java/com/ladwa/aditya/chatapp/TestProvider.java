package com.ladwa.aditya.chatapp;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;

import data.ChatDatabaseContract;
import data.DatabaseHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestProvider extends ApplicationTestCase<Application> {
    public TestProvider() {
        super(Application.class);
    }

    public void testDeleteDB() throws Throwable {
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);

    }


    public void testInsertDb() {
        String testName = "Aditya";
        String testMsg = "hey";
        String testTime = "7:56 AM";

        SQLiteDatabase db = new DatabaseHelper(mContext).getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(ChatDatabaseContract.TableEntry.COLUMN_NAME, testName);
        values.put(ChatDatabaseContract.TableEntry.COLUMN_MSG, testMsg);
        values.put(ChatDatabaseContract.TableEntry.COLUMN_TIME_STAMP, testTime);

        long locationId;


        locationId = db.insert(ChatDatabaseContract.TableEntry.TABLE_NAME, null, values);

        assertTrue(locationId != -1);
        Log.d("DATABASE ", "new row inserted at" + locationId);


        String[] columns = {
                ChatDatabaseContract.TableEntry.COLUMN_ID,
                ChatDatabaseContract.TableEntry.COLUMN_NAME,
                ChatDatabaseContract.TableEntry.COLUMN_MSG,
                ChatDatabaseContract.TableEntry.COLUMN_TIME_STAMP};

        Cursor cursor = db.query(
                ChatDatabaseContract.TableEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {


            String name = cursor.getString(cursor.getColumnIndex(ChatDatabaseContract.TableEntry.COLUMN_NAME));
            String msg = cursor.getString(cursor.getColumnIndex(ChatDatabaseContract.TableEntry.COLUMN_MSG));
            String time = cursor.getString(cursor.getColumnIndex(ChatDatabaseContract.TableEntry.COLUMN_TIME_STAMP));

            assertEquals(testMsg, msg);
            assertEquals(testName, name);
            assertEquals(testTime, time);

        } else {
            fail("No values Returned");
        }
    }

    public void testType() {
        final int testId = 100;
        final String testName = "Aditya";

        String type = mContext.getContentResolver().getType(ChatDatabaseContract.TableEntry.buildChatUriwithid(testId));
        assertEquals(ChatDatabaseContract.TableEntry.CONTENT_ITEM_TYPE, type);

        type = mContext.getContentResolver().getType(ChatDatabaseContract.TableEntry.buildChatUriwithname(testName));
        assertEquals(ChatDatabaseContract.TableEntry.CONTENT_ITEM_TYPE, type);
    }


}