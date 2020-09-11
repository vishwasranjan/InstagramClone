package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log In");
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btn_login);
        btnSignup=findViewById(R.id.btn_signin);
        btnSignup.setOnClickListener(LogIn.this);
        btnLogin.setOnClickListener(LogIn.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                final ProgressDialog progressDialog=new ProgressDialog(LogIn.this);
                progressDialog.setMessage("Signing in "+edtUsername.getText().toString());
                progressDialog.show();
                ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null&&e==null)
                        {
                            Toast.makeText(LogIn.this,edtUsername.getText().toString()+" is logged in",Toast.LENGTH_SHORT).show();
                            transitiontosocialmediaactivity();
                        }
                        else
                        {
                            Toast.makeText(LogIn.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                });
                break;
            case R.id.btn_signin:
                Intent intentlogin=new Intent(LogIn.this,SignUp.class);
                startActivity(intentlogin);
                break;
        }
    }
    public void rootlayoutisclicked(View view)
    {
        try {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void transitiontosocialmediaactivity()
    {
        Intent intent=new Intent(LogIn.this,SocialMediaActiviry.class);
        startActivity(intent);
    }
}