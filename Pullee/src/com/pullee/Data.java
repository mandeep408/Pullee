package com.pullee;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class Data
{
    private static final  String DATABASE_NAME = "pullee.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME_1 = "Pullee";
    private static Context context;
    static SQLiteDatabase db;
     
    public Data(Context context) {
    	Data.context = context;
    	OpenHelper openHelper = new OpenHelper(Data.context);
    	Data.db = openHelper.getWritableDatabase();
    }
    public void insert(String query) {
    	db.execSQL(query);
    }
 
    public void delete() {
    db.delete(TABLE_NAME_1, null, null);
    }
    
    public String select(String query) {
    	String s = DatabaseUtils.stringForQuery(db, query, null);
    	return s;
    }
    public void update(String query) {
    	db.execSQL(query);
    }

   private static class OpenHelper extends SQLiteOpenHelper {
    OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE Pullee (fistOpen TEXT);");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
         onCreate(db);
        }
   }
}
