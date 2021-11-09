package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lista500 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista500);

        ListView lista = findViewById(R.id.lista500);
        //Criar um objeto da classe do BancoDeDados
        BancoDeDados bd = new BancoDeDados(Lista500.this, 1);

        //Para adicionar os itens da lista no ListView, é necessário criar um Adapter
        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(
                Lista500.this, //Contexto da aplicação
                android.R.layout.simple_list_item_1, //Layout utilizado na lista (padrão)
                bd.buscaAcima500() //Quais os elementos/valores que serão listados
        );

        lista.setAdapter(adapter);
    }
}