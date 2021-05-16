package tr.yildiz.edu.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "mobil.db";

    public DBHelper(Context context) {
        super(context, "mobil.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(name TEXT,surname TEXT,number TEXT,email TEXT,password TEXT,image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    public boolean insertData(String name, String surname, String number, String email, String password, byte[] image){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",name);
        cv.put("surname",surname);
        cv.put("number",number);
        cv.put("email",email);
        cv.put("password",password);
        cv.put("image",image);

        long result= db.insert("users",null,cv);
        if(result==-1)
            return false;
        else
            return true;

    }

    public Bitmap getBitmap(String email){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from users where email=?",new String[] {email});
            cursor.moveToFirst();
            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            BitmapFactory.decodeByteArray(image, 0 , image.length);
            return BitmapFactory.decodeByteArray(image, 0 , image.length);

    }

    public Cursor getCursor(String email){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from users where email=?",new String[] {email});
        return cursor;


    }

    public boolean checkuser(String name,String email){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("Select * from users where name=?",new String[] {name});
        Cursor cursor1= db.rawQuery("Select * from users where email=?",new String[] {email});
        if (cursor.getCount()>0 || cursor1.getCount()>0 )
            return true;
        else
            return false;
    }

    public boolean checkpassword(String email,String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("Select * from users where email=? and password=?",new String[] {email,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
