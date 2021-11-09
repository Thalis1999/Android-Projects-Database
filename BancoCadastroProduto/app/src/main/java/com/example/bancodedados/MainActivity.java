package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nome = findViewById(R.id.nome);
        EditText preco = findViewById(R.id.preco);
        EditText quantidade = findViewById(R.id.quantidade);
        Button cadastrar = findViewById(R.id.cadastrar);
        Button atualizar = findViewById(R.id.atualizar);
        Button apagar = findViewById(R.id.apagar);
        Button listar = findViewById(R.id.listarTodos);
        Button listar500 = findViewById(R.id.listar500);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDeDados bd = new BancoDeDados(MainActivity.this, 1);
                if(bd.cadastrar(nome.getText().toString(),
                        Double.parseDouble(preco.getText().toString()),
                        Integer.parseInt(quantidade.getText().toString()))){
                    Toast.makeText(MainActivity.this, "Cadastrado",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao cadastrar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDeDados bd = new BancoDeDados(MainActivity.this, 1);
                if(bd.atualizar(nome.getText().toString(),
                        Double.parseDouble(preco.getText().toString()),
                        Integer.parseInt(quantidade.getText().toString()))){
                    Toast.makeText(MainActivity.this, "Atualizado",
                                                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao atualizar",
                                                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDeDados bd = new BancoDeDados(MainActivity.this, 1);
                if(bd.remover(nome.getText().toString())){
                    Toast.makeText(MainActivity.this, "Removido",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao remover",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ListarTodos.class);
                startActivity(it);
            }
        });

        listar500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Lista500.class);
                startActivity(it);
            }
        });

    }
}





