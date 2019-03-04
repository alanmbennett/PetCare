package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText email;
    EditText confirmEmail;
    EditText password;
    EditText confirmPassword;
    String errorStr;
    TextView errorMsg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        errorStr = "";
        errorMsg = (TextView) this.findViewById(R.id.error_textView);
        Log.d("First", "onCreate: ");
        signup = (Button) this.findViewById(R.id.signup_act_button);
        signup.setEnabled(false);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validInput()){
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        switchToAddPet();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Error!");

                                        builder.setMessage("Invalid Login Credentials!").setCancelable(true);

                                        builder.show();
                                    }
                                }
                            });
                }
            }
        });

        TextWatcher txtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validInput())
                {
                    errorMsg.setVisibility(View.GONE);
                    signup.setEnabled(true);
                }
                else
                {
                    errorMsg.setText(errorStr);
                    signup.setEnabled(false);
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        email = (EditText) this.findViewById(R.id.email_editText);
        confirmEmail = (EditText) this.findViewById(R.id.confirm_email_editText);
        password = (EditText) this.findViewById(R.id.password_editText);
        confirmPassword = (EditText) this.findViewById(R.id.confirm_password_editText);

        email.addTextChangedListener(txtWatcher);
        confirmEmail.addTextChangedListener(txtWatcher);
        password.addTextChangedListener(txtWatcher);
        confirmPassword.addTextChangedListener(txtWatcher);
    }

    public void switchToAddPet()
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    public boolean validInput()
    {
        boolean valid = true;
        String emailStr = email.getText().toString();
        String confirmEmailStr = confirmEmail.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPasswordStr = confirmPassword.getText().toString();
        errorStr = "One or more errors need to be corrected:\n\n";

        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Pattern emailRegex = Pattern.compile(emailPattern);
        Matcher m = emailRegex.matcher(emailStr);

        if(emailStr.length() == 0 || confirmEmailStr.length() == 0 || passwordStr.length() == 0
                || confirmPasswordStr.length() == 0)
        {
            errorStr += "- One or more fields are left blank.\n\n";
            valid = false;
        }

        if(!m.matches())
        {
            errorStr += "- Email given is not of valid email format.\n\n";
            valid = false;
        }

        if(!emailStr.equals(confirmEmailStr) || emailStr.isEmpty() || confirmEmailStr.isEmpty())
        {
            errorStr += "- Please make sure the emails match in the above fields.\n\n";
            valid = false;
        }

        if(!passwordStr.equals(confirmPasswordStr) || passwordStr.isEmpty() || confirmPasswordStr.isEmpty())
        {
            errorStr += "- Please make sure the passwords match in the above fields.\n";
            valid = false;
        }

        return valid;
    }
}
