package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = (Button) this.findViewById(R.id.signup_act_button);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchToAddPet(view);
            }
        });
    }

    public void switchToAddPet(View view)
    {
        Intent intent= new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }
}
