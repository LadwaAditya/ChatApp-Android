package com.ladwa.aditya.chatapp;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;

import data.ChatDatabaseContract;
import data.DatabaseHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testDb() throws Throwable {
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
        SQLiteDatabase db = new DatabaseHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertDb() {
        String name = "Aditya";
        String msg = "hey";
        String time = "7:56 AM";

        DatabaseHelper helper = new DatabaseHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(ChatDatabaseContract.TableEntry.COLUMN_NAME, name);
        values.put(ChatDatabaseContract.TableEntry.COLUMN_MSG, msg);
        values.put(ChatDatabaseContract.TableEntry.COLUMN_TIME_STAMP, time);

        long locationId;


        locationId = db.insert(ChatDatabaseContract.TableEntry.TABLE_NAME, null, values);

        assertTrue(locationId != -1);
        Log.d("DATABASE ", "new row inserted at" + locationId);
    }
}