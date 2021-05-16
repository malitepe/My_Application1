package tr.yildiz.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddQuestionAvtivity extends AppCompatActivity {


    TextView txt_media_name;
    EditText edit_txt_question;
    EditText txt_1_slct;
    EditText txt_2_slct;
    EditText txt_3_slct;
    EditText txt_4_slct;
    EditText txt_5_slct;
    Button btn_select_media;
    Button btn_save_question;
    int SELECT_MEDIA = 200;
    DBHelperQuestion DB;
    boolean is_image;
    boolean is_video;
    boolean is_audio;
    String realPath;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_avtivity);
        defineVariables();
        defineListeners();
    }
    public void defineVariables(){
         txt_media_name=(TextView)findViewById(R.id.txt_media_name);
         edit_txt_question=(EditText)findViewById(R.id.edit_txt_question);
         txt_1_slct=(EditText)findViewById(R.id.txt_1_slct);
         txt_2_slct=(EditText)findViewById(R.id.txt_2_slct);
         txt_3_slct=(EditText)findViewById(R.id.txt_3_slct);
         txt_4_slct=(EditText)findViewById(R.id.txt_4_slct);
         txt_5_slct=(EditText)findViewById(R.id.txt_5_slct);
         btn_select_media=(Button)findViewById(R.id.btn_select_media);
         btn_save_question=(Button)findViewById(R.id.btn_save_question);
         DB = new DBHelperQuestion(this);
        is_image=false;
        is_video=false;
        is_audio=false;

    }

    public void defineListeners(){

        btn_select_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btn_save_question.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean success;
                if(!isEmpty()){
                    if (is_image) {
                        //handle image
                        success=DB.insertData(edit_txt_question.getText().toString(),txt_1_slct.getText().toString(),txt_2_slct.getText().toString(),
                                txt_3_slct.getText().toString(),txt_4_slct.getText().toString(),txt_5_slct.getText().toString(),
                                realPath,null,null );
                        if(success)
                        Toast.makeText(AddQuestionAvtivity.this, "question added with image", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddQuestionAvtivity.this, "false", Toast.LENGTH_SHORT).show();


                    } else  if (is_video) {
                        //handle video
                        success=DB.insertData(edit_txt_question.getText().toString(),txt_1_slct.getText().toString(),txt_2_slct.getText().toString(),
                                txt_3_slct.getText().toString(),txt_4_slct.getText().toString(),txt_5_slct.getText().toString(),
                                null,realPath,null );
                        if(success)
                            Toast.makeText(AddQuestionAvtivity.this, "question added with video", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddQuestionAvtivity.this, "false", Toast.LENGTH_SHORT).show();


                    }else  if(is_audio){
                        //handle audio
                        success=DB.insertData(edit_txt_question.getText().toString(),txt_1_slct.getText().toString(),txt_2_slct.getText().toString(),
                                txt_3_slct.getText().toString(),txt_4_slct.getText().toString(),txt_5_slct.getText().toString(),
                                null,null,realPath );
                        if(success)
                            Toast.makeText(AddQuestionAvtivity.this, "question added with audio", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddQuestionAvtivity.this, "false", Toast.LENGTH_SHORT).show();

                    }else {
                        success=DB.insertData(edit_txt_question.getText().toString(),txt_1_slct.getText().toString(),txt_2_slct.getText().toString(),
                                txt_3_slct.getText().toString(),txt_4_slct.getText().toString(),txt_5_slct.getText().toString(),
                                null,null,null );
                        if(success)
                            Toast.makeText(AddQuestionAvtivity.this, "question added with no media", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddQuestionAvtivity.this, "false", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    public boolean isEmpty(){
        String question=edit_txt_question.getText().toString();
        String answer1=txt_1_slct.getText().toString();
        String answer2=txt_2_slct.getText().toString();
        String answer3=txt_3_slct.getText().toString();
        String answer4=txt_4_slct.getText().toString();
        String correct_answer=txt_5_slct.getText().toString();
        if(question.equals("") || answer1.equals("") || answer2.equals("") || answer3.equals("") || answer4.equals("") ||correct_answer.equals("")){
            Toast.makeText(this, "Please fill all the text fields", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/* video/* audio/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_MEDIA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_MEDIA) {


                Uri selectedImageURI = data.getData();


                if (selectedImageURI.toString().contains("image")) {
                    //handle image
                    is_image=true;

                } else  if (selectedImageURI.toString().contains("video")) {
                    //handle video
                    is_video=true;

                }else  if(selectedImageURI.toString().contains("audio")){
                    //handle audio
                    is_audio=true;
                }
                realPath= selectedImageURI.getPath();
                txt_media_name.setText(realPath);


            }
        }
    }




}


