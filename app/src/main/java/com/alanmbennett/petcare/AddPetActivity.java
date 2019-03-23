package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AddPetActivity extends AppCompatActivity {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("uid");

        Button add  = (Button) this.findViewById(R.id.add_button);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchToDashboard();
            }
        });

    }

    void switchToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
