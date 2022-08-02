package com.fauzi.reportapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fauzi.reportapps.ui.main.MainActivity;

public class Login extends AppCompatActivity {
    EditText userId, password;
    Button login;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIdText = userId.getText().toString();
                String passwordText = password.getText().toString();
                String item = spinner.getSelectedItem().toString();
                if (userIdText.equals("admin") && passwordText.equals("admin") && item.equals("admin"))
                    startActivity(new Intent(Login.this, AdminActivity.class));

                if (userIdText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all Fields!", Toast.LENGTH_LONG).show();
                }else if (userIdText.equals("admin")&& passwordText.equals("admin")&& item.equals("user")){
                    startActivity(new Intent(Login.this, MainActivity.class));
                }else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(userIdText, passwordText);
                            if (userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }else {
                                String name = userEntity.name;
                                startActivity(new Intent(
                                        Login.this, MainActivity.class));                                            }
                        }
                            }).start();
                        }
            }
        });
    }
}