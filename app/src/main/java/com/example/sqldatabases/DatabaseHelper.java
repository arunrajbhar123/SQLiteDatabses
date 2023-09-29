package com.example.sqldatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_AGE = "CUSTMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + CUSTOMER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT," + CUSTOMER_AGE + " INT," + ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addCustomer(CustomerData customerData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME, customerData.getName());
        cv.put(CUSTOMER_AGE, customerData.getAge());
        cv.put(ACTIVE_CUSTOMER, customerData.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }


    public List<CustomerData> getAllCustomer() {

        List<CustomerData> returnList = new ArrayList<>();

        String query = "Select * from " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1;

                returnList.add(new CustomerData(customerID, customerName, customerAge, customerActive));

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return returnList;
    }


    public boolean deleteOneCustomer(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CUSTOMER_TABLE + " WHERE ID = " + customerId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

}
