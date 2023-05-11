package com.example.rentmyspot;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//
public class SigninActivity extends AppCompatActivity {
    EditText username, password,Email,Age;
    Button signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

            username = findViewById(R.id.username1);
            password = findViewById(R.id.password1);
            signin = findViewById(R.id.signin1);
            Email=findViewById(R.id.email1);
            Age=findViewById(R.id.Age);
            DB = new DBHelper(this);

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    String email= Email.getText().toString();
                    String age= Age.getText().toString();


                    if (user.equals("") || pass.equals("") || email.equals("") || age.equals(""))
                        Toast.makeText(SigninActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    else {
                        validateEmail(email);
                        validateAge(age);
                        if(validateEmail(email)==true && validateAge(age)==true){
                        Boolean checkUser = DB.checkUsername(user);
                        boolean checkEmail = DB.checkEmail(email);
                        if (!checkUser && !checkEmail) {
                            Boolean insert = DB.insertData(user, pass,email,age);
                            if (insert) {
                                Toast.makeText(SigninActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                intent.putExtra("username", user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SigninActivity.this, "Failed registration", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SigninActivity.this, "Already exists!", Toast.LENGTH_SHORT).show();
                        }
                   }
                }}
            });
        }
    private boolean validateEmail( String email) {
//        String emailInput = email.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }else
            Toast.makeText(this,"invalid email Address ",Toast.LENGTH_SHORT).show();
        return false;
    }
    private boolean validateAge(String age) {
        int ageinput= Integer.parseInt(age);
        if(ageinput >= 18){
            return true;
        }else
            Toast.makeText(this,"Age less than the required",Toast.LENGTH_SHORT).show();
        return false;
    }
}
