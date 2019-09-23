package br.project.apprun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.project.apprun.controller.UserService;
import br.project.apprun.database.Connection;
import br.project.apprun.model.User;

public class RegisterActivity extends AppCompatActivity {

    RadioGroup rg;
    RadioButton rb;
    EditText nome, email, senha, telefone, disciplina, turma;
    UserService userService;
    Connection db;
    ImageView foto;
    Button btnFoto;
    byte[] byteCurrFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Database - Service
        db = new Connection(this);
        userService = new UserService(this);

        //Permissao
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }


        //Inputs - Buttons
        nome = findViewById(R.id.input_nome);
        email = findViewById(R.id.input_email);
        senha = findViewById(R.id.input_senha);
        telefone = findViewById(R.id.input_telefone);
        disciplina = findViewById(R.id.input_disciplina);
        turma = findViewById(R.id.input_turma);
        rg = findViewById(R.id.rg_sexo);
        foto = findViewById(R.id.iv_foto);


        findViewById(R.id.btn_foto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarfoto();
            }
        });
    }


    public void tirarfoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            foto.setImageBitmap(imagem);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteCurrFoto = stream.toByteArray();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void Cadastrar(View view) {
        int radioSexo = rg.getCheckedRadioButtonId();
        rb = findViewById(radioSexo);

        String nomevalue = nome.getText().toString();
        String emailvalue = email.getText().toString().trim();
        String senhavalue = senha.getText().toString().trim();
        String telefonevalue = telefone.getText().toString();
        String disciplinavalue = disciplina.getText().toString();
        String turmavalue = turma.getText().toString();
        String sexovalue = rb.getText().toString();



        if (nomevalue.isEmpty() || emailvalue.isEmpty() || senhavalue.isEmpty() || telefonevalue.isEmpty() || disciplinavalue.isEmpty() || turmavalue.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

        } else if (senhavalue.length() < 6) {
            Toast.makeText(this, "Senha minima de 6 caracteres!", Toast.LENGTH_SHORT).show();

        } else if (db.emailExist(emailvalue)) {
            Toast.makeText(this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailvalue).matches()) {
            email.setError("Digite um e-mail valido!");
            Toast.makeText(this, "Digite um e-mail valido!", Toast.LENGTH_SHORT).show();

        } else if (byteCurrFoto == null) {
            Toast.makeText(this, "Por favor, tire uma foto sua!", Toast.LENGTH_SHORT).show();
        }else {
            User user = new User();
            user.setFoto(byteCurrFoto);
            user.setNome(nomevalue);
            user.setSexo(sexovalue);
            user.setEmail(emailvalue);
            user.setSenha(senhavalue);
            user.setTelefone(telefonevalue);
            user.setDisciplina(disciplinavalue);
            user.setTurma(turmavalue);
            long id = userService.inserir(user);
            userService.inserir(user);
            Toast.makeText(this, "Cadastro realizado com sucesso! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }


}



