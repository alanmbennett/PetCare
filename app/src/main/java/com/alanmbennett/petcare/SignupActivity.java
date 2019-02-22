package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText email;
    EditText confirmEmail;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) this.findViewById(R.id.signup_act_button);
        signup.setEnabled(false);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validInput())
                    switchToAddPet(view);
            }
        });

        TextWatcher txtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validInput())
                    signup.setEnabled(true);
                else
                    signup.setEnabled(false);
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

    public void switchToAddPet(View view)
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    public boolean validInput()
    {
        String emailStr = email.getText().toString();
        String confirmEmailStr = confirmEmail.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPasswordStr = confirmPassword.getText().toString();

        if(!emailStr.equals(confirmEmailStr) || emailStr.isEmpty() || confirmEmailStr.isEmpty())
        {
            return false;
        }

        if(!passwordStr.equals(confirmPasswordStr) || passwordStr.isEmpty() || confirmPasswordStr.isEmpty())
        {
            return false;
        }

        return true;
    }
}
