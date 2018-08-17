package com.example.vst.testsqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.vst.testsqlite.model.Student;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studients_manager";
    private static final String TABLE_NAME = "studients";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";
    private static final int VERSION = 1;
    private Context context;

    String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE_NUMBER + " TEXT, " +
            ADDRESS + " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addStudent(Student student) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ADDRESS, student.getmAddress());
        values.put(EMAIL, student.getmEmail());
        values.put(NAME, student.getmName());
        values.put(PHONE_NUMBER, student.getmPhoneNumber());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Student> getAllStudents() {

        List<Student> studentList = new ArrayList<>();

        String selectedQuerry = "SELECT *FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectedQuerry, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setmID(cursor.getInt(0));
                student.setmAddress(cursor.getString(1));
                student.setmEmail(cursor.getString(2));
                student.setmName(cursor.getString(3));
                student.setmPhoneNumber(cursor.getString(4));

                studentList.add(student);

            } while (cursor.moveToNext());

        }
        db.close();
        return studentList;

    }

    public int updateStudent(Student student) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, student.getmName());
        contentValues.put(ADDRESS, student.getmAddress());
        contentValues.put(PHONE_NUMBER, student.getmPhoneNumber());
        contentValues.put(EMAIL, student.getmEmail());
        int number = db.update(TABLE_NAME, contentValues, ID+"=?", new String[]{String.valueOf(student.getmID())});
        return number;
    }

    public int deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(id)});

    }


}
