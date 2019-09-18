package br.project.apprun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

import br.project.apprun.controller.UserService;
import br.project.apprun.database.Connection;
import br.project.apprun.model.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText nome, email, senha, telefone, disciplina, turma;
    private UserService userService;
    private Connection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nome = findViewById(R.id.input_nome);
        email = findViewById(R.id.input_email);
        senha = findViewById(R.id.input_senha);
        telefone = findViewById(R.id.input_telefone);
        disciplina = findViewById(R.id.input_disciplina);
        turma = findViewById(R.id.input_turma);
        userService = new UserService(this);

    }





    public void Save (View view) {

        //Values
        String nomevalue = nome.getText().toString();
        String emailvalue = email.getText().toString();
        String senhavalue = senha.getText().toString();
        String telefonevalue = telefone.getText().toString();
        String disciplinavalue = disciplina.getText().toString();
        String turmavalue = turma.getText().toString();


        User user = new User();
        user.setNome(nomevalue);
        user.setEmail(emailvalue);
        user.setSenha(senhavalue);
        user.setTelefone(telefonevalue);
        user.setDisciplina(disciplinavalue);
        user.setTurma(turmavalue);

        if(nomevalue.equals("") ||emailvalue.equals("") ||  senhavalue.equals("")
                || telefonevalue.equals("") || disciplinavalue.equals("") || turmavalue.equals("")) {
            Toast.makeText(this,"Preencha todos os campos!",Toast.LENGTH_SHORT).show();
        } else {
            long id = userService.inserir(user);
            Toast.makeText(this,"Usuario inserido com o id: " + id,Toast.LENGTH_SHORT).show();
            userService.inserir(user);

            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            }

        }






    }



