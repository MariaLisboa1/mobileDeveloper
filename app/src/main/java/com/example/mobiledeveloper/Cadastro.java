package com.example.mobiledeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                criarUser(email, senha);
            }
        });
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                alert("Usuário cadastrado com sucesso");
                                Intent i = new Intent(Cadastro.this,Home.class);
                                startActivity(i);
                                finish();
                            } else {
                                alert("Erro de cadastro");
                            }
                        }
                    });
    }

    private void alert(String msg) {
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail = (EditText) findViewById(R.id.editCadastroEmail);
        editSenha = (EditText) findViewById(R.id.editCadastroSenha);
        btnRegistrar = (Button) findViewById(R.id.btnCadastroRegistrar);
        btnVoltar = (Button) findViewById(R.id.btnCadastroVoltar);
    }

    @Override
    protected void onStart(){
        super.onStart();
        auth = Connection.getFirebaseAuth();
    }
}
