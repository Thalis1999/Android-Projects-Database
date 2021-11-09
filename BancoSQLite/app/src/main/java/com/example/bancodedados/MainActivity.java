package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usuario = findViewById(R.id.usuarioLogin);
        EditText senha = findViewById(R.id.senhaLogin);
        Button acessar = findViewById(R.id.acessar);
        Button cadastro = findViewById(R.id.cadastro);

        Button listar = findViewById(R.id.listar);
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, TelaListar.class);
                startActivity(it);
            }
        });


        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir a tela do "TelaCadastro"
                Intent it = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(it);
            }
        });
        acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDeDados bd = new BancoDeDados(MainActivity.this, 1);
                if(bd.validarAcesso(usuario.getText().toString(), senha.getText().toString())){
                    Toast.makeText(MainActivity.this, "Acesso OK",
                                                                Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Dados inválidos",
                                                                    Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}






