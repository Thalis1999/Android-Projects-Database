package com.example.bancofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TelaListar extends AppCompatActivity {
    FirebaseFirestore conexao = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar);

        List<Produto> lista = new ArrayList<Produto>();
        ListView listView = findViewById(R.id.listView);

        conexao.collection("produtos")
                .get() //Recupera todos os documentos da coleção "produto"
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Testar se existe algum conteúdo
                        if(task.isSuccessful()){
                            //Percorrer cada resultado que foi recuperado
                            for(QueryDocumentSnapshot doc : task.getResult()){

                                Produto p = new Produto(Integer.parseInt(doc.get("codigo").toString()),
                                        doc.get("nome").toString(),
                                        Double.parseDouble(doc.get("preco").toString()),
                                        Integer.parseInt(doc.get("quantidade").toString()));
                                lista.add(p);
                            }
                            ArrayAdapter<Produto> adapter = new ArrayAdapter<>(
                                    TelaListar.this,
                                    android.R.layout.simple_list_item_1,
                                    lista);
                            listView.setAdapter(adapter);
                        }
                    }
                });
    }
}