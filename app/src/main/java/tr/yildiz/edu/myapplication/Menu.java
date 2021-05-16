package tr.yildiz.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Menu extends AppCompatActivity {

    ImageView imageView;
    DBHelper DB;
    String email;
    TextView txtNameSurname;
    TextView txtMenuEmail;
    Button btn_add_question;
    Button btn_list_questions;
    Button btn_create_exam;
    Button btn_exam_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        defineVariables();
        defineListeners();

    }

    public void defineVariables(){
        btn_add_question=(Button)findViewById(R.id.btn_add_question);
        btn_list_questions=(Button)findViewById(R.id.btn_list_questions);
        btn_create_exam=(Button)findViewById(R.id.btn_create_exam);
        btn_exam_options=(Button)findViewById(R.id.btn_exam_options);

        txtNameSurname=(TextView)findViewById(R.id.txtNameSurname);
        txtMenuEmail=(TextView)findViewById(R.id.txtMenuEmail);

        imageView = (ImageView)findViewById(R.id.imageView);
        DB= new DBHelper(this);
        email = getIntent().getStringExtra("email_address");

        Cursor cursor= DB.getCursor(email);
        cursor.moveToFirst();
        byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
        BitmapFactory.decodeByteArray(image, 0 , image.length);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0 , image.length));
        String FullName=cursor.getString(cursor.getColumnIndex("name")) + " " + cursor.getString(cursor.getColumnIndex("surname"));
        txtNameSurname.setText(FullName);
        txtMenuEmail.setText(email);

    }

    public void defineListeners(){
        btn_add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),AddQuestionAvtivity.class);
                startActivity(intent);
            }
        });

        btn_list_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),ListQuestionsActivity.class);
                startActivity(intent);
            }
        });

        btn_create_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),CreateExamActivity.class);
                startActivity(intent);

            }
        });

        btn_exam_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),ExamOptionsActivity.class);
                startActivity(intent);

            }
        });



    }

}