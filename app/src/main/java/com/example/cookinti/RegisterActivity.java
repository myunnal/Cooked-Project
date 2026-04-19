package com.example.cookinti;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppActivity.getDatabase();

        EditText userText = findViewById(R.id.regUser);
        EditText email = findViewById(R.id.regEmail);
        EditText pass1 = findViewById(R.id.regPass1);
        EditText pass2 = findViewById(R.id.regPass2);


        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userText.getText()) || TextUtils.isEmpty(email.getText()) ||
                        TextUtils.isEmpty(pass1.getText()) || TextUtils.isEmpty(pass2.getText()))
                {
                    Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                User attempt = db.userDao().findUsername(userText.getText().toString());
                if (attempt != null)
                {
                    Toast.makeText(getApplicationContext(), "username taken", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass1.getText().toString().equals(pass2.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                {
                    Toast.makeText(getApplicationContext(), "email not valid", Toast.LENGTH_SHORT).show();
                    return;
                }


                User newUser = new User();
                newUser.setUsername(userText.getText().toString());
                newUser.setPassword(pass1.getText().toString());
                newUser.setPronouns("he/him");

                db.userDao().insert(newUser);

                attempt = db.userDao().findUsername(userText.getText().toString());
                if (attempt != null)
                {
                    if (attempt.getPassword().equals(pass1.getText().toString()))
                    {
                        AppActivity.currentSession = attempt;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return;
                    }
                }

                return;

            }
        });
    }
}