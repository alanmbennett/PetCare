package com.alanmbennett.petcare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText email;
    EditText password;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        Button login = (Button) this.findViewById(R.id.login_button);
        email = (EditText) this.findViewById(R.id.email_editText);
        password = (EditText) this.findViewById(R.id.password_editText);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validInput())
                {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        switchToDashboard();

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
    }

    boolean validInput()
    {

        return true;

    }

    void switchToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
