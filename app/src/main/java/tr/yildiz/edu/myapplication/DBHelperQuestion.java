package tr.yildiz.edu.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelperQuestion extends SQLiteOpenHelper {

    public static final String DBNAME = "exam.db";

    public DBHelperQuestion( Context context) {
        super(context,"exam.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table questions(question TEXT primary key,answer1 TEXT,answer2 TEXT,answer3 TEXT,answer4 TEXT,correctAnswer TEXT,imagePath TEXT,videoPath TEXT,audioPath TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists questions");
    }

    public boolean insertData(String question, String ans1, String ans2, String ans3, String ans4, String correctAnswer,String imagePath,String videoPath,String audioPath){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("question",question);
        cv.put("answer1",ans1);
        cv.put("answer2",ans2);
        cv.put("answer3",ans3);
        cv.put("answer4",ans4);
        cv.put("correctAnswer",correctAnswer);
        cv.put("imagePath",imagePath);
        cv.put("videoPath",videoPath);
        cv.put("audioPath",audioPath);


        long result= db.insert("questions",null,cv);
        if(result==-1){

            return false;
        }

        else{

            return true;
        }

    }

    public Cursor getCursor(String question){
        SQLiteDatabase db= this.getReadableDatabase();
        return db.rawQuery("Select * from users where question=?",new String[] {question});

    }
}
