package com.example.loginlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginlite.Sql.DBHelper;

public class Login extends AppCompatActivity {
    EditText email , password;
    Button btnSubmit;
    TextView createAcc;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.text_email);
        password=findViewById(R.id.text_pass);
        btnSubmit = findViewById(R.id.btnSubmit_login);
        dbHelper = new DBHelper(this);
        btnSubmit.setOnClickListener(view -> {

            String emailCheck = email.getText().toString();
            String passCheck = password.getText().toString();
            Cursor  cursor = dbHelper.getData();
            if(cursor.getCount() == 0){
                Toast.makeText(Login.this,"No entries Exists",Toast.LENGTH_LONG).show();
            }
            if (loginCheck(cursor,emailCheck,passCheck)) {
                Intent intent = new Intent(Login.this,FinalPage.class);
                intent.putExtra("email",emailCheck);
                email.setText("");
                password.setText("");
                startActivity(intent);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setCancelable(true);
                builder.setTitle("Wrong Credential");
                builder.setMessage("Wrong Credential");
                builder.show();
            }
            dbHelper.close();
        });
        createAcc=findViewById(R.id.createAcc);
        createAcc.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this,SignUp.class);
            startActivity(intent);
        });

    }
    public static boolean loginCheck(Cursor cursor,String emailCheck,String passCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(emailCheck)) {
                return cursor.getString(2).equals(passCheck);
            }
        }
        return false;
    }

}