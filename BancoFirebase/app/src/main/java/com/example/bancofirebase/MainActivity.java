package com.example.bancofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    //Conexão com o Firestore do Firebase
    FirebaseFirestore conexao = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText codigo = findViewById(R.id.codigo);
        EditText nome = findViewById(R.id.nome);
        EditText preco = findViewById(R.id.preco);
        EditText quantidade = findViewById(R.id.quantidade);
        Button cadastrar = findViewById(R.id.cadastrar);
        Button cadastrarID = findViewById(R.id.cadastrarID);
        Button buscar = findViewById(R.id.buscar);
        Button apagar = findViewById(R.id.apagar);
        Button telaListar = findViewById(R.id.telaListar);

        telaListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaListar.class));
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexao.collection("produtos")
                    .whereGreaterThan("preco", 500)
                    .get() //Recupera todos os documentos da coleção "produto"
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //Testar se existe algum conteúdo
                            if(task.isSuccessful()){
                                //Percorrer cada resultado que foi recuperado
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    Log.d("APP_FIREBASE", "ID: " + doc.getId());
                                    Log.d("APP_FIREBASE", "Nome: " + doc.get("nome"));
                                    Log.d("APP_FIREBASE", "Código: " + doc.get("codigo"));
                                    Log.d("APP_FIREBASE", "R$: " + doc.get("preco"));
                                    Log.d("APP_FIREBASE", "Qtde: " + doc.get("quantidade"));
                                }
                            }
                        }
                    });
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexao.collection("produtos")
                        .document(codigo.getText().toString())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Removido",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Erro",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


        cadastrarID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Criar objeto da classe Produto
                Produto p = new Produto(Integer.parseInt(codigo.getText().toString()),
                        nome.getText().toString(),
                        Double.parseDouble(preco.getText().toString()),
                        Integer.parseInt(quantidade.getText().toString()));

                //Conexão com o Firebase Firestore e atribuindo um ID ao documento
                conexao.collection("produtos")
                        .document(codigo.getText().toString())
                        .set(p)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Cadastrado",
                                                                Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Erro ao cadastrar",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Criar objeto da classe Produto
                Produto p = new Produto(Integer.parseInt(codigo.getText().toString()),
                        nome.getText().toString(),
                        Double.parseDouble(preco.getText().toString()),
                        Integer.parseInt(quantidade.getText().toString()));

                //Criar uma coleção chamada "produtos" no Firestore e enviar o produto "p"
                //para cdastrar com um ID automático, ou seja, gerado pelo Firestore
                conexao.collection("produtos")
                    .add(p) //Enviar o objeto "p" para cadastro
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Se o cadastro for realizado no Firestore...
                            Toast.makeText(MainActivity.this, "Cadastrado",
                                                                Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Se acontecer algum erro ao cadastrar...
                            Toast.makeText(MainActivity.this, "Erro ao cadastrar",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}