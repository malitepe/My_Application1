package tr.yildiz.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btn_sign_in;
    Button btn_sign_up;
    Integer attempt;
    TextView sttsMessage;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineVariables();
        defineListeners();
    }

    public void defineVariables(){
        attempt=0;
        email=(EditText) findViewById(R.id.editTextTextUsername);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        btn_sign_in=(Button) findViewById(R.id.signin);
        btn_sign_up=(Button) findViewById(R.id.signup);
        sttsMessage=(TextView) findViewById(R.id.status);
        DB=new DBHelper(this);

    }

    public void defineListeners(){
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getApplicationContext(),Sign_up.class);
                startActivity(intent);

            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 =email.getText().toString();
                String password1 =password.getText().toString();

                if (email1.equals("") || password1.equals("")){
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkpass= DB.checkpassword(email1,password1);
                    if(checkpass==true){
                        Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),Menu.class);
                        intent.putExtra("email_address", email1);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        }
    }


