package com.example.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    //Comando SQL para criar a tabela de usuários
    private final String criaTabelaProduto = "CREATE TABLE produto (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(60), " +
            "preco REAL, " +
            "quantidade INTEGER );";

    public BancoDeDados(@Nullable Context context, int version) {
        super(context, "BANCO_APP", null, version);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //O método do onConfigure é executado antes do onCreate
        //Usamos o onConfigure para habilitar o uso de chave estrangeira
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){
            //Se a versão do Android do dispositivo for a 4.1 ou anterior
            db.setForeignKeyConstraintsEnabled(true);
        }else{
            //Se for versões após a 4.1
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(criaTabelaProduto);
    }

    //Método para cadastrar um novo produto na tabela (INSERT)
    public boolean cadastrar(String nome, double preco, int quantidade){
        //Abrir a conexão com o banco de dados
        SQLiteDatabase conexao = getWritableDatabase();

        //Utilizar a classe ContentValues para passar o valor de cada coluna
        ContentValues valores = new ContentValues();
        valores.put("nome", nome); //nome_da_coluna, valor_a_inserir
        valores.put("preco", preco);
        valores.put("quantidade", quantidade);

        //Executar o comando INSERT já verificando com IF o retorno
        if(conexao.insert("produto", null, valores) != -1){
            //O método insert() retorna -1 se caso ocorreu algum erro, se
            //estive diferente de -1 então o INSERT executou corretamente
            return true;
        }else{
            return false;
        }
    }

    //Método para montar uma lista com todos os dados da tabela "produto"
    public List<Produto> buscaTodos(){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão

        Cursor resultado = conexao.query("produto", null, null,
                null, null, null, null);

        List<Produto> lista = new ArrayList<Produto>();

        //Testar se há algum conteúdo em "resultado"
        if(resultado.moveToFirst()){
            //Utilização do do/while para executar algo e depois avançar
            do{
                //Recuperar os valores de cada coluna da tabela
                int id = resultado.getInt(0); //Coluna de índice zero == coluna _id
                String nome = resultado.getString(1);
                double preco = resultado.getDouble(2);
                int quantidade = resultado.getInt(3);

                Produto p = new Produto(id, nome, preco, quantidade);

                //Adicionar o Produto "p" na lista
                lista.add(p);

            }while(resultado.moveToNext());
        }
        return lista;
    }

    public List<Produto> buscaAcima500(){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão

        Cursor resultado = conexao.query("produto", null,
                "preco > ?",
                new String[]{"500"}, null, null, null);

        List<Produto> lista = new ArrayList<Produto>();

        //Testar se há algum conteúdo em "resultado"
        if(resultado.moveToFirst()){
            //Utilização do do/while para executar algo e depois avançar
            do{
                //Recuperar os valores de cada coluna da tabela
                int id = resultado.getInt(0); //Coluna de índice zero == coluna _id
                String nome = resultado.getString(1);
                double preco = resultado.getDouble(2);
                int quantidade = resultado.getInt(3);

                Produto p = new Produto(id, nome, preco, quantidade);

                //Adicionar o usuário "u" na lista
                lista.add(p);

            }while(resultado.moveToNext());
        }
        return lista;
    }

    //Método para atualizar (UPDATE) os valores de um produto
    public boolean atualizar(String nome, double preco, int quantidade){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão

        //Utilizando a classe ContentValues para indicar os valores atualizados
        ContentValues valores = new ContentValues();
        valores.put("preco", preco);
        valores.put("quantidade", quantidade);

        //Chamando o método update
        if(conexao.update(
                "produto", //Nome da tabela que será atualizada
                valores, //Valores que serão atualizados
                "nome = ?", //Condição de atualização "WHERE nome = ?"
                new String[]{nome} //Valor que irá no lugar do ?
                ) != 0){ //Se o resultado do UPDATE for != 0, então atualizou algo
            return true;
        }else{
            return false;
        }
    }

    public boolean remover(String nome){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão

        if(conexao.delete("produto", "nome = ?",
                                                    new String[]{nome}) != 0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
