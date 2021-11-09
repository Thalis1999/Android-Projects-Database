package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListarTodos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        ListView lista = findViewById(R.id.listaTodos);
        //Criar um objeto da classe do BancoDeDados
        BancoDeDados bd = new BancoDeDados(ListarTodos.this, 1);

        //Para adicionar os itens da lista no ListView, é necessário criar um Adapter
        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(
                ListarTodos.this, //Contexto da aplicação
                android.R.layout.simple_list_item_1, //Layout utilizado na lista (padrão)
                bd.buscaTodos() //Quais os elementos/valores que serão listados
        );

        lista.setAdapter(adapter);
    }
}