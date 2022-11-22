package com.example.cyclingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private Button Btn;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Btn = findViewById(R.id.BT_login);
        progressbar = findViewById(R.id.progressBar);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPasswordReset();


            }
        });


    }
    public void sendPasswordReset() {

        progressbar.setVisibility(View.VISIBLE);

        // [START send_password_reset]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText ETemail = findViewById(R.id.ET_email);
        String emailAddress = ETemail.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Email sent.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            progressbar.setVisibility(View.GONE);
                            Intent intent = new Intent(ResetActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                            "Error. Try again later.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
        // [END send_password_reset]
    }
}