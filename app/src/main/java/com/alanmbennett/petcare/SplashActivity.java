package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button login = (Button) this.findViewById(R.id.login_button);
        Button signup = (Button) this.findViewById(R.id.signup_button);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchToLogin(view);
            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchToSignup(view);
            }
        });
    }

    public void switchToLogin(View view)
    {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void switchToSignup(View view)
    {
        Intent intent= new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
