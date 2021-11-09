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

        EditText nome = findViewById(R.id.nome);
        EditText email = findViewById(R.id.email);
        EditText usuario = findViewById(R.id.usuario);
        EditText senha = findViewById(R.id.senha);
        Button cadastrar = findViewById(R.id.cadastrar);

        //Evento do botão cadastrar
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criar um objeto da classe BancoDeDados
                BancoDeDados bd = new BancoDeDados(TelaCadastro.this, 1);
                //Chamar o método cadastrar() e verificar se retornou true ou false
                if(bd.cadastrar(nome.getText().toString(), email.getText().toString(),
                            usuario.getText().toString(), senha.getText().toString())){
                    Toast.makeText(TelaCadastro.this, "Sucesso",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TelaCadastro.this, "Erro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}







