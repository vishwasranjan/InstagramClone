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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail,edtUsername,edtPassword;
    Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign Up");
        setContentView(R.layout.activity_sign_up);
        edtEmail=findViewById(R.id.edtEmail);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnSignup=findViewById(R.id.btn_login);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup.setOnClickListener(SignUp.this);
        btnLogin.setOnClickListener(SignUp.this);
        if (ParseUser.getCurrentUser()!=null)
        {
            transitiontosocialmediaactivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if (edtUsername.getText().toString().contentEquals("")||
                        edtPassword.getText().toString().contentEquals("")||
                        edtEmail.getText().toString().contentEquals("")){
                    Toast.makeText(this, "Email,Password and Username is required", Toast.LENGTH_SHORT).show();

                }
                else {
                    ParseUser parsesignup = new ParseUser();
                    parsesignup.setEmail(edtEmail.getText().toString());
                    parsesignup.setUsername(edtUsername.getText().toString());
                    parsesignup.setPassword(edtPassword.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing in " + edtUsername.getText().toString());
                    progressDialog.show();
                    parsesignup.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignUp.this, edtUsername.getText().toString() + " is siggned up sucessfully", Toast.LENGTH_SHORT).show();
                                transitiontosocialmediaactivity();
                            } else {
                                Toast.makeText(SignUp.this, "There was an error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.cancel();
                        }
                    });
                }
                break;
            case R.id.btnLogin:
                Intent intentlogin=new Intent(SignUp.this, LogIn.class);
                startActivity(intentlogin);
                break;
        }
    }
    public void rootlayoutisclicked(View view){
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
        Intent intent=new Intent(SignUp.this,SocialMediaActiviry.class);
        startActivity(intent);
    }
}