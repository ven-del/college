package br.project.apprun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.project.apprun.database.Connection;

public class LoginActivity extends AppCompatActivity {
    EditText email, senha;
    Connection db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new Connection(this);
        email = findViewById(R.id.email_login);
        senha = findViewById(R.id.senha_login);

    }

    public void Register (View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        String emailvalue = email.getText().toString();
        String senhavalue = senha.getText().toString();

        boolean res = db.emailsenha(emailvalue, senhavalue);
        if(res){
            Toast.makeText(this,"Login efetuado com sucesso!" ,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (emailvalue.isEmpty() || senhavalue.isEmpty()) {
            Toast.makeText(this,"Preencha os campos!" ,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Dados inválidos!" ,Toast.LENGTH_SHORT).show();
        }

    }
}
