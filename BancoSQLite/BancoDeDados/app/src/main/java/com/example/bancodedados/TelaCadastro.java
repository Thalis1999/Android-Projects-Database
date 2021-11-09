package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        //Componentes da tela
        EditText nome = findViewById(R.id.nome);
        EditText email = findViewById(R.id.email);
        EditText usuario = findViewById(R.id.usuario);
        EditText senha = findViewById(R.id.senha);
        Button cadastrar = findViewById(R.id.cadastrar);

        //Evento do botão cadastrar
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Criar um objeto da classe do BancoDeDados
                BancoDeDados bd = new BancoDeDados(TelaCadastro.this, 1);
                if(bd.cadastrar(
                        nome.getText().toString(), email.getText().toString(),
                        usuario.getText().toString(), senha.getText().toString() )){
                    Toast.makeText(TelaCadastro.this, "Cadastrado",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TelaCadastro.this, "Erro",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}








