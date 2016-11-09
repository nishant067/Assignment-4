package iiitd.nishant.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Jobs.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "jobs";
    public static final String COLUMN_NAME = "title";
    public static final String COLUMN_SCORE = "desc";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " TEXT," +
                COLUMN_SCORE + " INTEGER" +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addScore(Tasks tasks) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME, tasks.getJob());
        values.put(COLUMN_SCORE, tasks.getDesc());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateScore(Tasks tasks) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_NAME, tasks.getJob());
        values.put(COLUMN_SCORE, tasks.getDesc());
        db.update(TABLE_NAME, values, COLUMN_NAME + " = ?", new String[]{tasks.getJob()});
    }

    public List<Tasks> dtoS() {
        List<Tasks> myList = new ArrayList<Tasks>();
        String dS = "";

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Tasks tasks = new Tasks();
                tasks.setJob(c.getString(0));
                tasks.setDesc(c.getString(1));
                myList.add(tasks);
            }
            while (c.moveToNext());
        }
        db.close();
        return myList;
    }
}
