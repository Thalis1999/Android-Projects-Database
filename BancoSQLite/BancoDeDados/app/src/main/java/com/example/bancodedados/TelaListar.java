package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TelaListar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar);

        //Acessando o ListView da tela
        ListView listaTodos = findViewById(R.id.listaTodos);

        //Objeto da classe BancoDeDados
        BancoDeDados bd = new BancoDeDados(TelaListar.this, 1);

        //Antes de colocar os valores no ListView é preciso colocar em um adapter
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(
                TelaListar.this, //Contexto da aplicação
                android.R.layout.simple_list_item_1, //Layout (padrão)
                bd.buscarTodos() //Lista com os valores que serão exibidos
        );

        //Atribuir o adapter no ListView
        listaTodos.setAdapter(adapter);

    }
}



