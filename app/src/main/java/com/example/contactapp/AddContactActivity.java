package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        contactDao = appDatabase.contactDao();

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();

                // Thêm liên hệ vào cơ sở dữ liệu
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        contactDao.insert(new Contact(name, phone, email));
                        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });
    }
}

