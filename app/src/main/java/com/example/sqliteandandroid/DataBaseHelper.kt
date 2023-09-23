package com.example.sqliteandandroid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context?) : SQLiteOpenHelper(context, "customer.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement =
            "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL )"
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {



    }
    fun addOne(customModel : CustomModel) : Boolean {

        val db = this.writableDatabase;
        val cv = ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customModel.name)
        cv.put(COLUMN_CUSTOMER_AGE, customModel.age)
        cv.put(COLUMN_ACTIVE_CUSTOMER, customModel.isActive)

        val insert : Long = db.insert(CUSTOMER_TABLE, null, cv)

        if( insert == -1L ) {
            return false
        }
            return true;
    }

    fun getEveryone(): List<CustomModel> {

        val querySelect : String = "SELECT * FROM " + CUSTOMER_TABLE;

        val db = this.readableDatabase;
        val returnList = arrayListOf<CustomModel>()
        var cursor : Cursor = db.rawQuery(querySelect, null)

        if(cursor.moveToFirst()) {
            do {
                var customerId : Int = cursor.getInt(0);
                var customerName : String = cursor.getString(1)
                var customerAge : Int = cursor.getInt(2)
                var customerActive : Boolean = if( cursor.getInt(3) == 1 ) true else false

                val newCustomer = CustomModel(customerId,customerName,customerAge, customerActive)
                returnList.add(newCustomer)


            }   while (cursor.moveToNext());
        }else {
                /// failure
        }
        cursor.close()
        db.close()
        return returnList;

    }

    fun deleteOne(customModel: CustomModel) : Boolean {

        val db = this.writableDatabase;
        val querySelect : String = "DELETE FROM "+  CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customModel.id;


        val cursor : Cursor = db.rawQuery(querySelect, null)

        if(cursor.moveToFirst() ) {
            return true
        }
        return false;
    }

    companion object {
        const val CUSTOMER_TABLE = "CUSTOMER_TABLE"
        const val COLUMN_ID = "ID"
        const val COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME"
        const val COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE"
        const val COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER"
    }
}