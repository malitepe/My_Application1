package tr.yildiz.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;

import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Sign_up extends AppCompatActivity {



    EditText name;
    EditText surname;
    EditText email;
    EditText number;
    EditText password;
    EditText repassword;
    Button btnRegister;
    ImageView img;
    int SELECT_PICTURE = 200;
    DBHelper DB;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        defineVariables();
        defineListeners();
    }

    public void defineVariables(){
        name=(EditText)findViewById(R.id.txtName);
        surname=(EditText)findViewById(R.id.txtSurname);
        email=(EditText)findViewById(R.id.txtEmail);
        number=(EditText)findViewById(R.id.txtNumber);
        password=(EditText)findViewById(R.id.txtPassword);
        repassword=(EditText)findViewById(R.id.txtRepassword);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        img=(ImageView)findViewById(R.id.imgAvatar);
        DB=new DBHelper(this);

    }

    public void defineListeners(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=name.getText().toString();
                String surname1=surname.getText().toString();
                String email1=email.getText().toString();
                String number1=number.getText().toString();
                String password1=password.getText().toString();
                String repassword1=repassword.getText().toString();



                if(name1.equals("") || surname1.equals("") || email1.equals("") || number1.equals("") || password1.equals("") ||repassword1.equals(""))
                    Toast.makeText(Sign_up.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if (password1.equals(repassword1)){
                        boolean checkuser = DB.checkuser(name1,email1);
                        if (checkuser==false){
                            boolean insert = DB.insertData(name1,surname1,number1,email1,password1,byteArray);
                            if (insert==true){
                                Toast.makeText(Sign_up.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(getApplicationContext(),Menu.class);
                                intent.putExtra("email_address", email1);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Sign_up.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Sign_up.this, "User or Email already exists.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Sign_up.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }

                }



            }
        });


    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageURI = data.getData();
                InputStream imageStream ;
                //InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageURI);
                    img.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                    Bitmap image = ((BitmapDrawable)img.getDrawable()).getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}